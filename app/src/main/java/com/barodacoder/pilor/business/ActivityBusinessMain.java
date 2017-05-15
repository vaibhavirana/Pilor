package com.barodacoder.pilor.business;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.barodacoder.pilor.ActivityBase;
import com.barodacoder.pilor.AppConstants;
import com.barodacoder.pilor.R;
import com.barodacoder.pilor.model.ListBooking;
import com.barodacoder.pilor.utils.ParseJson;
import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.net.URLDecoder;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ActivityBusinessMain extends ActivityBase {
    private Toolbar toolbar;

    private RecyclerView rvRequest, rvCalender;

    private AdapterJob adpJobs;
    private AdapterCalender adpCalender;

    private ArrayList<ListBooking> listBookings, listJob, listCalender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarGradiant(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_business_main);

        initData();

        initUi();

    }


    protected void initData() {
        super.initData();
        listBookings = new ArrayList<>();
        listJob = new ArrayList<>();
        listCalender = new ArrayList<>();
    }

    private void initUi() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((TextView) findViewById(R.id.tvTitle)).setTypeface(appData.getFontMedium());
        ((TextView) findViewById(R.id.tvTitle)).setText(URLDecoder.decode(appData.getUserData().getDisplayName()));


        adpJobs = new AdapterJob();
        rvRequest = (RecyclerView) findViewById(R.id.rvRequest);
        rvRequest.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvRequest.setAdapter(adpJobs);

        adpCalender = new AdapterCalender();
        rvCalender = (RecyclerView) findViewById(R.id.rvCalender);
        rvCalender.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvCalender.setAdapter(adpCalender);

        getAllJobList();
    }

    public class AdapterJob extends RecyclerView.Adapter<AdapterJob.MyViewHolder> {
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivUserPic;
            private TextView tvUserName, tvTime, tvOrder;

            public MyViewHolder(View view) {
                super(view);

                ivUserPic = (ImageView) view.findViewById(R.id.ivUserPic);

                tvUserName = (TextView) view.findViewById(R.id.tvUserName);
                tvUserName.setTypeface(appData.getFontBold());

                tvTime = (TextView) view.findViewById(R.id.tvTime);
                tvTime.setTypeface(appData.getFontRegular());

                tvOrder = (TextView) view.findViewById(R.id.tvOrder);
                tvOrder.setTypeface(appData.getFontRegular());
            }
        }

        public AdapterJob() {

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job, parent, false);

            //itemView.setOnClickListener(mOnClickListener);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            ListBooking listBooking = listJob.get(position);
            if (!listBooking.profile_pic.equals(""))
                Glide.with(getApplicationContext()).load(listBooking.profile_pic).placeholder(R.drawable.user).into(holder.ivUserPic);

            holder.tvUserName.setText(URLDecoder.decode(listBooking.display_name));

            holder.tvTime.setText(getString(R.string.txt_time).concat(": ").concat(URLDecoder.decode(listBooking.date_of_booking)));
            holder.tvOrder.setText(getString(R.string.txt_order).concat(": ").concat(URLDecoder.decode(listBooking.category_name)));
        }

        @Override
        public int getItemCount() {

            return listJob.size();
            //  return 2;
        }
    }

    public class AdapterCalender extends RecyclerView.Adapter<AdapterCalender.MyViewHolder> {
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivUserPic;
            private TextView tvUserName, tvTime, tvOrder;


            public MyViewHolder(View view) {
                super(view);

                ivUserPic = (ImageView) view.findViewById(R.id.ivUserPic);

                tvUserName = (TextView) view.findViewById(R.id.tvUserName);
                tvUserName.setTypeface(appData.getFontBold());

                tvTime = (TextView) view.findViewById(R.id.tvTime);
                tvTime.setTypeface(appData.getFontRegular());

                tvOrder = (TextView) view.findViewById(R.id.tvOrder);
                tvOrder.setTypeface(appData.getFontRegular());
            }
        }

        public AdapterCalender() {

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);

            //itemView.setOnClickListener(mOnClickListener);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            ListBooking listBooking = listCalender.get(position);
            Log.e("data",listCalender.get(position).toString());
            if (listBooking.profile_pic != null && !listBooking.profile_pic.equals(""))
                Glide.with(getApplicationContext()).load(listBooking.profile_pic).placeholder(R.drawable.user).into(holder.ivUserPic);

            holder.tvUserName.setText(URLDecoder.decode(listBooking.display_name));

            holder.tvTime.setText(getString(R.string.txt_time).concat(": ").concat(URLDecoder.decode(listBooking.date_of_booking)));
            holder.tvOrder.setText(getString(R.string.txt_order).concat(": ").concat(URLDecoder.decode(listBooking.category_name)));
        }

        @Override
        public int getItemCount() {

            return listCalender.size();
            //return 2;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_setting) {
            goToBusinessSettingScreen();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getAllJobList() {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("user_id", libFile.getUserId());
        params.put("user_token", libFile.getUserToken());

        client.post(AppConstants.URL_LIST_BOOKING_FOR_CUTTER, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                //showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.v(AppConstants.DEBUG_TAG, "SERVICE  RESPONSE : " + response);
                    if (ParseJson.parseListBooking(response).statusCode == 1)
                        listBookings.addAll(ParseJson.parseListBooking(response).info);

                    setData(listBookings);
                    //swipeRefresh.setRefreshing(false);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                try {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.v(AppConstants.DEBUG_TAG, "PAYMENT HISTORY RESPONSE : FAILED : " + response);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setData(ArrayList<ListBooking> listBookings) {
        for (ListBooking listBooking : listBookings) {
            if (listBooking.is_service_accepted == 0) {
                listJob.add(listBooking);
                Log.e("result", "JOB");
            } else {
                listCalender.add(listBooking);
                Log.e("result", "CALender");
            }
        }

        adpCalender.notifyDataSetChanged();
        adpJobs.notifyDataSetChanged();

    }
}
