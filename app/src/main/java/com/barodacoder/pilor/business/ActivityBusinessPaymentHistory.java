package com.barodacoder.pilor.business;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barodacoder.pilor.ActivityBase;
import com.barodacoder.pilor.AppConstants;
import com.barodacoder.pilor.R;
import com.barodacoder.pilor.model.PaymentHistory;
import com.barodacoder.pilor.utils.ParseJson;
import com.barodacoder.pilor.utils.RecyclerItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class ActivityBusinessPaymentHistory extends ActivityBase {
    private Toolbar toolbar;

    private SwipeRefreshLayout swipeRefresh;

    private RecyclerView rvHistory;

    private AdapterPaymentHistory adpHistory;

    private ArrayList<PaymentHistory> alPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_business_payment_history);

        initData();

        initUi();

        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);

                listPaymentHistory();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.slide_in_from_left_to_right, R.anim.slide_out_from_left_to_right);

        finish();
    }

    protected void initData() {
        super.initData();

        alPayment = new ArrayList<>();
    }

    private void initUi() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back_icon);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ((TextView) findViewById(R.id.tvTitle)).setTypeface(appData.getFontMedium());

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listPaymentHistory();
            }
        });

        rvHistory = (RecyclerView) findViewById(R.id.rvHistory);
        rvHistory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rvHistory.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rvHistory, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        adpHistory = new AdapterPaymentHistory();
        rvHistory.setAdapter(adpHistory);
    }

    public class AdapterPaymentHistory extends RecyclerView.Adapter<AdapterPaymentHistory.MyViewHolder> {
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private CircularImageView ivImage;

            private TextView tvName;

            private TextView tvDate;

            private TextView tvPrice;

            public MyViewHolder(View view) {
                super(view);

                tvName = (TextView) view.findViewById(R.id.tvName);
                tvName.setTypeface(appData.getFontMedium());

                tvDate = (TextView) view.findViewById(R.id.tvDate);
                tvDate.setTypeface(appData.getFontRegular());

                tvPrice = (TextView) view.findViewById(R.id.tvPrice);
                tvPrice.setTypeface(appData.getFontMedium());

                ivImage = (CircularImageView) view.findViewById(R.id.ivImage);
            }
        }

        public AdapterPaymentHistory() {
            //this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_history, parent, false);

            //itemView.setOnClickListener(mOnClickListener);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            PaymentHistory history = alPayment.get(position);

            if (!history.getProfilePic().equals(""))
                //  Glide.with(getApplicationContext()).load(history.getTaskPath()).placeholder(R.drawable.user).into(holder.ivImage);
                Glide.with(ActivityBusinessPaymentHistory.this).load(history.getProfilePic())
                        .asBitmap().centerCrop()
                        .placeholder(R.drawable.user)
                        .into(new BitmapImageViewTarget(holder.ivImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(ActivityBusinessPaymentHistory.this.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder.ivImage.setImageDrawable(circularBitmapDrawable);
                    }
                });
            holder.tvName.setText(URLDecoder.decode(history.getDisplayName()));

            holder.tvPrice.setText(history.getAmount().concat(" DKK"));

            try {
                String commentDate = history.getDate();

                Date date = dateFormat.parse(commentDate);

                //2016-12-06 13:47:04
                SimpleDateFormat sdfFinal = new SimpleDateFormat("MMM dd");

                holder.tvDate.setText((sdfFinal.format(date)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return alPayment.size();
        }
    }

    private void listPaymentHistory() {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("user_id", libFile.getUserId());
        params.put("user_token", libFile.getUserToken());
        // params.put("other_user_id", libFile.getUserId());

        client.post(AppConstants.URL_LIST_MY_TRANSACTION, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

                //showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                swipeRefresh.setRefreshing(false);

                try {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.v(AppConstants.DEBUG_TAG, "PAYMENT HISTORY RESPONSE : " + response);

                    alPayment.clear();

                    alPayment.addAll(ParseJson.parsePaymentHistory(response));

                    adpHistory.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                swipeRefresh.setRefreshing(false);

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
}
