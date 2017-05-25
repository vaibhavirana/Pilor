package com.barodacoder.pilor.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.barodacoder.pilor.ActivityBase;
import com.barodacoder.pilor.AppConstants;
import com.barodacoder.pilor.R;
import com.barodacoder.pilor.model.UserData;
import com.barodacoder.pilor.utils.ParseJson;
import com.barodacoder.pilor.utils.Validator;
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
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class ActivityYourProfile extends ActivityBase {
    private Toolbar toolbar;

    private CircularImageView ivImage;

    private EditText etFullName, etEmail;

    private File file, fileSource;


    private ImagePicker imagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarGradiant(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_your_profile);

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

        ((TextView) findViewById(R.id.tvAddPhoto)).setTypeface(appData.getFontRegular());

        ivImage = (CircularImageView) findViewById(R.id.ivImage);

        if (!appData.getUserData().getProfile().equals("")) {
            Log.e("image", appData.getUserData().getProfile());
            if (appData.getUserData().getProfile().contains("graph.facebook.com")) {
                String profilePic = appData.getUserData().getProfile().replace("http", "https");

               // Glide.with(getApplicationContext()).load(profilePic).placeholder(R.drawable.icon_no_image).into(ivImage);
                Glide.with(this).load(profilePic)
                        .asBitmap().centerCrop()
                        .placeholder(R.drawable.user)
                        .into(new BitmapImageViewTarget(ivImage) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(ActivityYourProfile.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                ivImage.setImageDrawable(circularBitmapDrawable);
                            }
                        });

            } else {
                //Glide.with(getApplicationContext()).load(appData.getUserData().getProfile()).placeholder(R.drawable.icon_no_image).into(ivImage);
                Glide.with(this).load(appData.getUserData().getProfile())
                        .asBitmap().centerCrop()
                        .placeholder(R.drawable.user)
                        .into(new BitmapImageViewTarget(ivImage) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(ActivityYourProfile.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                ivImage.setImageDrawable(circularBitmapDrawable);
                            }
                        });
            }
        }

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker.startChooser(ActivityYourProfile.this, new ImagePicker.Callback() {
                    // 选择图片回调
                    @Override
                    public void onPickImage(Uri imageUri) {
                        Log.e("image url pick", imageUri.getPath());
                       /* ivImage.setImageURI(null);
                        ivImage.setImageURI(imageUri);*/
                        Glide.with(ActivityYourProfile.this).load(new File(imageUri.getPath())).asBitmap().centerCrop()
                                .into(new BitmapImageViewTarget(ivImage) {
                                    @Override
                                    protected void setResource(Bitmap resource) {
                                        RoundedBitmapDrawable circularBitmapDrawable =
                                                RoundedBitmapDrawableFactory.create(ActivityYourProfile.this.getResources(), resource);
                                        circularBitmapDrawable.setCircular(true);
                                        ivImage.setImageDrawable(circularBitmapDrawable);
                                    }
                                });

                        fileSource = new File(imageUri.getPath());
                        file = new File(getExternalCacheDir(), fileSource.getName());
                        try {
                            copyImage(fileSource , file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCropImage(Uri imageUri) {
                        Log.e("image url crop", imageUri.getPath());
                        Glide.with(ActivityYourProfile.this).load(new File(imageUri.getPath()))
                                .asBitmap().centerCrop().into(new BitmapImageViewTarget(ivImage) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(ActivityYourProfile.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                ivImage.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                        fileSource = new File(imageUri.getPath());
                        file = new File(getExternalCacheDir(), fileSource.getName());
                        try {
                            copyImage(fileSource , file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
        });

        etFullName = (EditText) findViewById(R.id.etFullName);
        etFullName.setTypeface(appData.getFontRegular());
        etFullName.setText(URLDecoder.decode(appData.getUserData().getDisplayName()));

        etEmail = (EditText) findViewById(R.id.etEmail);
        etEmail.setTypeface(appData.getFontRegular());
        etEmail.setText(appData.getUserData().getEmail());

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

        if (!Validator.validateNameNotNull(etEmail.getText().toString())) {
            showMsgDialog(getString(R.string.txt_err_email));

            return false;
        }

        if (!Validator.validateEmail(etEmail.getText().toString())) {
            showMsgDialog(getString(R.string.txt_err_valid_email));

            return false;
        }


        return true;
    }

    private void updateProfile() {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
       // Log.v(AppConstants.DEBUG_TAG, "FILE : " + file.toString());
        try {
            params.put("user_id", libFile.getUserId());
            params.put("user_token", libFile.getUserToken());
            params.put("display_name", etFullName.getText().toString());
            params.put("email", etEmail.getText().toString());
            // params.put("mobile", etPhone.getText().toString());
            //params.put("bio", etBio.getText().toString());
            params.put("profile_pic", file);

            Log.v(AppConstants.DEBUG_TAG, "UPDATE PROFILE REQQUEST : " + params.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        client.post(AppConstants.URL_UPDATE_PROFILE, params, new AsyncHttpResponseHandler() {
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

                        Toast.makeText(ActivityYourProfile.this, getString(R.string.txt_success_update_profile), Toast.LENGTH_LONG).show();

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

    protected void  copyImage(File src , File dest) throws IOException
    {
        InputStream inputStream = new FileInputStream(src);
        OutputStream outputStream = new FileOutputStream(dest);

        byte[] buffer = new byte[1024];

        int length;

        while ((length = inputStream.read(buffer)) > 0)
        {
            outputStream.write(buffer, 0, length);
        }

        if(AppConstants.DEBUG) Log.d(AppConstants.DEBUG_TAG, "File Copied !!! ");

        inputStream.close();

        outputStream.close();
    }

}
