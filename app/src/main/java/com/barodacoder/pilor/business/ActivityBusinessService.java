package com.barodacoder.pilor.business;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.barodacoder.pilor.ActivityBase;
import com.barodacoder.pilor.AppConstants;
import com.barodacoder.pilor.R;
import com.barodacoder.pilor.custom.SwitchView;
import com.barodacoder.pilor.model.Service;
import com.barodacoder.pilor.utils.ParseJson;
import com.barodacoder.pilor.utils.RecyclerItemClickListener;
import com.barodacoder.pilor.utils.Validator;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.barodacoder.pilor.R.id.rvHistory;

public class ActivityBusinessService extends ActivityBase {
    private Toolbar toolbar;

    private SwipeRefreshLayout swipeRefresh;

    private RecyclerView rvService;

    private AdapterService adpService;

    private List<Service> allService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_business_payment_history);

        initData();

        initUi();
        allService = new ArrayList<>();

        getServicesList();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.slide_in_from_left_to_right, R.anim.slide_out_from_left_to_right);

        finish();
    }

    protected void initData() {
        super.initData();

        // alPayment = new ArrayList<>();
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

        ((TextView) findViewById(R.id.tvTitle)).setText(getString(R.string.txt_service_and_pricing));
        ((TextView) findViewById(R.id.tvTitle)).setTypeface(appData.getFontMedium());

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);
            }
        });

        rvService = (RecyclerView) findViewById(rvHistory);
        rvService.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rvService.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rvService, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        adpService = new AdapterService();
        rvService.setAdapter(adpService);
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
            if (validate()) updateServices();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validate() {

        for (int i = 0; i < allService.size(); i++) {

            Service service = allService.get(i);

            if (service.getIs_service_active() != null) {
                if (!service.getIs_service_active().isEmpty() && Validator.isNumeric(service.getIs_service_active())) {
                    int isServiceActiveInt = Integer.parseInt(service.getIs_service_active());
                    if (isServiceActiveInt == 1) {
                        if (!Validator.validateValueIsNotZero(service.getRate())) {
                            showMsgDialog(getString(R.string.str_enter_rate_of_service));
                            return false;
                        }
                    }
                }

            }
        }
        return true;
    }


    public class AdapterService extends RecyclerView.Adapter<AdapterService.MyViewHolder> {
        public class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView tvServiceName;

            private EditText etPrice;

            private SwitchView switchService;

            public MyViewHolder(View view) {
                super(view);

                tvServiceName = (TextView) view.findViewById(R.id.tvServiceName);
                tvServiceName.setTypeface(appData.getFontMedium());

                etPrice = (EditText) view.findViewById(R.id.etPrice);
                etPrice.setTypeface(appData.getFontRegular());

                switchService = (SwitchView) view.findViewById(R.id.switchService);
                switchService.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(SwitchView switchView, boolean isChecked) {
                        Toast.makeText(ActivityBusinessService.this, "onCheckedChanged: " + isChecked, Toast.LENGTH_SHORT).show();
                        Service service = allService.get(getAdapterPosition());
                        if (isChecked) {
                            service.is_service_active = "1";
                        } else {
                            service.is_service_active = "0";
                            //service.rate = "0";
                        }
                    }
                });

                etPrice.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.toString().trim().length() > 0) {
                            Service service = allService.get(getAdapterPosition());
                            service.rate = charSequence.toString().trim();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        }

        public AdapterService() {
            //this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_services, parent, false);

            //itemView.setOnClickListener(mOnClickListener);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            Service service = allService.get(position);

            if (service != null) {

                holder.tvServiceName.setText("" + URLDecoder.decode(service.getService_name()));

                if (service.is_service_active == null) {
                    holder.switchService.setChecked(false);
                    holder.etPrice.setText("0");
                } else if (!service.is_service_active.isEmpty() && Validator.isNumeric(service.is_service_active)) {
                    holder.etPrice.setText("" + service.rate);
                    int isServiceActiveInt = Integer.parseInt(service.is_service_active);
                    if (isServiceActiveInt == 0) {
                        holder.switchService.setChecked(false);
                    } else if (isServiceActiveInt == 1) {
                        holder.switchService.setChecked(true);

                    }
                }


            }
        }

        @Override
        public int getItemCount() {

            //return 5;
            return allService.size();
        }
    }

    private void getServicesList() {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("user_id", libFile.getUserId());

        client.post(AppConstants.URL_LIST_USER_SERVICES, params, new AsyncHttpResponseHandler() {
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

                    allService.addAll(ParseJson.parseListService(response).info);

                    swipeRefresh.setRefreshing(false);

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

    private void updateServices() {

        JSONArray jsonArray = new JSONArray();

        Log.e("array", "alServices : " + allService.toString());
        try {
            for (Service service : allService) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("service_id", "" + service.service_id);
                if (service.rate != null && !service.rate.isEmpty() && Validator.isNumeric(service.rate)) {
                    int rate = Integer.parseInt(service.rate);
                    jsonObject.put("rate", "" + rate);
                } else {
                    jsonObject.put("rate", "0");
                }
                jsonObject.put("user_id", "" + service.user_id);
                jsonObject.put("service_name", "" + service.service_name);
                jsonObject.put("is_service_active", "" + service.is_service_active);
                jsonArray.put(jsonObject);
            }

            Log.v("array", "JsonArr: " + jsonArray.toString());
            if (jsonArray.length() == 0) return;
            AsyncHttpClient client = new AsyncHttpClient();

            RequestParams params = new RequestParams();

            params.put("user_id", libFile.getUserId());
            params.put("user_token", libFile.getUserToken());
            params.put("service_info", jsonArray.toString());

            client.post(AppConstants.URL_UPDATE_USER_SERVICES, params, new AsyncHttpResponseHandler() {
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
                        if (ParseJson.parseListService(response).statusCode == 1) {
                            allService.clear();
                            allService.addAll(ParseJson.parseListService(response).info);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
