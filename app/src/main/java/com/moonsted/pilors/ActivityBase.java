package com.moonsted.pilors;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.moonsted.pilors.business.ActivityBusinessChangePassword;
import com.moonsted.pilors.business.ActivityBusinessMain;
import com.moonsted.pilors.business.ActivityBusinessOpeningHours;
import com.moonsted.pilors.business.ActivityBusinessPaymentHistory;
import com.moonsted.pilors.business.ActivityBusinessProfile;
import com.moonsted.pilors.business.ActivityBusinessService;
import com.moonsted.pilors.business.ActivityBusinessSetting;
import com.moonsted.pilors.model.BookService;
import com.moonsted.pilors.user.ActivityAddCard;
import com.moonsted.pilors.user.ActivityBooking;
import com.moonsted.pilors.user.ActivityChangePasscode;
import com.moonsted.pilors.user.ActivityCreatePayment;
import com.moonsted.pilors.user.ActivityCutterProfile;
import com.moonsted.pilors.user.ActivityEnterPin;
import com.moonsted.pilors.user.ActivitySetting;
import com.moonsted.pilors.user.ActivityYourProfile;
import com.moonsted.pilors.user.MainActivity;
import com.moonsted.pilors.utils.AppData;
import com.moonsted.pilors.utils.LibFile;

import java.text.SimpleDateFormat;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ActivityBase extends AppCompatActivity {
    public static AppData appData;

    public static LibFile libFile;

    protected ProgressDialog progressDialog;

    protected SimpleDateFormat dateFormat;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.action_bar_bg);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(R.color.transparent));
            window.setBackgroundDrawable(background);

        }
    }

    protected void initData() {
        appData = AppData.getInstance(getApplicationContext());

        libFile = LibFile.getInstance(getApplicationContext());

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    protected void goToLandingScreen() {
        Intent intent = new Intent(getApplicationContext(), LandingActivity.class);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);

        finish();
    }

    protected void goToLoginScreen(boolean isBusinessLogin) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

        intent.putExtra("IS_BUSINESS_LOGIN", isBusinessLogin);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
    }

   /* protected void goToForgotPasswordScreen()
    {
        Intent intent = new Intent(getApplicationContext(), ActivityForgotPassword.class);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
    }*/

    protected void goToSignupScreen() {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
    }


    protected void goToHomeScreen() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);

        finish();
    }

    protected void goToSettingScreen() {
        Intent intent = new Intent(getApplicationContext(), ActivitySetting.class);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
    }

    protected void goToYourProfileScreen() {
        Intent intent = new Intent(getApplicationContext(), ActivityYourProfile.class);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
    }

    protected void goToLandingWithClearCache() {
        Intent intent = new Intent(getApplicationContext(), LandingActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

        overridePendingTransition(R.anim.rotate_in, R.anim.rotate_out);

        finish();
    }

    protected void goToBookingScreen() {
        Intent intent = new Intent(getApplicationContext(), ActivityBooking.class);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
    }

    protected void goToEnterPinScreen(BookService bookService) {
        Intent intent = new Intent(getApplicationContext(), ActivityEnterPin.class);

        intent.putExtra("BOOK_SERVICE", bookService);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
    }

    protected void goToCutterDetailPage() {
        Intent intent = new Intent(getApplicationContext(), ActivityCutterProfile.class);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
    }


    protected void goToAddCardScreen(BookService bookService) {
        Intent intent = new Intent(getApplicationContext(), ActivityAddCard.class);

        intent.putExtra("BOOK_SERVICE", bookService);

        startActivityForResult(intent, 1);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);

        //finish();
    }


    protected void goToCreatePaymentScreen(BookService bookService) {
        Intent intent = new Intent(getApplicationContext(), ActivityCreatePayment.class);

        intent.putExtra("BOOK_SERVICE", bookService);

        startActivityForResult(intent, 1);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
    }

    protected void goToBusinessMainScreen() {
        Intent intent = new Intent(getApplicationContext(), ActivityBusinessMain.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);

        finish();
    }

    protected void goToBusinessSettingScreen() {
        Intent intent = new Intent(getApplicationContext(), ActivityBusinessSetting.class);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
    }

    protected void goToBusinessPaymentHistoryScreen() {
        Intent intent = new Intent(getApplicationContext(), ActivityBusinessPaymentHistory.class);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
    }


    protected void goToChangePasswordScreen()
    {
        Intent intent = new Intent(getApplicationContext(), ActivityBusinessChangePassword.class);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
    }

    protected void goToBusinessOpeningHoursScreen() {
        Intent intent = new Intent(getApplicationContext(), ActivityBusinessOpeningHours.class);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
    }

    protected void goToBusinessServiceScreen() {
        Intent intent = new Intent(getApplicationContext(), ActivityBusinessService.class);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
    }

    protected void goToBusinessProfileScreen() {
        Intent intent = new Intent(getApplicationContext(), ActivityBusinessProfile.class);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
    }

    protected void goToChangePasscodeScreen() {
        Intent intent = new Intent(getApplicationContext(), ActivityChangePasscode.class);

        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
    }

    protected void dialNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        startActivity(intent);
    }

    public void sendSMS(String number)
    {
      //  String number = "12346556";  // The number on which you want to send SMS
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
    }

    public void showMap(String displayName,float lat, float lng)
    {

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q="+lat+","+lng+"&z=16 (" + displayName + ")"));
        startActivity(intent);
    }
    protected void loadWebsite(String website) {
        if (!website.contains("http"))
            website = "http://".concat(website);

        if (AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "URL LINK : " + website);

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(website));
        startActivity(i);
    }

    protected void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    protected void showSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(Color.BLACK);

        TextView tv = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        tv.setTypeface(appData.getFontRegular());

        snackbar.show();

        //Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    protected void showProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        progressDialog = new ProgressDialog(this);

        //progressDialog.setTitle(title);

        progressDialog.setMessage(getString(R.string.txt_loading));

        progressDialog.setCancelable(false);

        progressDialog.show();
    }

    protected void showProgressDialog(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        progressDialog = new ProgressDialog(this);

        //progressDialog.setTitle(title);

        progressDialog.setMessage(msg);

        progressDialog.setCancelable(false);

        progressDialog.show();
    }

    protected void cancelProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    protected void showOKAlertMsg(String title, String msg, final boolean isFinish) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setTitle(title);

        dialogBuilder.setMessage(msg);

        dialogBuilder.setPositiveButton(getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isFinish) {
                    finish();
                }
                dialog.dismiss();
            }
        });

        dialogBuilder.show();
    }

    protected void showMsgDialog(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected boolean isNetworkAvailable() {

        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    protected static byte[] encrypt(byte[] data, byte[] key, byte[] ivs) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//AES/CBC/PKCS5Padding
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            byte[] finalIvs = new byte[16];
            int len = ivs.length > 16 ? 16 : ivs.length;
            //System.arraycopy(ivs, 0, finalIvs, 0, len);
            IvParameterSpec ivps = new IvParameterSpec(finalIvs);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivps);

            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected static byte[] decrypt(byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(AppConstants.AES128_KEY.getBytes("utf-8"), "AES");
            byte[] finalIvs = new byte[16];
            int len = "1234567890123456".getBytes("utf-8").length > 16 ? 16 : "1234567890123456".getBytes("utf-8").length;
            //System.arraycopy(ivs, 0, finalIvs, 0, len);
            IvParameterSpec ivps = new IvParameterSpec(finalIvs);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivps);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
