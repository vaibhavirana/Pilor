package com.moonsted.pilors.business;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.moonsted.pilors.ActivityBase;
import com.moonsted.pilors.AppConstants;
import com.moonsted.pilors.R;
import com.moonsted.pilors.model.Hours;
import com.moonsted.pilors.model.UserData;
import com.moonsted.pilors.utils.AppData;
import com.moonsted.pilors.utils.ParseJson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ActivityBusinessOpeningHours extends ActivityBase implements View.OnClickListener {
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefresh;
    private TextView tvOpenMonday, tvCloseMonday;
    private TextView tvOpenTuesday, tvCloseTuesday;
    private TextView tvOpenWednesday, tvCloseWednesday;
    private TextView tvOpenThursday, tvCloseThursday;
    private TextView tvOpenFriday, tvCloseFriday;
    private TextView tvOpenSaturday, tvCloseSaturday;
    private TextView tvOpenSunday, tvCloseSunday;
    private UserData user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarGradiant(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_business_opening_hours);

        initData();

        initUi();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.slide_in_from_left_to_right, R.anim.slide_out_from_left_to_right);

        finish();
    }

    protected void initData() {
        super.initData();
        user = AppData.getInstance(ActivityBusinessOpeningHours.this).getUserData();

    }

    private void initUi() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back_icon);
        //toolbar.setTitle("Back");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvOpenMonday = (TextView) findViewById(R.id.tvOpenMonday);
        tvCloseMonday = (TextView) findViewById(R.id.tvCloseMonday);

        tvOpenMonday.setOnClickListener(this);
        tvCloseMonday.setOnClickListener(this);

        tvOpenTuesday = (TextView) findViewById(R.id.tvOpenTuesday);
        tvCloseTuesday = (TextView) findViewById(R.id.tvCloseTuesday);

        tvOpenTuesday.setOnClickListener(this);
        tvCloseTuesday.setOnClickListener(this);

        tvOpenWednesday = (TextView) findViewById(R.id.tvOpenWednesday);
        tvCloseWednesday = (TextView) findViewById(R.id.tvCloseWednesday);

        tvOpenWednesday.setOnClickListener(this);
        tvCloseWednesday.setOnClickListener(this);

        tvOpenThursday = (TextView) findViewById(R.id.tvOpenThursday);
        tvCloseThursday = (TextView) findViewById(R.id.tvCloseThursday);

        tvOpenThursday.setOnClickListener(this);
        tvCloseThursday.setOnClickListener(this);

        tvOpenFriday = (TextView) findViewById(R.id.tvOpenFriday);
        tvCloseFriday = (TextView) findViewById(R.id.tvCloseFriday);

        tvOpenFriday.setOnClickListener(this);
        tvCloseFriday.setOnClickListener(this);

        tvOpenSaturday = (TextView) findViewById(R.id.tvOpenSaturday);
        tvCloseSaturday = (TextView) findViewById(R.id.tvCloseSaturday);

        tvOpenSaturday.setOnClickListener(this);
        tvCloseSaturday.setOnClickListener(this);

        tvOpenSunday = (TextView) findViewById(R.id.tvOpenSunday);
        tvCloseSunday = (TextView) findViewById(R.id.tvCloseSunday);

        tvOpenSunday.setOnClickListener(this);
        tvCloseSunday.setOnClickListener(this);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);
            }
        });

        setUserProfile();

    }

    private void setUserProfile() {
        if (user.getOpeningHours() != null) {
            List<Hours> hoursList = user.getOpeningHours();
            if (hoursList != null) {
                Log.e("hours list", hoursList.toString());
                if (hoursList.size() > 0) {
                    if (hoursList.get(0).getStartTime() !=null && !hoursList.get(0).getStartTime().equalsIgnoreCase("00:00"))
                        tvOpenMonday.setText("" + hoursList.get(0).getStartTime());
                    if (hoursList.get(0).getEndTime()!=null && !hoursList.get(0).getEndTime().equalsIgnoreCase("00:00"))
                        tvCloseMonday.setText("" + hoursList.get(0).getEndTime());
                }

                if (hoursList.size() > 1) {
                    if (hoursList.get(1).getStartTime()!=null && !hoursList.get(1).getStartTime().equalsIgnoreCase("00:00"))
                        tvOpenTuesday.setText("" + hoursList.get(1).getStartTime());
                    if (hoursList.get(1).getEndTime()!=null && !hoursList.get(1).getEndTime().equalsIgnoreCase("00:00"))
                        tvCloseTuesday.setText("" + hoursList.get(1).getEndTime());
                }
                if (hoursList.size() > 2) {
                    if (hoursList.get(2).getStartTime()!=null && !hoursList.get(2).getStartTime().equalsIgnoreCase("00:00"))
                        tvOpenWednesday.setText("" + hoursList.get(2).getStartTime());
                    if (hoursList.get(2).getEndTime()!=null && !hoursList.get(2).getEndTime().equalsIgnoreCase("00:00"))
                        tvCloseWednesday.setText("" + hoursList.get(2).getEndTime());
                }
                if (hoursList.size() > 3) {
                    if (hoursList.get(3).getStartTime()!=null && !hoursList.get(3).getStartTime().equalsIgnoreCase("00:00"))
                        tvOpenThursday.setText("" + hoursList.get(3).getStartTime());
                    if (hoursList.get(3).getEndTime()!=null && !hoursList.get(3).getEndTime().equalsIgnoreCase("00:00"))
                        tvCloseThursday.setText("" + hoursList.get(3).getEndTime());
                }
                if (hoursList.size() > 4) {
                    if (hoursList.get(4).getStartTime()!=null && !hoursList.get(4).getStartTime().equalsIgnoreCase("00:00"))
                        tvOpenFriday.setText("" + hoursList.get(4).getStartTime());
                    if (hoursList.get(4).getEndTime()!=null && !hoursList.get(4).getEndTime().equalsIgnoreCase("00:00"))
                        tvCloseFriday.setText("" + hoursList.get(4).getEndTime());
                }
                if (hoursList.size() > 5) {
                    if (hoursList.get(5).getStartTime()!=null && !hoursList.get(5).getStartTime().equalsIgnoreCase("00:00"))
                        tvOpenSaturday.setText("" + hoursList.get(5).getStartTime());
                    if (hoursList.get(5).getEndTime()!=null && !hoursList.get(5).getEndTime().equalsIgnoreCase("00:00"))
                        tvCloseSaturday.setText("" + hoursList.get(5).getEndTime());
                }
                if (hoursList.size() > 6) {
                    if ( hoursList.get(6).getStartTime()!=null && !hoursList.get(6).getStartTime().equalsIgnoreCase("00:00"))
                        tvOpenSunday.setText("" + hoursList.get(6).getStartTime());
                    if (hoursList.get(6).getEndTime()!=null && !hoursList.get(6).getEndTime().equalsIgnoreCase("00:00"))
                        tvCloseSunday.setText("" + hoursList.get(6).getEndTime());
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tvOpenMonday:
                getTimePicker(tvOpenMonday);
                break;
            case R.id.tvCloseMonday:
                getTimePicker(tvCloseMonday);
                break;
            case R.id.tvOpenTuesday:
                getTimePicker(tvOpenTuesday);
                break;
            case R.id.tvCloseTuesday:
                getTimePicker(tvCloseTuesday);
                break;
            case R.id.tvOpenWednesday:
                getTimePicker(tvOpenWednesday);
                break;
            case R.id.tvCloseWednesday:
                getTimePicker(tvCloseWednesday);
                break;
            case R.id.tvOpenThursday:
                getTimePicker(tvOpenThursday);
                break;
            case R.id.tvCloseThursday:
                getTimePicker(tvCloseThursday);
                break;
            case R.id.tvOpenFriday:
                getTimePicker(tvOpenFriday);
                break;
            case R.id.tvCloseFriday:
                getTimePicker(tvCloseFriday);
                break;
            case R.id.tvOpenSaturday:
                getTimePicker(tvOpenSaturday);
                break;
            case R.id.tvCloseSaturday:
                getTimePicker(tvCloseSaturday);
                break;
            case R.id.tvOpenSunday:
                getTimePicker(tvOpenSunday);
                break;
            case R.id.tvCloseSunday:
                getTimePicker(tvCloseSunday);
                break;
        }
    }

    private void getTimePicker(final TextView textView) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                textView.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            updateOpeningHours();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateOpeningHours() {

        try {
            JSONArray outerArr = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("start_time", "" + tvOpenMonday.getText().toString());
            jsonObject.put("end_time", "" + tvCloseMonday.getText().toString());
            outerArr.put(jsonObject);

            jsonObject = new JSONObject();
            jsonObject.put("start_time", "" + tvOpenTuesday.getText().toString());
            jsonObject.put("end_time", "" + tvCloseTuesday.getText().toString());
            outerArr.put(jsonObject);

            jsonObject = new JSONObject();
            jsonObject.put("start_time", "" + tvOpenWednesday.getText().toString());
            jsonObject.put("end_time", "" + tvCloseWednesday.getText().toString());
            outerArr.put(jsonObject);

            jsonObject = new JSONObject();
            jsonObject.put("start_time", "" + tvOpenThursday.getText().toString());
            jsonObject.put("end_time", "" + tvCloseThursday.getText().toString());
            outerArr.put(jsonObject);

            jsonObject = new JSONObject();
            jsonObject.put("start_time", "" + tvOpenFriday.getText().toString());
            jsonObject.put("end_time", "" + tvCloseFriday.getText().toString());
            outerArr.put(jsonObject);

            jsonObject = new JSONObject();
            jsonObject.put("start_time", "" + tvOpenSaturday.getText().toString());
            jsonObject.put("end_time", "" + tvCloseSaturday.getText().toString());
            outerArr.put(jsonObject);

            jsonObject = new JSONObject();
            jsonObject.put("start_time", "" + tvOpenSunday.getText().toString());
            jsonObject.put("end_time", "" + tvCloseSunday.getText().toString());
            outerArr.put(jsonObject);

            Log.v("data", "OuterArr : " + outerArr.toString());

            if (outerArr.length() == 0) return;
            AsyncHttpClient client = new AsyncHttpClient();

            RequestParams params = new RequestParams();

            params.put("user_id", libFile.getUserId());
            params.put("user_token", libFile.getUserToken());
            params.put("opening_hours", outerArr.toString());

            client.post(AppConstants.URL_UPDATE_TIME, params, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                    swipeRefresh.setRefreshing(true);
                    //showProgressDialog();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    swipeRefresh.setRefreshing(false);

                    try {
                        String response = new String(responseBody, "UTF-8");

                        if (AppConstants.DEBUG)
                            Log.v(AppConstants.DEBUG_TAG, "SERVICE  RESPONSE : " + response);

                        if (ParseJson.parseOpeningHoursService(response).statusCode == 1) {
                            // AppData.getInstance(ActivityBusinessOpeningHours.this).getUserData().setOpeningHours((ParseJson.parseOpeningHoursService(response).info));
                            user.getOpeningHours().clear();
                            user.setOpeningHours(ParseJson.parseOpeningHoursService(response).info);
                            setUserProfile();
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
            // updateTimeAsyncTask = new UpdateTimeAsyncTask(getActivity(), this);
            // updateTimeAsyncTask.execute(outerArr.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
