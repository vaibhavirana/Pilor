package com.moonsted.pilors.business;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.moonsted.pilors.ActivityBase;
import com.moonsted.pilors.AppConstants;
import com.moonsted.pilors.R;
import com.moonsted.pilors.model.ListBooking;
import com.moonsted.pilors.utils.ParseJson;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;

public class ActivityBusinessMain extends ActivityBase {
    private Toolbar toolbar;

    private RecyclerView rvRequest, rvCalender;
    private SwipeRefreshLayout swipeRefresh;

    private AdapterJob adpJobs;
    private AdapterCalender adpCalender;

    private ArrayList<ListBooking> listJob, listCalender;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String selectedDate;


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
        listJob = new ArrayList<>();
        listCalender = new ArrayList<>();
    }

    private void initUi() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((TextView) findViewById(R.id.tvTitle)).setTypeface(appData.getFontMedium());
        ((TextView) findViewById(R.id.tvTitle)).setText(URLDecoder.decode(appData.getUserData().getDisplayName()));

        rvRequest = (RecyclerView) findViewById(R.id.rvRequest);
        rvRequest.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adpJobs = new AdapterJob();
        rvRequest.setAdapter(adpJobs);

        rvCalender = (RecyclerView) findViewById(R.id.rvCalender);
        rvCalender.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adpCalender = new AdapterCalender();
        rvCalender.setAdapter(adpCalender);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllJobList();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllJobList();
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setPositiveButton(getString(R.string.txt_yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                finish();
            }
        });

        dialogBuilder.setNegativeButton(getString(R.string.txt_no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setTitle(this.getString(R.string.exit_title));

        dialogBuilder.setMessage(this.getString(R.string.exit));

        dialogBuilder.show();
    }

    public class AdapterJob extends RecyclerView.Adapter<AdapterJob.MyViewHolder> {
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivUserPic, ivClear, ivReschedule, ivAccept;
            private TextView tvUserName, tvTime, tvOrder;

            public MyViewHolder(View view) {
                super(view);

                ivUserPic = (ImageView) view.findViewById(R.id.ivUserPic);
                ivClear = (ImageView) view.findViewById(R.id.ivClear);
                ivReschedule = (ImageView) view.findViewById(R.id.ivReschedule);
                ivAccept = (ImageView) view.findViewById(R.id.ivAccept);

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
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final ListBooking listBooking = listJob.get(position);

            if (!listBooking.profile_pic.equals("")) {
                //   Glide.with(getApplicationContext()).load(listBooking.profile_pic).placeholder(R.drawable.user).into(holder.ivUserPic);
                Glide.with(ActivityBusinessMain.this).load(listBooking.profile_pic)
                        .asBitmap().centerCrop()
                        .placeholder(R.drawable.user)
                        .into(new BitmapImageViewTarget(holder.ivUserPic) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(ActivityBusinessMain.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                holder.ivUserPic.setImageDrawable(circularBitmapDrawable);
                            }
                        });
            }

            holder.tvUserName.setText(URLDecoder.decode(listBooking.display_name));

            holder.tvTime.setText(getString(R.string.txt_time).concat(": ").concat(URLDecoder.decode(listBooking.date_of_booking)));
            holder.tvOrder.setText(getString(R.string.txt_order).concat(": ").concat(URLDecoder.decode(listBooking.category_name)));

            holder.ivAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateService(listBooking.book_id, 1);
                }
            });

            holder.ivClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateService(listBooking.book_id, 2);
                }
            });

            holder.ivReschedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // showMsgDialog("Reschedule date");
                    datePickerDialog(listBooking.book_id);
                }
            });
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
            private TextView tvUserName, tvTime, tvOrder, tvAction;


            public MyViewHolder(View view) {
                super(view);

                ivUserPic = (ImageView) view.findViewById(R.id.ivUserPic);

                tvUserName = (TextView) view.findViewById(R.id.tvUserName);
                tvUserName.setTypeface(appData.getFontBold());

                tvTime = (TextView) view.findViewById(R.id.tvTime);
                tvTime.setTypeface(appData.getFontRegular());

                tvOrder = (TextView) view.findViewById(R.id.tvOrder);
                tvOrder.setTypeface(appData.getFontRegular());

                tvAction = (TextView) view.findViewById(R.id.tvAction);
                tvAction.setTypeface(appData.getFontRegular());
            }
        }

        public AdapterCalender() {

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final ListBooking listBooking = listCalender.get(position);
           // Log.e("data", listCalender.get(position).toString());
            if (listBooking.profile_pic != null && !listBooking.profile_pic.equals("")) {    //Glide.with(getApplicationContext()).load(listBooking.profile_pic).placeholder(R.drawable.user).into(holder.ivUserPic);
                Glide.with(ActivityBusinessMain.this).load(listBooking.profile_pic)
                        .asBitmap().centerCrop()
                        .placeholder(R.drawable.user)
                        .into(new BitmapImageViewTarget(holder.ivUserPic) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(ActivityBusinessMain.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                holder.ivUserPic.setImageDrawable(circularBitmapDrawable);
                            }
                        });
            }
            holder.tvUserName.setText(URLDecoder.decode(listBooking.display_name));

            holder.tvTime.setText(getString(R.string.txt_time).concat(": ").concat(URLDecoder.decode(listBooking.date_of_booking)));
            holder.tvOrder.setText(getString(R.string.txt_order).concat(": ").concat(URLDecoder.decode(listBooking.category_name)));

            if (listBooking.is_done == 0) {
                holder.tvAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateService(listBooking.book_id, 3);
                    }
                });
            } else  if (listBooking.is_done ==3) {
                holder.tvAction.setText(getResources().getString(R.string.txt_completed));
            }
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
                swipeRefresh.setRefreshing(false);

                try {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.v(AppConstants.DEBUG_TAG, "SERVICE  RESPONSE : " + response);
                    if (ParseJson.parseListBooking(response).statusCode == 1) {
                        listJob.clear();
                        listJob.addAll(ParseJson.parseListBooking(response).info);
                       // adpJobs.notifyDataSetChanged();

                        adpJobs = new AdapterJob();
                        rvRequest.setAdapter(adpJobs);

                        listCalender.clear();
                        // listBookings.addAll(ParseJson.parseListBooking(response).info);
                        listCalender.addAll(ParseJson.parseListBooking(response).accepted_info);
                        //adpCalender.notifyDataSetChanged();
                        adpCalender = new AdapterCalender();
                        rvCalender.setAdapter(adpCalender);


                    }
                    //swipeRefresh.setRefreshing(false);

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

    private void updateService(String book_id, int code) {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("user_id", libFile.getUserId());
        params.put("user_token", libFile.getUserToken());
        params.put("book_id", book_id);
        params.put("is_service_accepted", code);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String datestring = dateFormat.format(date);
        params.put("localtime", datestring);

       // Log.e("req",params.toString());
        client.post(AppConstants.URL_UPDATE_SERVICE_STATUS_BY_CUTTER, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    cancelProgressDialog();
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.e(AppConstants.DEBUG_TAG, "SERVICE  RESPONSE : " + response);
                    if (ParseJson.parseListBooking(response).statusCode == 1)
                        getAllJobList();
                    //swipeRefresh.setRefreshing(false);



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                try {
                    cancelProgressDialog();
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.v(AppConstants.DEBUG_TAG, "PAYMENT HISTORY RESPONSE : FAILED : " + response);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void datePickerDialog(final String bookId)
    {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Log.e("date:",dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        selectedDate = year
                                + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                        timePickerDialog(bookId,c);
                    }
                }, mYear, mMonth, mDay);
        c.add(Calendar.DAY_OF_YEAR,1);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    public void timePickerDialog(final String bookId, Calendar c)
    {

        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        Log.e("time",hourOfDay + ":" + minute);
                        selectedDate = selectedDate+ " " + hourOfDay + ":" + minute
                                + ":00" ;

                        Log.e("time",selectedDate);

                        rescheduleService(bookId,selectedDate);
                     /*   BookService bookService=new BookService();
                        bookService.service_id=android.text.TextUtils.join(",", selectedIds);
                        bookService.date_of_booking=selectedDate;
                        bookService.service_provide_by=userCutter.getUserId();
                        bookService.price= String.valueOf(totalPrice);
                        goToEnterPinScreen(bookService);*/
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    private void rescheduleService(String book_id,String booking_date) {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("user_id", libFile.getUserId());
        params.put("user_token", libFile.getUserToken());
        params.put("book_id", book_id);
        params.put("is_service_accepted", 0);
        params.put("date_of_booking", booking_date);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String datestring = dateFormat.format(date);
        params.put("localtime", datestring);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        params.put("localtime_UTC", dateFormat.format(date));
        Log.e("req",params.toString());
        client.post(AppConstants.URL_RESCHEDULE_REQUEST, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    String response = new String(responseBody, "UTF-8");
                    cancelProgressDialog();
                    if (AppConstants.DEBUG)
                        Log.e(AppConstants.DEBUG_TAG, "SERVICE  RESPONSE : " + response);
                   /* if (ParseJson.parseListBooking(response).statusCode == 1)
                        listBookings.addAll(ParseJson.parseListBooking(response).info);
                    */
                    //swipeRefresh.setRefreshing(false);

                    getAllJobList();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                try {
                    cancelProgressDialog();
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.e(AppConstants.DEBUG_TAG, "PAYMENT HISTORY RESPONSE : FAILED : " + response);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
