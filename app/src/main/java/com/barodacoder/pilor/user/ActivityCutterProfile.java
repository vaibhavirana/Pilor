package com.barodacoder.pilor.user;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.barodacoder.pilor.ActivityBase;
import com.barodacoder.pilor.AppConstants;
import com.barodacoder.pilor.R;
import com.barodacoder.pilor.custom.CustomDateTimePicker;
import com.barodacoder.pilor.custom.GridViewItem;
import com.barodacoder.pilor.model.BookService;
import com.barodacoder.pilor.model.Rating;
import com.barodacoder.pilor.model.Service;
import com.barodacoder.pilor.model.UserCutter;
import com.barodacoder.pilor.rating.ProperRatingBar;
import com.barodacoder.pilor.utils.AppData;
import com.barodacoder.pilor.utils.ParseJson;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class ActivityCutterProfile extends ActivityBase implements View.OnClickListener {
    private Toolbar toolbar;

    private CircularImageView ivImage;
    private ImageView opacityFilter,opacityFilter1;
    private Button btnOrderCut;
    private RecyclerView rvService, rvPhoto, rvRate;
    private UserCutter userCutter;
    private ProperRatingBar ratingBar;

    private RadioGroup radioGroup;
    private AdapterPhoto adpPhoto;
    private LinearLayout llPrice;
    ArrayList<String> image_url;
    ArrayList<Service> services = new ArrayList<>();
    ArrayList<Rating> user_ratings = new ArrayList<>();
    private AdapterRate adpRate;
    private AdapterService adpService;

    private String selectedDate;
    private double totalPrice = 0;
    public ArrayList<String> selectedIds;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarGradiant(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cutter_profile);

        userCutter = (UserCutter) getIntent().getSerializableExtra("user");
        Log.e("user", userCutter.getDisplayName().toString());

        image_url = new ArrayList<>();
        if (userCutter != null) {
            if (userCutter.getCoverImage1().length() > 0)
                image_url.add(userCutter.getCoverImage1());
            if (userCutter.getCoverImage2().length() > 0)
                image_url.add(userCutter.getCoverImage2());
            if (userCutter.getCoverImage3().length() > 0)
                image_url.add(userCutter.getCoverImage3());
            if (userCutter.getCoverImage4().length() > 0)
                image_url.add(userCutter.getCoverImage4());
            if (userCutter.getCoverImage5().length() > 0)
                image_url.add(userCutter.getCoverImage5());

            if (userCutter.getService_info() != null)
                Log.e("service", userCutter.getService_info().toString());
            services.addAll(userCutter.getService_info());


        }
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
        ((TextView) findViewById(R.id.txtBio)).setTypeface(appData.getFontMedium());
        ((TextView) findViewById(R.id.tvTitle)).setText(userCutter.getDisplayName());
        ((TextView) findViewById(R.id.txtBio)).setText(userCutter.getBio());

        ratingBar = (ProperRatingBar) findViewById(R.id.rating);
        //ratingBar.setRating(4);
        ratingBar.setRating(userCutter.getAvarage_rating());
        ivImage = (CircularImageView) findViewById(R.id.ivImage);
        opacityFilter = (ImageView) findViewById(R.id.opacityFilter);
        opacityFilter1 = (ImageView) findViewById(R.id.opacityFilter1);
        btnOrderCut = (Button) findViewById(R.id.btnOrderCut);
        btnOrderCut.setOnClickListener(this);
        rvPhoto = (RecyclerView) findViewById(R.id.rvPhoto);
        rvRate = (RecyclerView) findViewById(R.id.rvRate);
        rvRate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adpRate = new AdapterRate();
        rvRate.setAdapter(adpRate);
        getUserRatings();

        llPrice = (LinearLayout) findViewById(R.id.llPrice);

        setPriceLayout();

        Glide.with(getApplicationContext()).load(R.drawable.default_image)
                //.placeholder(R.drawable.default_image)
                .bitmapTransform(new BlurTransformation(this, 30))
                .into(opacityFilter1);
        Glide.with(this)
                .load(userCutter.getProfile())
               // .placeholder(R.drawable.default_image)
                .bitmapTransform(new BlurTransformation(this, 30))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        opacityFilter1.setVisibility(View.GONE);
                         return false;
                    }
                })
                .into(opacityFilter);
        Glide.with(getApplicationContext()).load(userCutter.getProfile()).asBitmap().centerCrop()
                .placeholder(R.drawable.user)
                .into(new BitmapImageViewTarget(ivImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        ivImage.setImageDrawable(circularBitmapDrawable);
                    }
                });


        radioGroup = (RadioGroup) findViewById(R.id.segmented3);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioPrice:
                        setPriceLayout();
                        break;

                    case R.id.radioPhoto:
                        setPhotoLayout();
                        break;

                    case R.id.radioRate:
                        setRateLayout();
                        break;
                }
            }
        });
    }

    private void setPriceLayout() {
        llPrice.setVisibility(View.VISIBLE);
        rvPhoto.setVisibility(View.GONE);
        rvRate.setVisibility(View.GONE);
        ((TextView) findViewById(R.id.txtService)).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.txtService)).setTypeface(appData.getFontMedium());
        rvService = (RecyclerView) findViewById(R.id.rvDetail);
        rvService.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adpService = new AdapterService();
        rvService.setAdapter(adpService);
    }

    private void setPhotoLayout() {
        rvPhoto.setVisibility(View.VISIBLE);
        llPrice.setVisibility(View.GONE);
        rvRate.setVisibility(View.GONE);
        ((TextView) findViewById(R.id.txtService)).setVisibility(View.GONE);
        rvPhoto.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        //rvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adpPhoto = new AdapterPhoto();
        rvPhoto.setAdapter(adpPhoto);
    }

    private void setRateLayout() {

        rvPhoto.setVisibility(View.GONE);
        llPrice.setVisibility(View.GONE);
        rvRate.setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.txtService)).setVisibility(View.GONE);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOrderCut:
                if (selectedIds.size() > 0) {
                    // showDateTimePicker();
                    datePickerDialog();
                } else
                    showOKAlertMsg(getString(R.string.txt_select_service_title), getString(R.string.txt_select_service), false);
                break;
        }
    }

    private void showDateTimePicker() {

        CustomDateTimePicker custom = new CustomDateTimePicker(this,
                new CustomDateTimePicker.ICustomDateTimeListener() {

                    @Override
                    public void onSet(Dialog dialog, Calendar calendarSelected,
                                      Date dateSelected, int year, String monthFullName,
                                      String monthShortName, int monthNumber, int date,
                                      String weekDayFullName, String weekDayShortName,
                                      int hour24, int hour12, int min, int sec,
                                      String AM_PM) {

                        selectedDate = year
                                + "-" + (monthNumber + 1) + "-" + calendarSelected.get(Calendar.DAY_OF_MONTH)
                                + " " + hour24 + ":" + min
                                + ":" + sec;

                        Log.e("selected date", date + " " + year
                                + "-" + (monthNumber + 1) + "-" + calendarSelected.get(Calendar.DAY_OF_MONTH)
                                + " " + hour24 + ":" + min
                                + ":" + sec);


                    }

                    @Override
                    public void onCancel() {

                    }
                });
        /**
         * Pass Directly current time format it will return AM and PM if you set
         * false
         */
        custom.set24HourFormat(true);
        /**
         * Pass Directly current data and time to show when it pop up
         */
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        custom.setDate(c);

        custom.showDialog();
    }


    public class AdapterPhoto extends RecyclerView.Adapter<AdapterPhoto.MyViewHolder> {
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private GridViewItem ivImage;

            public MyViewHolder(View view) {
                super(view);
                ivImage = (GridViewItem) view.findViewById(R.id.ivImage);
            }
        }

        public AdapterPhoto() {
            //this.moviesList = moviesList;
        }

        @Override
        public AdapterPhoto.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
            return new AdapterPhoto.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final AdapterPhoto.MyViewHolder holder, int position) {

            Glide.with(getApplicationContext()).load(image_url.get(position)).centerCrop()
                    .placeholder(R.drawable.icon_no_image)
                    .into(holder.ivImage);
        }

        @Override
        public int getItemCount() {

            //return 5;
            return image_url.size();
        }
    }

    public class AdapterRate extends RecyclerView.Adapter<AdapterRate.MyViewHolder> {
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivImage;
            private ProperRatingBar ratingBar;
            private TextView tvName, tvComment;

            public MyViewHolder(View view) {
                super(view);
                ivImage = (ImageView) view.findViewById(R.id.ivImage);
                tvName = (TextView) view.findViewById(R.id.tvName);
                tvComment = (TextView) view.findViewById(R.id.tvComment);
                ratingBar = (ProperRatingBar) view.findViewById(R.id.rating);
            }
        }

        public AdapterRate() {
            //this.moviesList = moviesList;
        }

        @Override
        public AdapterRate.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rating, parent, false);
            return new AdapterRate.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final AdapterRate.MyViewHolder holder, int position) {

            Rating rating = user_ratings.get(position);
          /*  Glide.with(getApplicationContext()).load(rating.getProfile_pic()).centerCrop()
                    .placeholder(R.drawable.user)
                    .into(holder.ivImage);*/
            Glide.with(getApplicationContext()).load(rating.getProfile_pic()).asBitmap().centerCrop()
                    .placeholder(R.drawable.user)
                    .into(new BitmapImageViewTarget(holder.ivImage) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            holder.ivImage.setImageDrawable(circularBitmapDrawable);
                        }
                    });

            holder.tvName.setText(URLDecoder.decode(rating.getDisplay_name()));
            holder.ratingBar.setRating(rating.getReview_star());
            holder.tvComment.setText(URLDecoder.decode(rating.getReview_text()));
        }

        @Override
        public int getItemCount() {

            //return 5;
            return user_ratings.size();
        }
    }

    public class AdapterService extends RecyclerView.Adapter<AdapterService.MyViewHolder> {
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView imgSelect;
            private RelativeLayout rlMyProfile;
            private TextView tvPrice, tvServiceTitle;

            public MyViewHolder(View view) {
                super(view);
                imgSelect = (ImageView) view.findViewById(R.id.imgSelect);
                tvPrice = (TextView) view.findViewById(R.id.tvPrice);
                tvServiceTitle = (TextView) view.findViewById(R.id.tvServiceTitle);
                rlMyProfile = (RelativeLayout) view.findViewById(R.id.rlMyProfile);

                tvPrice.setTypeface(AppData.getInstance(ActivityCutterProfile.this).getFontRegular());
                tvServiceTitle.setTypeface(AppData.getInstance(ActivityCutterProfile.this).getFontRegular());
            }
        }

        public AdapterService() {
            //this.moviesList = moviesList;
            selectedIds = new ArrayList<String>();
        }

        @Override
        public AdapterService.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
            return new AdapterService.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final AdapterService.MyViewHolder holder, final int position) {
            final boolean[] isSelect = {false};
            holder.tvServiceTitle.setText(URLDecoder.decode(services.get(position).getService_name()));
            holder.tvPrice.setText(URLDecoder.decode(services.get(position).getRate()) + " DKK");

        /*    holder.imgSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isSelect[0]) {
                        isSelect[0] = false;
                        // selectedService=services.get(position)+",";
                        // Log.e("deselected",selectedIds.toString() +" || "+selectedIds.contains(services.get(position).getService_id()));

                        holder.imgSelect.setImageDrawable(getResources().getDrawable(R.drawable.checkmark_unselected));
                        selectedIds.remove(services.get(position).getService_id());
                        totalPrice = totalPrice - Double.parseDouble(services.get(position).getRate());
                        if (selectedIds.contains(services.get(position).getService_id())) {

                            selectedIds.remove(services.get(position).getService_id());
                            totalPrice = totalPrice - Double.parseDouble(services.get(position).getRate());
                            Log.e("deselected", selectedIds.toString());
                        } else {
                            selectedIds.add(services.get(position).getService_id());
                            totalPrice = totalPrice + Double.parseDouble(services.get(position).getRate());
                        }
                        //Log.e("deselected",selectedIds.toString() +" || "+selectedIds.contains(services.get(position).getService_id()));

                    } else {
                        isSelect[0] = true;
                        holder.imgSelect.setImageDrawable(getResources().getDrawable(R.drawable.checkmark_selected));
                        if (selectedIds.contains(services.get(position).getService_id())) {

                            selectedIds.remove(services.get(position).getService_id());
                            totalPrice = totalPrice - Double.parseDouble(services.get(position).getRate());
                            Log.e("deselected", selectedIds.toString());
                        } else {
                            selectedIds.add(services.get(position).getService_id());
                            totalPrice = totalPrice + Double.parseDouble(services.get(position).getRate());
                        }
                        // Log.e("selected",selectedIds.toString());
                    }

                }
            });*/

            holder.rlMyProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSelect[0]) {
                        isSelect[0] = false;
                        // selectedService=services.get(position)+",";
                     /*   selectedIds.remove(services.get(position).getService_id());
                        totalPrice = totalPrice - Double.parseDouble(services.get(position).getRate());*/
                        holder.imgSelect.setImageDrawable(getResources().getDrawable(R.drawable.checkmark_unselected));
                        if (selectedIds.contains(services.get(position).getService_id())) {

                            selectedIds.remove(services.get(position).getService_id());
                            totalPrice = totalPrice - Double.parseDouble(services.get(position).getRate());
                            Log.e("deselected", selectedIds.toString());
                        } else {
                            selectedIds.add(services.get(position).getService_id());
                            totalPrice = totalPrice + Double.parseDouble(services.get(position).getRate());
                        }

                    } else {
                        isSelect[0] = true;
                        holder.imgSelect.setImageDrawable(getResources().getDrawable(R.drawable.checkmark_selected));
                        // selectedService=services.get(position).getService_id();
                       /* totalPrice= Double.parseDouble(services.get(position).getRate());
                        selectedIds.add(services.get(position).getService_id());
                        totalPrice = totalPrice + Double.parseDouble(services.get(position).getRate());
*/
                        if (selectedIds.contains(services.get(position).getService_id())) {

                            selectedIds.remove(services.get(position).getService_id());
                            totalPrice = totalPrice - Double.parseDouble(services.get(position).getRate());
                            Log.e("deselected", selectedIds.toString());
                        } else {
                            selectedIds.add(services.get(position).getService_id());
                            totalPrice = totalPrice + Double.parseDouble(services.get(position).getRate());
                        }
                    }


                }
            });
        }

        @Override
        public int getItemCount() {

            //return 5;
            return services.size();
        }
    }


    public void getUserRatings() {

        user_ratings.clear();
        adpRate.notifyDataSetChanged();
        final AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("user_id", libFile.getUserId());
        params.put("user_token", libFile.getUserToken());
        params.put("cutter_id", userCutter.getUserId());

        Log.v(AppConstants.DEBUG_TAG, "CUTTER_RATING_LIST REQUEST : " + params.toString());

        client.post(AppConstants.URL_LIST_RATING, params, new AsyncHttpResponseHandler() {
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
                        Log.v(AppConstants.DEBUG_TAG, "CUTTER_LIST RESPONSE : " + response);

                    user_ratings.clear();
                    user_ratings.addAll(ParseJson.parseCutterRatingList(response).getInfo());
                    Log.v(AppConstants.DEBUG_TAG, "SEARCH CUTTER_LIST RESPONSE : " + user_ratings.toString());

                  /*  if (user_ratings.size() == 0) {

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
                    }*/
                    adpRate.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

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

    public void datePickerDialog() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Log.e("date:", dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        selectedDate = year
                                + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                        timePickerDialog(c);
                    }
                }, mYear, mMonth, mDay);
        c.add(Calendar.DAY_OF_YEAR, 1);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    public void timePickerDialog(Calendar c) {

        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        Log.e("time", hourOfDay + ":" + minute);
                        selectedDate = selectedDate + " " + hourOfDay + ":" + minute
                                + ":00";

                        Log.e("time", selectedDate);

                        BookService bookService = new BookService();
                        bookService.service_id = android.text.TextUtils.join(",", selectedIds);
                        bookService.date_of_booking = selectedDate;
                        bookService.service_provide_by = userCutter.getUserId();
                        bookService.price = String.valueOf(totalPrice);
                        goToEnterPinScreen(bookService);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }
}
