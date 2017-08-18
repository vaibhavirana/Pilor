package com.moonsted.pilors.user;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akhgupta.easylocation.EasyLocationAppCompatActivity;
import com.akhgupta.easylocation.EasyLocationRequest;
import com.akhgupta.easylocation.EasyLocationRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.moonsted.pilors.AppConstants;
import com.moonsted.pilors.R;
import com.moonsted.pilors.custom.EmptyLayout;
import com.moonsted.pilors.custom.MaterialSpinner;
import com.moonsted.pilors.model.Category;
import com.moonsted.pilors.model.UserCutter;
import com.moonsted.pilors.rating.ProperRatingBar;
import com.moonsted.pilors.utils.ParseJson;
import com.moonsted.pilors.utils.RecyclerItemClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.TextUtils;

import static com.moonsted.pilors.ActivityBase.appData;
import static com.moonsted.pilors.ActivityBase.libFile;
import static com.moonsted.pilors.ActivityBase.setStatusBarGradiant;
import static com.moonsted.pilors.R.id.map;
import static com.moonsted.pilors.R.id.search;


public class MainActivity extends EasyLocationAppCompatActivity {
    private Toolbar toolbar;

    private SwipeRefreshLayout swipeRefresh;

    private RecyclerView rvCutter;

    private ArrayList<UserCutter> cutterArrayList;
    private ArrayList<Category> categoryArrayList ;

    private AdapterCategories adpCutter;
    private EditText searchView;
    private EmptyLayout emptyLayout;
    private RadioGroup radioGroupExplore;
    private RelativeLayout rlExplore;
    private MaterialSpinner spCat;

    MapView mapView;
    private int service_id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setStatusBarGradiant(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(map);
        rlExplore = (RelativeLayout) findViewById(R.id.rlExplore);

        mapView.onCreate(savedInstanceState);
        initData();

        initUi();

        LocationRequest locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(5000);
        EasyLocationRequest easyLocationRequest = new EasyLocationRequestBuilder()
                .setLocationRequest(locationRequest)
                .setFallBackToLastLocationTime(3000)
                .build();
        requestSingleLocationFix(easyLocationRequest);

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
        getAllCategoryList();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
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

    protected void initData() {
        //super.initData();
        cutterArrayList = new ArrayList<>();
        categoryArrayList = new ArrayList<>();
    }

    private void initUi() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_action_booking);
        //toolbar.setTitle("Back");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ActivityBooking.class);

                startActivity(intent);

                overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
            }
        });

        spCat = (MaterialSpinner) findViewById(R.id.spCat);
        spCat.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
              //service_id=;
            }
        });

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        searchView = (EditText) findViewById(search);

        radioGroupExplore = (RadioGroup) findViewById(R.id.radioGroupExplore);
        radioGroupExplore.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioExplore:
                        mapView.setVisibility(View.GONE);
                        rlExplore.setVisibility(View.VISIBLE);
                        break;

                    case R.id.radioMap:
                        mapView.setVisibility(View.VISIBLE);
                        rlExplore.setVisibility(View.GONE);
                        break;
                }
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchView.setText("");
                getCutterList();
            }
        });

        searchView.clearFocus();
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    getCutterList();
                } else
                    searchCutterList(s.toString());
                // TODO Auto-generated method stub
            }
        });

        rvCutter = (RecyclerView) findViewById(R.id.rvCutter);
        emptyLayout = (EmptyLayout) findViewById(R.id.emptyLayout);


        //rvCategory.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        rvCutter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rvCutter.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rvCutter, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), ActivityCutterProfile.class);
                intent.putExtra("user", cutterArrayList.get(position));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        adpCutter = new AdapterCategories();
        rvCutter.setAdapter(adpCutter);
    }

    private void showMap() {
        if (mapView != null) {
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {

                    if (cutterArrayList.size() > 0) {
                        for (UserCutter userCutter : cutterArrayList) {

                            LatLng myLocation = new LatLng(userCutter.getLatitude(), userCutter.getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(myLocation)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin))
                                    .title(userCutter.getDisplayName()));


                            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                                @Override
                                public View getInfoWindow(Marker arg0) {
                                    return null;
                                }

                                @Override
                                public View getInfoContents(Marker marker) {
                                    View myContentView = getLayoutInflater().inflate(
                                            R.layout.item_map_info, null);
                                    TextView tvTitle = ((TextView) myContentView
                                            .findViewById(R.id.tvTitle));
                                    tvTitle.setText(marker.getTitle());

                                    return myContentView;
                                }
                            });


                            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                                @Override
                                public void onInfoWindowClick(Marker arg0) {
                                    // TODO Auto-generated method stub
                                    Intent intent = new Intent(getApplicationContext(), ActivityCutterProfile.class);
                                    int index = getIndex(cutterArrayList, arg0.getTitle());
                                    intent.putExtra("user", cutterArrayList.get(index));
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
                                }
                            });
                        }
                    }
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        return;
                    }

                    googleMap.setMyLocationEnabled(true);
                    googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                    googleMap.getUiSettings().setZoomControlsEnabled(false);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(Double.parseDouble(libFile.getLatitude()), Double.parseDouble(libFile.getLongitude()))), 12));
                }
            });

        }
    }

    private int getIndex(ArrayList<UserCutter> cutterArrayList, String title) {
        for (UserCutter d : cutterArrayList) {
            if (d.getDisplayName() != null && d.getDisplayName().contains(title))
                //something here
                return cutterArrayList.indexOf(d);
        }
        return 0;
    }

    @Override
    public void onLocationPermissionGranted() {
        showToast("Location permission granted");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationPermissionDenied() {
        showToast("Location permission denied");
    }

    @Override
    public void onLocationReceived(Location location) {
        //showToast(location.getProvider() + "," + location.getLatitude() + "," + location.getLongitude());
        appData.setLatitude(location.getLatitude());
        appData.setLongitude(location.getLongitude());

        libFile.setLatitude(location.getLatitude() + "");
        libFile.setLongitude(location.getLongitude() + "");

        if (location != null) {
            swipeRefresh.setRefreshing(true);
            getCutterList();
        }
    }

    @Override
    public void onLocationProviderEnabled() {
        showToast("Location services are now ON");
    }

    @Override
    public void onLocationProviderDisabled() {
        showToast("Location services are still Off");
    }

    public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.MyViewHolder> {

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivImage;
            private CircularImageView ivImage1;
            private TextView tvCatName, tvDistance, tvDistance1;
            private ProperRatingBar ratingBar;

            public MyViewHolder(View view) {
                super(view);
                ivImage = (ImageView) view.findViewById(R.id.ivImage);
                ivImage1 = (CircularImageView) view.findViewById(R.id.ivImage1);
                ratingBar = (ProperRatingBar) view.findViewById(R.id.rating);
                tvDistance = (TextView) view.findViewById(R.id.tvDistance);
                tvDistance1 = (TextView) view.findViewById(R.id.tvDistance1);
                tvCatName = (TextView) view.findViewById(R.id.tvCatName);
                tvCatName.setTypeface(appData.getFontBold());
                tvDistance.setTypeface(appData.getFontBold());
                tvDistance1.setTypeface(appData.getFontRegular());

            }

        }

        public AdapterCategories() {
            //this.moviesList = moviesList;
        }

        @Override
        public AdapterCategories.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cutter, parent, false);
            return new AdapterCategories.MyViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

            final UserCutter userCutter = cutterArrayList.get(position);

           // if (!userCutter.getThumbImage1().equals(""))
                Glide.with(getApplicationContext()).load(userCutter.getThumbImage1())
                        .placeholder(R.drawable.default_image)
                        .into(holder.ivImage);

            if (!userCutter.getProfile().equals(""))
                Glide.with(getApplicationContext()).load(userCutter.getProfile()).asBitmap().centerCrop()
                        .placeholder(R.drawable.icon_no_image)
                        .into(new BitmapImageViewTarget(holder.ivImage1) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                holder.ivImage1.setImageDrawable(circularBitmapDrawable);
                            }
                        });

            holder.tvCatName.setText(userCutter.getDisplayName());
            holder.tvDistance.setText("From " + userCutter.getMin_rate() + " DKK");
            DecimalFormat df = new DecimalFormat("#.##");
            holder.tvDistance1.setText(df.format(distance(userCutter.getLatitude(), userCutter.getLongitude())) + " km");
            Log.e("rating", cutterArrayList.get(position).getAvarage_rating() + "");
            holder.ratingBar.setRating(userCutter.getAvarage_rating());
            //   holder.ratingBar.setRating(2);
        }

        @Override
        public int getItemCount() {

            //return 5;
            return cutterArrayList.size();
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
            Intent intent = new Intent(getApplicationContext(), ActivitySetting.class);

            startActivity(intent);

            overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getCutterList() {
        final AsyncHttpClient client = new AsyncHttpClient();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        RequestParams params = new RequestParams();

        params.put("user_id", libFile.getUserId());
        params.put("user_token", libFile.getUserToken());
        params.put("latitude", libFile.getLatitude());
        params.put("longitude", libFile.getLongitude());
        params.put("radius", libFile.getRedius());
        params.put("service_id", service_id);

        Log.v(AppConstants.DEBUG_TAG, "CUTTER_LIST REQUESR : " + params.toString());

        client.post(AppConstants.URL_LIST_CUTTER, params, new AsyncHttpResponseHandler() {
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

                    cutterArrayList.clear();
                    cutterArrayList.addAll(ParseJson.parseCutterList(response).getInfo());
                    Log.v(AppConstants.DEBUG_TAG, "CUTTER_LIST RESPONSE : " + cutterArrayList.toString());

                    if (cutterArrayList.size() == 0) {

                        emptyLayout.setVisibility(View.VISIBLE);
                        emptyLayout.setType(AppConstants.NO_BARBER, new EmptyLayout.onRefreshListner() {
                            @Override
                            public void onRefresh() {
                                getCutterList();
                                searchView.setText("");
                            }
                        });
                    } else {
                        emptyLayout.setVisibility(View.GONE);
                    }
                    adpCutter.notifyDataSetChanged();
                    showMap();
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

    public void getAllCategoryList() {
        final AsyncHttpClient client = new AsyncHttpClient();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        RequestParams params = new RequestParams();

        params.put("user_id", libFile.getUserId());
        params.put("user_token", libFile.getUserToken());

        Log.v(AppConstants.DEBUG_TAG, "CATEGORY_LIST REQUEST : " + params.toString());

        client.post(AppConstants.URL_LIST_CATEGORY, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

                //showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                progressDialog.dismiss();
                try {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.v(AppConstants.DEBUG_TAG, "CUTTER_LIST RESPONSE : " + response);

                    Category categoryAll=new Category();
                    categoryAll.setCategoryId("0");
                    categoryAll.setCategoryName("All");
                    categoryArrayList.clear();
                    categoryArrayList.add(categoryAll);
                    for (Category categoryResponse : ParseJson.parseCategoryList(response)) {
                        categoryArrayList.add(categoryResponse);
                    }
                    spCat.setItems(categoryArrayList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

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

    public void searchCutterList(String search) {

        cutterArrayList.clear();
        adpCutter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(true);
        final AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("user_id", libFile.getUserId());
        params.put("user_token", libFile.getUserToken());
        params.put("search_string", search);

        Log.v(AppConstants.DEBUG_TAG, "CUTTER_LIST REQUESR : " + params.toString());

        client.post(AppConstants.URL_SEARCH_CUTTER, params, new AsyncHttpResponseHandler() {
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
                        Log.v(AppConstants.DEBUG_TAG, "CUTTER_LIST RESPONSE : " + response);

                    cutterArrayList.clear();
                    cutterArrayList.addAll(ParseJson.parseCutterList(response).getInfo());
                    Log.v(AppConstants.DEBUG_TAG, "SEARCH CUTTER_LIST RESPONSE : " + cutterArrayList.toString());

                    if (cutterArrayList.size() == 0) {

                        emptyLayout.setVisibility(View.VISIBLE);
                        emptyLayout.setType(AppConstants.NO_BARBER, new EmptyLayout.onRefreshListner() {
                            @Override
                            public void onRefresh() {
                                searchView.setText("");
                                getCutterList();

                            }
                        });
                    } else {
                        emptyLayout.setVisibility(View.GONE);
                    }
                    adpCutter.notifyDataSetChanged();
                    showMap();
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
                        Log.v(AppConstants.DEBUG_TAG, "CUTTER LIST RESPONSE : FAILED : " + response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private double distance(double lat1, double lon1) {
        double theta = lon1 - Double.parseDouble(libFile.getLongitude());
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(Double.parseDouble(libFile.getLatitude())))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(Double.parseDouble(libFile.getLatitude())))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        // dist = Math.floor(dist * 100) / 100;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
