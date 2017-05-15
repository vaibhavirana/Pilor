package com.barodacoder.pilor.user;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.barodacoder.pilor.ActivityBase;
import com.barodacoder.pilor.AppConstants;
import com.barodacoder.pilor.R;
import com.barodacoder.pilor.model.BookService;
import com.barodacoder.pilor.model.BookingResp;
import com.barodacoder.pilor.utils.ParseJson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;
import eu.epay.library.EpayWebView;
import eu.epay.library.PaymentResultListener;

public class ActivityCreatePayment extends ActivityBase
{
    private WebView wvPayment;

    private Map<String, String> data;

  /*  private Tasks selectedTask;
    private Bids bid;*/

    private String transactionId = "", encryptedTransactionId = "";
    private String recipt = "";
    private String merchantNumber = "";
    private String subscriptionId = "", encryptedSubscriptionId = "";
    private String cardNumber = "", encryptedCardNumber = "";

    private boolean isPaymentAcepted = true;
    private BookService bookService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setStatusBarGradiant(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_payment);

        initData();

        initUi();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        overridePendingTransition(R.anim.slide_in_from_left_to_right, R.anim.slide_out_from_left_to_right);

        finish();
    }

    protected void initData()
    {
        super.initData();

      /*  selectedTask = new Tasks();

        selectedTask = (Tasks) getIntent().getSerializableExtra("SELECTED_TASK");

        bid = (Bids) getIntent().getSerializableExtra("BID_TO_ADD");
*/

        bookService =null;

        bookService = (BookService) getIntent().getSerializableExtra("BOOK_SERVICE");

        data = new HashMap<String, String>();

        data.put("merchantnumber", AppConstants.MERCHANT_ID);
        data.put("currency", "DKK");//DKK = 208
        data.put("amount", "0");
        data.put("language", "0");

        data.put("paymentcollection", "1");
        data.put("lockpaymentcollection", "1");
        data.put("encoding", "UTF-8");
        data.put("instantcapture", "1");
        data.put("ordertext", "save card");
        data.put("description", "Android");
        //data.put("paymenttype", "4"); //4 = Master Card
        data.put("subscription", "1");
        data.put("subscriptionname", libFile.getUserId());
    }

    private void initUi()
    {
        wvPayment = (WebView) findViewById(R.id.wvPayment);

        EpayWebView paymentView = new EpayWebView(new MyPaymentListener(), wvPayment, false);

        wvPayment = paymentView.LoadPaymentWindow(data);
    }

    private class MyPaymentListener implements PaymentResultListener
    {
        @Override
        public void Debug(String arg0)
        {

        }

        @Override
        public void ErrorOccurred(int arg0, String arg1, String arg2)
        {
            Toast.makeText(ActivityCreatePayment.this, "Payment unsuccessful..!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void PaymentAccepted(Map<String, String> arg0)
        {
            if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "PAYMENT ACCEPTED : " + arg0.toString());

            //Toast.makeText(ActivityCreatePayment.this, "", Toast.LENGTH_LONG).show();
            if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "PaymentAccepted : "+arg0 + "\n" + arg0.get("txnid")
                    + " :: " + arg0.get("cardno") + " :: " + arg0.get("orderid"));
            try
            {
                transactionId = arg0.get("txnid");

                recipt = arg0.get("orderid");

                subscriptionId = arg0.get("subscriptionid");

                cardNumber = arg0.get("cardno");

                byte[] encryptedData = encrypt(subscriptionId.getBytes(), AppConstants.AES128_KEY.getBytes(),
                        "1234567890123456".getBytes());
                encryptedSubscriptionId = new String(Base64.encodeToString(encryptedData, 0));
                if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "ENCODED SUB : "+encryptedSubscriptionId);

                libFile.setSubscriptionId(subscriptionId);
                //libFile.setSubscriptionId(encryptedSubscriptionId);

                byte[] encryptedCardNum = encrypt(cardNumber.getBytes(), AppConstants.AES128_KEY.getBytes(),
                        "1234567890123456".getBytes());

                encryptedCardNumber = new String(Base64.encodeToString(encryptedCardNum, 0));
                if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "ENCODED CARD : "+encryptedCardNumber);

                libFile.setCardNumber(cardNumber);
                //libFile.setCardNumber(encryptedCardNum);

                libFile.setPaymentType(arg0.get("paymenttype"));

                if(isPaymentAcepted)
                {
                    isPaymentAcepted = false;

                    bookService();
                }
                /*
                1 = DKK
                3 =  VISA
                4 = Master Card
                 */

                //onBackPressed();

               /* if(selectedTask.getTaskId().equals(""))
                    onBackPressed();
                else
                {
                    if(isPaymentAcepted)
                    {
                        isPaymentAcepted = false;

                        addBid();
                    }
                }*/

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            //new AsyncSaveTransaction().execute();
        }

        @Override
        public void PaymentLoadingAcceptPage()
        {
            if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "PAYMENT LOADING ACCEPT PAGE");
        }

        @Override
        public void PaymentWindowCancelled()
        {
            if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "PAYMENT WINDOW CANCELLED");
        }

        @Override
        public void PaymentWindowLoaded()
        {
            cancelProgressDialog();
        }

        @Override
        public void PaymentWindowLoading()
        {
            showProgressDialog();
        }
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
        params.put("service_id",bookService.service_id);
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
                    progressDialog.setMessage("Booking..");
                    showProgressDialog();
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
                            Toast.makeText(ActivityCreatePayment.this, getString(R.string.txt_service_book_success), Toast.LENGTH_LONG).show();

                            setResult(RESULT_OK);

                            goToBookingScreen();
                            finish();
                            //onBackPressed();
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
