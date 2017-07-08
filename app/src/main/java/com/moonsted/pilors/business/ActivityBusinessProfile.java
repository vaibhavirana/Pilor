package com.moonsted.pilors.business;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.moonsted.pilors.ActivityBase;
import com.moonsted.pilors.AppConstants;
import com.moonsted.pilors.R;
import com.moonsted.pilors.model.UserData;
import com.moonsted.pilors.utils.AppData;
import com.moonsted.pilors.utils.ParseJson;
import com.moonsted.pilors.utils.Validator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.linchaolong.android.imagepicker.ImagePicker;
import com.linchaolong.android.imagepicker.cropper.CropImage;
import com.linchaolong.android.imagepicker.cropper.CropImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;


public class ActivityBusinessProfile extends ActivityBase {
    private Toolbar toolbar;
    private RecyclerView rvGallery;
    private EditText etFullName, etBio, etAddress, etPhone;

    private File file, fileSource;

    private ImagePicker imagePicker;
    private AdapterPhoto adpPhoto;
    private ArrayList<String> image_url;
    private ArrayList<File> image_url_file;
    private UserData cutter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarGradiant(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_business_profile);

        initData();

        initUi();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.slide_in_from_left_to_right, R.anim.slide_out_from_left_to_right);

        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


    protected void initData() {
        super.initData();
        String dateCreated = dateFormat.format(new Date(System.currentTimeMillis()));

        file = new File(getExternalCacheDir(), "IMG-" + dateCreated.trim() + ".jpg");

        cutter = AppData.getInstance(this).getUserData();
        image_url = new ArrayList<>();
        image_url_file = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            image_url_file.add(file);
        }
        if (cutter != null) {
            if (cutter.getProfile().length() > 0)
                image_url.add(cutter.getProfile());
            else
                image_url.add("no_image");

            if (cutter.getCoverImage1().length() > 0)
                image_url.add(cutter.getCoverImage1());
            else
                image_url.add("no_image");
            if (cutter.getCoverImage2().length() > 0)
                image_url.add(cutter.getCoverImage2());
            else
                image_url.add("no_image");
            if (cutter.getCoverImage3().length() > 0)
                image_url.add(cutter.getCoverImage3());
            else
                image_url.add("no_image");
            if (cutter.getCoverImage4().length() > 0)
                image_url.add(cutter.getCoverImage4());
            else
                image_url.add("no_image");
            if (cutter.getCoverImage5().length() > 0)
                image_url.add(cutter.getCoverImage5());
            else
                image_url.add("no_image");

        }

        imagePicker = new ImagePicker();
// 设置标题
        imagePicker.setTitle("设置头像");
// 设置是否裁剪图片
        imagePicker.setCropImage(true);
// 启动图片选择器


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

        rvGallery = (RecyclerView) findViewById(R.id.rvGallery);
        rvGallery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adpPhoto = new AdapterPhoto();
        rvGallery.setAdapter(adpPhoto);
        etFullName = (EditText) findViewById(R.id.etFullName);
        etFullName.setTypeface(appData.getFontRegular());
        etFullName.setText(URLDecoder.decode(appData.getUserData().getDisplayName()));

        etBio = (EditText) findViewById(R.id.etBio);
        etBio.setTypeface(appData.getFontRegular());
        etBio.setText(appData.getUserData().getBio());

        etAddress = (EditText) findViewById(R.id.etAddress);
        etAddress.setTypeface(appData.getFontRegular());
        etAddress.setText(appData.getUserData().getAddress());

        etPhone = (EditText) findViewById(R.id.etPhone);
        etPhone.setTypeface(appData.getFontRegular());
        etPhone.setText(appData.getUserData().getMobile());

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
            validateProfileData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void validateProfileData() {
        hideSoftKeyboard();

        if (!validated())
            return;

        updateProfile();
    }

    private boolean validated() {
        if (!Validator.validateNameNotNull(etFullName.getText().toString())) {
            showMsgDialog(getString(R.string.txt_err_name));

            return false;
        }

       /* if (!Validator.validateNameNotNull(etEmail.getText().toString())) {
            showMsgDialog(getString(R.string.txt_err_email));

            return false;
        }

        if (!Validator.validateEmail(etEmail.getText().toString())) {
            showMsgDialog(getString(R.string.txt_err_valid_email));

            return false;
        }*/


        return true;
    }

    private void updateProfile() {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        try {
            params.put("user_id", libFile.getUserId());
            params.put("other_user_id", libFile.getUserId());
            params.put("user_token", libFile.getUserToken());
            params.put("display_name", etFullName.getText().toString());
            params.put("first_name", cutter.getFirstName());
            params.put("last_name", cutter.getLastName());
            params.put("email", cutter.getEmail());
            params.put("mobile", etPhone.getText().toString());
            params.put("bio", etBio.getText().toString());
            params.put("address", etAddress.getText().toString());
            params.put("profile_pic", file);
            // fileSource=new File(imageUri.getPath());
            Log.e("file ", image_url_file.size() + " || " + image_url_file.get(0).toString());

            params.put("cover_image1", image_url_file.get(0));
            params.put("thumb_image1", resizeImage(image_url_file.get(0)));
            params.put("cover_image2", image_url_file.get(1));
            params.put("thumb_image2", file);
            params.put("cover_image3", image_url_file.get(2));
            params.put("thumb_image3", file);
            params.put("cover_image4", image_url_file.get(3));
            params.put("thumb_image4", file);
            params.put("cover_image5", image_url_file.get(4));
            params.put("thumb_image5", file);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("param", params.toString());
        client.post(AppConstants.URL_UPDATE_CUTTER_PROFILE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

                showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                cancelProgressDialog();

                try {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.v(AppConstants.DEBUG_TAG, "UPDATE PROFILE RESPONSE : " + response);

                    UserData userData = ParseJson.parseSignUp(response);

                    if (userData.getStatusCode() == 1) {
                        appData.setUserData(userData);

                        libFile.setUserId(appData.getUserData().getUserId());

                        libFile.setUserToken(appData.getUserData().getUserToken());

                        libFile.setEmailId(userData.getEmail());

                        Toast.makeText(ActivityBusinessProfile.this, getString(R.string.txt_success_update_profile), Toast.LENGTH_LONG).show();

                        onBackPressed();
                    } else {
                        showOKAlertMsg(getString(R.string.txt_failed), getString(R.string.txt_failed_update_profile), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    goToLandingScreen();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                cancelProgressDialog();

                try {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.v(AppConstants.DEBUG_TAG, "UPDATE PROFILE RESPONSE : FAILED : " + response);

                    showOKAlertMsg(getString(R.string.txt_failed), getString(R.string.txt_failed_update_profile), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private File resizeImage(File file_url) {
        Bitmap b = BitmapFactory.decodeFile(file_url.getPath());
        Bitmap out = Bitmap.createScaledBitmap(b, 400, 400, false);
        // File file1 = new File(file_url, "resize.png");
        File file1 = new File(getExternalCacheDir(), file_url.getName());
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file1);
            out.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            b.recycle();
            out.recycle();
            copyImage(file1, file);
        } catch (Exception e) {
        }

        return file1;
    }

    public class AdapterPhoto extends RecyclerView.Adapter<AdapterPhoto.MyViewHolder> {
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private CircularImageView ivImage;

            public MyViewHolder(View view) {
                super(view);
                ivImage = (CircularImageView) view.findViewById(R.id.ivImage);
            }
        }

        public AdapterPhoto() {
            //this.moviesList = moviesList;
        }

        @Override
        public AdapterPhoto.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cover_photo, parent, false);
            return new AdapterPhoto.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final AdapterPhoto.MyViewHolder holder, final int position) {

            int default_image;
            if (position == 0)
                default_image = R.drawable.user;
            else
                default_image = R.drawable.default_image;

            if (image_url.get(position).equals("no_image")) {
                Glide.with(ActivityBusinessProfile.this)
                        .load(default_image)
                        .asBitmap().centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(new BitmapImageViewTarget(holder.ivImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(ActivityBusinessProfile.this.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder.ivImage.setImageDrawable(circularBitmapDrawable);
                    }
                });

            } else {
                Glide.with(ActivityBusinessProfile.this).load(image_url.get(position))
                        .asBitmap().centerCrop()
                        .placeholder(default_image)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(new BitmapImageViewTarget(holder.ivImage) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(ActivityBusinessProfile.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                holder.ivImage.setImageDrawable(circularBitmapDrawable);
                            }
                        });
            }





        holder.ivImage.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
            imagePicker.startChooser(ActivityBusinessProfile.this, new ImagePicker.Callback() {
                // 选择图片回调
                @Override
                public void onPickImage(Uri imageUri) {
                    Log.e("image url", imageUri.getPath());
                       /* ivImage.setImageURI(null);
                        ivImage.setImageURI(imageUri);*/
                    fileSource = new File(imageUri.getPath());
                    file = new File(getExternalCacheDir(), fileSource.getName());
                    try {
                        copyImage(fileSource, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    image_url_file.set(position, file);
                    Log.e("image set at", position + "");
                    Glide.with(ActivityBusinessProfile.this).load(new File(imageUri.getPath())).asBitmap().centerCrop()
                            .into(new BitmapImageViewTarget(holder.ivImage) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable circularBitmapDrawable =
                                            RoundedBitmapDrawableFactory.create(ActivityBusinessProfile.this.getResources(), resource);
                                    circularBitmapDrawable.setCircular(true);
                                    holder.ivImage.setImageDrawable(circularBitmapDrawable);
                                }
                            });

                }

                @Override
                public void onCropImage(Uri imageUri) {
                    Log.e("image url", imageUri.getPath());
                    fileSource = new File(imageUri.getPath());
                    file = new File(getExternalCacheDir(), fileSource.getName());
                    try {
                        copyImage(fileSource, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    image_url_file.set(position, file);
                    Glide.with(ActivityBusinessProfile.this).load(fileSource)
                            .asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.ivImage) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(ActivityBusinessProfile.this.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            holder.ivImage.setImageDrawable(circularBitmapDrawable);
                        }
                    });

                }

                @Override
                public void cropConfig(CropImage.ActivityBuilder builder) {
                    builder
                            .setMultiTouchEnabled(false)
                            .setGuidelines(CropImageView.Guidelines.OFF)
                            .setCropShape(CropImageView.CropShape.RECTANGLE)
                            .setRequestedSize(960, 540);
                    //.setAspectRatio(16, 9);
                }

                // 用户拒绝授权回调
                @Override
                public void onPermissionDenied(int requestCode, String[] permissions,
                                               int[] grantResults) {
                }
            });
        }
        }

        );

    }

    @Override
    public int getItemCount() {

        //return 5;
        return image_url.size();
    }

}

    protected void copyImage(File src, File dest) throws IOException {
        InputStream inputStream = new FileInputStream(src);
        OutputStream outputStream = new FileOutputStream(dest);

        byte[] buffer = new byte[1024];

        int length;

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        if (AppConstants.DEBUG) Log.d(AppConstants.DEBUG_TAG, "File Copied !!! ");

        inputStream.close();

        outputStream.close();
    }
}
