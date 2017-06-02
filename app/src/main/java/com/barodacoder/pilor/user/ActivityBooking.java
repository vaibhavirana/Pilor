package com.barodacoder.pilor.user;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barodacoder.pilor.ActivityBase;
import com.barodacoder.pilor.AppConstants;
import com.barodacoder.pilor.R;
import com.barodacoder.pilor.custom.EmptyLayout;
import com.barodacoder.pilor.model.ListBooking;
import com.barodacoder.pilor.utils.AppData;
import com.barodacoder.pilor.utils.ParseJson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;

public class ActivityBooking extends ActivityBase {
    private Toolbar toolbar;
    private RecyclerView rvBooking;
    private AdapterBook adptBooking;
    private SwipeRefreshLayout swipeRefresh;
    private EmptyLayout emptyLayout;
    private ArrayList<ListBooking> listBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarGradiant(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_booking);

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
        listBookings = new ArrayList<>();
        getBookingList();
    }

    private void initUi() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back_icon);
        //toolbar.setTitle("Back");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ((TextView) findViewById(R.id.tvReview)).setTypeface(appData.getFontMedium());
        ((TextView) findViewById(R.id.tvReview)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivitySendReview.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
            }
        });

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBookingList();
            }
        });

        emptyLayout = (EmptyLayout) findViewById(R.id.emptyLayout);

        rvBooking = (RecyclerView) findViewById(R.id.rvBooking);
        rvBooking.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adptBooking = new AdapterBook();
        rvBooking.setAdapter(adptBooking);


    }

    public class AdapterBook extends RecyclerView.Adapter<AdapterBook.MyViewHolder> {
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView tvTitle, tvDetail, tvAction,txtSaloonName,txtDate,txtTime,txtCall,txtSMS,txtMap;
            private RelativeLayout rlAction, rlBottom;
            private LinearLayout llAction;
            private ImageView ivAccept, ivClear;

            public MyViewHolder(View view) {
                super(view);
                tvTitle = (TextView) view.findViewById(R.id.tvTitle);
                tvDetail = (TextView) view.findViewById(R.id.tvDetail);
                tvAction = (TextView) view.findViewById(R.id.tvAction);
                txtSaloonName = (TextView) view.findViewById(R.id.txtSaloonName);
                txtDate = (TextView) view.findViewById(R.id.txtDate);
                txtTime = (TextView) view.findViewById(R.id.txtTime);
                txtCall = (TextView) view.findViewById(R.id.txtCall);
                txtSMS = (TextView) view.findViewById(R.id.txtSMS);
                txtMap = (TextView) view.findViewById(R.id.txtMap);

                rlAction = (RelativeLayout) view.findViewById(R.id.rlAction);
                rlBottom = (RelativeLayout) view.findViewById(R.id.rlBottom);
                llAction = (LinearLayout) view.findViewById(R.id.llAction);
                ivAccept = (ImageView) view.findViewById(R.id.ivAccept);
                ivClear = (ImageView) view.findViewById(R.id.ivClear);

                tvTitle.setTypeface(AppData.getInstance(ActivityBooking.this).getFontBold());
                tvDetail.setTypeface(AppData.getInstance(ActivityBooking.this).getFontRegular());
                tvAction.setTypeface(AppData.getInstance(ActivityBooking.this).getFontRegular());
            }
        }

        public AdapterBook() {
            //this.moviesList = moviesList;
        }

        @Override
        public AdapterBook.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_revised, parent, false);
            return new AdapterBook.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final AdapterBook.MyViewHolder holder, int position) {

            final ListBooking booking = listBookings.get(position);
            // Log.e("booking", booking.is_service_accepted + "");
            String title = null, detail = null, action = null;
            if (booking.is_service_accepted == 0) {
                if (booking.is_reschedule == 0) {
                    title = getString(R.string.txt_order);
                    action = getString(R.string.txt_cancel);
                    detail = String.format(getString(R.string.txt_order_detail), booking.display_name, booking.date_of_booking);
                    holder.tvAction.setVisibility(View.VISIBLE);
                    holder.rlBottom.setVisibility(View.GONE);
                    holder.tvAction.setTextColor(getResources().getColor(R.color.color_google_red));
                    holder.tvAction.setBackground(getResources().getDrawable(R.drawable.bg_red_bordered_rounded_5));
                    holder.tvAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showCancelAlertMsg("", getString(R.string.txt_are_you_sure), booking.book_id);
                            // cancleBooking(booking.book_id);
                        }
                    });
                } else {
                    title = getString(R.string.txt_change_time);
                    // action = getString(R.string.txt_cancel);
                    detail = String.format(getString(R.string.txt_reschedule_detail),
                            booking.display_name, booking.date_of_booking);
                    holder.tvAction.setVisibility(View.GONE);
                    holder.llAction.setVisibility(View.VISIBLE);
                    holder.rlBottom.setVisibility(View.GONE);
                    holder.ivAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateRescheduleService(booking.book_id, 1);
                        }
                    });
                    holder.ivClear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateRescheduleService(booking.book_id, 4);
                        }
                    });
                }
            } else if (booking.is_service_accepted == 1) {
                if (booking.is_done == 0) {
                    title = getString(R.string.txt_order_confirmed);
                    action = getString(R.string.txt_cancel);
                    detail = String.format(getString(R.string.txt_confirm_detail), booking.display_name, booking.date_of_booking);
                    holder.tvAction.setVisibility(View.VISIBLE);
                    holder.rlBottom.setVisibility(View.GONE);
                    holder.tvAction.setTextColor(getResources().getColor(R.color.color_google_red));
                    holder.tvAction.setBackground(getResources().getDrawable(R.drawable.bg_red_bordered_rounded_5));
                    holder.tvAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showCancelAlertMsg("", getString(R.string.txt_are_you_sure), booking.book_id);
                            // cancleBooking(booking.book_id);
                        }
                    });
                } else if (booking.is_done == 3 && booking.is_review_added == 0) {
                    title = getString(R.string.txt_rate);
                    action = getString(R.string.txt_review);
                    detail = String.format(getString(R.string.txt_rate_detail), booking.display_name);
                    holder.tvAction.setVisibility(View.VISIBLE);
                    holder.rlBottom.setVisibility(View.GONE);
                    holder.tvAction.setTextColor(getResources().getColor(R.color.colorAccent));
                    holder.tvAction.setBackground(getResources().getDrawable(R.drawable.bg_blue_bordered_rounded_5));
                    holder.tvAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), ActivitySendReview.class);
                            intent.putExtra("booking", booking);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
                        }
                    });
                } else if (booking.is_done == 3) {
                    title = getString(R.string.txt_completed);
                    detail = String.format(getString(R.string.txt_rate_detail), booking.display_name);
                    holder.tvAction.setVisibility(View.GONE);
                    holder.rlBottom.setVisibility(View.VISIBLE);
                    holder.txtSaloonName.setText(booking.display_name);
                    Date date = null;
                    try {
                        date = dateFormat.parse(booking.date_of_booking);
                        DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
                        DateFormat dfTime = new SimpleDateFormat("HH:mm");
                        holder.txtDate.setText(dfDate.format(date));
                        holder.txtTime.setText(dfTime.format(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    holder.txtCall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    holder.txtMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    holder.txtSMS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            } else if (booking.is_service_accepted == 2) {
                title = getString(R.string.txt_rejected);
                //action = getString(R.string.txt_cancel);
                holder.tvAction.setVisibility(View.GONE);
                holder.rlBottom.setVisibility(View.GONE);
                detail = String.format(getString(R.string.txt_reject_detail), booking.display_name);
            } else if (booking.is_service_accepted == 3) {
                title = getString(R.string.txt_rate);
                action = getString(R.string.txt_review);
                detail = String.format(getString(R.string.txt_rate_detail), booking.display_name);
                holder.tvAction.setVisibility(View.VISIBLE);
                holder.rlBottom.setVisibility(View.GONE);
                holder.tvAction.setTextColor(getResources().getColor(R.color.color_google_red));
                holder.tvAction.setBackground(getResources().getDrawable(R.drawable.bg_blue_bordered_rounded_5));
                holder.tvAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ActivitySendReview.class);
                        intent.putExtra("booking", booking);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
                    }
                });
            } else if (booking.is_service_accepted == 4) {
                title = getString(R.string.txt_cancelled);
                action = getString(R.string.txt_cancel);
                holder.tvAction.setVisibility(View.GONE);
                holder.rlBottom.setVisibility(View.GONE);
                detail = String.format(getString(R.string.txt_cancel_detail), booking.display_name);
            }
            holder.tvTitle.setText(title);
            //holder.tvDetail.setText(URLDecoder.decode(booking.display_name));
            holder.tvDetail.setText(detail);
            holder.tvAction.setText(action);

        }

        @Override
        public int getItemCount() {

            return listBookings.size();
            //return 3;
        }
    }

    private void updateRescheduleService(String book_id, int is_service_accepted) {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("user_id", libFile.getUserId());
        params.put("user_token", libFile.getUserToken());
        params.put("book_id", book_id);
        params.put("is_service_accepted", is_service_accepted);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String datestring = dateFormat.format(date);
        params.put("localtime", datestring);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        params.put("localtime_UTC", dateFormat.format(date));
        Log.e("req", params.toString());
        client.post(AppConstants.URL_UPDATE_RESCHEDULE_REQUEST, params, new AsyncHttpResponseHandler() {
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
                    JSONObject jsonRoot = new JSONObject(response);

                    if (jsonRoot.has("status_code") && jsonRoot.getInt("status_code") == 1) {
                        //Toast.makeText(ActivityBooking.this, getString(R.string.txt_review_successfully), Toast.LENGTH_LONG).show();
                        getBookingList();
                    } /*else {
                        showOKAlertMsg(getString(R.string.txt_failed), getString(R.string.txt_review_failed), false);
                    }*/

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


    public void getBookingList() {
        final AsyncHttpClient client = new AsyncHttpClient();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        RequestParams params = new RequestParams();

        params.put("user_id", libFile.getUserId());
        params.put("user_token", libFile.getUserToken());

        Log.v(AppConstants.DEBUG_TAG, "BOOKING_LIST REQUEST : " + params.toString());

        client.post(AppConstants.URL_LIST_BOOKING, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

                //showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                swipeRefresh.setRefreshing(false);
                progressDialog.dismiss();
                try {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.v(AppConstants.DEBUG_TAG, "CUTTER_LIST RESPONSE : " + response);

                    listBookings.clear();
                    listBookings.addAll(ParseJson.parseListBooking(response).info);
                    Log.v(AppConstants.DEBUG_TAG, "CUTTER_LIST RESPONSE : " + listBookings.toString());

                    if (listBookings.size() == 0) {

                        emptyLayout.setVisibility(View.VISIBLE);
                        emptyLayout.setType(AppConstants.NO_BARBER, new EmptyLayout.onRefreshListner() {
                            @Override
                            public void onRefresh() {
                                getBookingList();
                            }
                        });
                    } else {
                        emptyLayout.setVisibility(View.GONE);
                    }
                    adptBooking.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                swipeRefresh.setRefreshing(false);
                progressDialog.dismiss();
                try {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.v(AppConstants.DEBUG_TAG, "CUTTER LIST RESPONSE : FAILED : " + response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showCancelAlertMsg(String title, String msg, final String booking_id) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setPositiveButton(getString(R.string.txt_yes_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                cancleBooking(booking_id);
                dialog.dismiss();
            }
        });

        dialogBuilder.setNegativeButton(getString(R.string.txt_no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setTitle(title);

        dialogBuilder.setMessage(msg);

        dialogBuilder.show();
    }

    public void cancleBooking(String book_id) {
        final AsyncHttpClient client = new AsyncHttpClient();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        RequestParams params = new RequestParams();

        params.put("user_id", libFile.getUserId());
        params.put("user_token", libFile.getUserToken());
        params.put("book_id", book_id);
        params.put("is_service_accepted", "4");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String datestring = dateFormat.format(date);
        params.put("localtime", datestring);
        // dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        //params.put("localtime_UTC", dateFormat.format(date));
        Log.v(AppConstants.DEBUG_TAG, "BOOKING_LIST REQUEST : " + params.toString());

        client.post(AppConstants.URL_CANCEL_SERVICE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

                //showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //swipeRefresh.setRefreshing(false);
                progressDialog.dismiss();
                try {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.v(AppConstants.DEBUG_TAG, "CUTTER_LIST RESPONSE : " + response);

                    // JSONObject jsonRoot = new JSONObject(response);

                    if (ParseJson.parseListBooking(response).statusCode == 1) {
                        listBookings.clear();
                        getBookingList();

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //  swipeRefresh.setRefreshing(false);
                progressDialog.dismiss();
                try {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.v(AppConstants.DEBUG_TAG, "CUTTER LIST RESPONSE : FAILED : " + response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
