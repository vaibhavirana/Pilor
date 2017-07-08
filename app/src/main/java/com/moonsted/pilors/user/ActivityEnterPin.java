package com.moonsted.pilors.user;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.moonsted.pilors.ActivityBase;
import com.moonsted.pilors.AppConstants;
import com.moonsted.pilors.R;
import com.moonsted.pilors.model.BookService;
import com.moonsted.pilors.model.BookingResp;
import com.moonsted.pilors.utils.ParseJson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nightonke.blurlockview.BlurLockView;
import com.nightonke.blurlockview.Password;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;

public class ActivityEnterPin extends ActivityBase implements BlurLockView.OnPasswordInputListener, BlurLockView.OnLeftButtonClickListener {
    private BlurLockView blurlockview;

    private boolean isFirstTime = true;
    private String passcode = "";
    private BookService bookService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarGradiant(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_enter_pin);

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

        bookService = null;

        bookService = (BookService) getIntent().getSerializableExtra("BOOK_SERVICE");

        // bid = (Bids) getIntent().getSerializableExtra("BID_TO_ADD");
    }

    private void initUi() {
        blurlockview = (BlurLockView) findViewById(R.id.blurlockview);

        blurlockview.setCorrectPassword(libFile.getPasscode());

        /*if(libFile.getPasscode().equals("6581") || passcode.equals(""))
        {
            blurlockview.setTitle(getString(R.string.txt_create_passcode));

            isFirstTime = true;
        }*/
        if (!libFile.getPasscode().equals("6581")) {
            passcode = libFile.getPasscode();

            if (bookService != null && bookService.price != null)
                blurlockview.setTitle(bookService.price+" DKK");
            else
                blurlockview.setTitle("Enter Pin");
            isFirstTime = false;
        } else {
            blurlockview.setTitle(getString(R.string.txt_create_passcode));
        }

        blurlockview.setLeftButton(getString(R.string.txt_cancel));
        blurlockview.setRightButton(getString(R.string.txt_delete));
        blurlockview.setTypeface(appData.getFontRegular());
        blurlockview.setType(Password.NUMBER, false);

        //blurlockview.show(500, ShowType.FROM_TOP_TO_BOTTOM, EaseType.Linear);

        blurlockview.setOnLeftButtonClickListener(this);
        blurlockview.setOnPasswordInputListener(this);
    }

    @Override
    public void correct(String inputPassword) {
        if (AppConstants.DEBUG)
            Log.v(AppConstants.DEBUG_TAG, "PASSWORD : CORRECT : " + inputPassword);

        //Toast.makeText(ActivityEnterPin.this, "Password Correct", Toast.LENGTH_SHORT).show();

        //blurlockview.hide(500, HideType.FROM_BOTTOM_TO_TOP, EaseType.Linear);

        doAction(inputPassword);
    }

    @Override
    public void incorrect(String inputPassword) {
        if (AppConstants.DEBUG)
            Log.v(AppConstants.DEBUG_TAG, "PASSWORD : INCORRECT : " + inputPassword);

        //Toast.makeText(ActivityEnterPin.this, "Incorrect password", Toast.LENGTH_SHORT).show();

        doAction(inputPassword);
    }

    @Override
    public void input(String inputPassword) {
        if (AppConstants.DEBUG)
            Log.v(AppConstants.DEBUG_TAG, "PASSWORD : INPUT : " + inputPassword);
    }

    public void doAction(String inputPassword) {
        if (isFirstTime) {
            isFirstTime = false;

            passcode = inputPassword;

            //libFile.setPasscode(inputPassword);

            initUi();

            blurlockview.setTitle("Enter Pin");
        } else {
            if (inputPassword.equals(passcode)) {
                libFile.setPasscode(passcode);

                if (libFile.getCardNumber().equals("")) {
                    goToAddCardScreen(bookService);

                    finish();
                } else {
                    if (bookService != null) {
                        bookService();
                    } else {
                        goToAddCardScreen(bookService);
                        finish();
                    }
                    // addBid();
                }
            } else {
                Toast.makeText(ActivityEnterPin.this, "Incorrect password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick() {
        onBackPressed();
    }

    private void bookService() {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        byte[] encryptedCardNum = encrypt(libFile.getCardNumber().getBytes(), AppConstants.AES128_KEY.getBytes(),
                "1234567890123456".getBytes());
        String encryptedCardNumber = new String(Base64.encodeToString(encryptedCardNum, 0));

        byte[] encryptedData = encrypt(libFile.getSubscriptionId().getBytes(), AppConstants.AES128_KEY.getBytes(),
                "1234567890123456".getBytes());
        String encryptedSubscriptionId = new String(Base64.encodeToString(encryptedData, 0));

        params.put("user_id", libFile.getUserId());
        params.put("user_token", libFile.getUserToken());
        params.put("service_provide_by", bookService.service_provide_by);
        params.put("date_of_booking", bookService.date_of_booking);
        params.put("booking_date", bookService.date_of_booking);
        params.put("service_id", bookService.service_id);
        // params.put("service_id", selectedService);
        params.put("price", bookService.price);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String datestring = dateFormat.format(date);
        params.put("localtime", datestring);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        params.put("localtime_UTC", dateFormat.format(date));
        params.put("instantcapture", "0");
        params.put("merchantnumber", AppConstants.MERCHANT_ID);
        params.put("subscriptionid", encryptedSubscriptionId);
        params.put("credit_card", encryptedCardNumber);

        Log.e("params", params.toString());
        if (AppConstants.DEBUG)
            //Log.v(AppConstants.DEBUG_TAG, "MY TASKS RESPONSE : FAILED : " + libFile.getUserId() + "::" + libFile.getUserToken());

            client.post(AppConstants.URL_BOOK_SERVICE, params, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();

                    showProgressDialog(getString(R.string.txt_service_booking));
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    cancelProgressDialog();

                    try {
                        String response = new String(responseBody, "UTF-8");

                        if (AppConstants.DEBUG)
                            Log.v(AppConstants.DEBUG_TAG, "ADD Book RESPONSE : " + response);

                        BookingResp resp = ParseJson.parseServiceBooking(response);

                        if (resp.msg.equals("OK")) {
                            Toast.makeText(ActivityEnterPin.this, getString(R.string.txt_service_book_success), Toast.LENGTH_LONG).show();

                            setResult(RESULT_OK);

                            goToBookingScreen();
                            //onBackPressed();
                            finish();
                        } else {
                            showOKAlertMsg(getString(R.string.txt_failed), getString(R.string.txt_service_book_failed), false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    cancelProgressDialog();

                    try {
                        String response = new String(responseBody, "UTF-8");

                        if (AppConstants.DEBUG)
                            Log.v(AppConstants.DEBUG_TAG, "ADD BID RESPONSE : FAILED : " + response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
    }


}
