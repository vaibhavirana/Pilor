package com.barodacoder.pilor.utils;

import com.barodacoder.pilor.model.BookService;
import com.barodacoder.pilor.model.BookingResp;
import com.barodacoder.pilor.model.Category;
import com.barodacoder.pilor.model.Hours;
import com.barodacoder.pilor.model.ListBooking;
import com.barodacoder.pilor.model.ListBookingResp;
import com.barodacoder.pilor.model.ListCutter;
import com.barodacoder.pilor.model.ListRating;
import com.barodacoder.pilor.model.OpeningHoursResp;
import com.barodacoder.pilor.model.PaymentHistory;
import com.barodacoder.pilor.model.Rating;
import com.barodacoder.pilor.model.Service;
import com.barodacoder.pilor.model.ServiceResp;
import com.barodacoder.pilor.model.UserCutter;
import com.barodacoder.pilor.model.UserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParseJson {
    public static UserData parseSignUp(String response) throws JSONException {
        UserData user = new UserData();

        JSONObject jsonRoot = new JSONObject(response);

        user = parseUserData(jsonRoot);

        return user;
    }

    private static UserData parseUserData(JSONObject jsonRoot) throws JSONException {
        UserData user = new UserData();
        List<Hours> opening_hours = new ArrayList<>();

        if (jsonRoot.has("user_id"))
            user.setUserId(jsonRoot.getString("user_id"));

        if (jsonRoot.has("image_count"))
            user.setImageCount(jsonRoot.getString("image_count"));

        if (jsonRoot.has("cover_image1"))
            user.setCoverImage1(jsonRoot.getString("cover_image1"));

        if (jsonRoot.has("cover_image2"))
            user.setCoverImage2(jsonRoot.getString("cover_image2"));

        if (jsonRoot.has("cover_image3"))
            user.setCoverImage3(jsonRoot.getString("cover_image3"));

        if (jsonRoot.has("cover_image4"))
            user.setCoverImage4(jsonRoot.getString("cover_image4"));

        if (jsonRoot.has("cover_image5"))
            user.setCoverImage5(jsonRoot.getString("cover_image5"));

        if (jsonRoot.has("thumb_image1"))
            user.setThumbImage1(jsonRoot.getString("thumb_image1"));

        if (jsonRoot.has("thumb_image2"))
            user.setThumbImage2(jsonRoot.getString("thumb_image2"));

        if (jsonRoot.has("thumb_image3"))
            user.setThumbImage3(jsonRoot.getString("thumb_image3"));

        if (jsonRoot.has("thumb_image4"))
            user.setThumbImage4(jsonRoot.getString("thumb_image4"));

        if (jsonRoot.has("thumb_image5"))
            user.setThumbImage5(jsonRoot.getString("thumb_image5"));

        if (jsonRoot.has("last_subscription_date"))
            user.setLastSubscriptionDate(jsonRoot.getString("last_subscription_date"));

        if (jsonRoot.has("role"))
            user.setRole(jsonRoot.getString("role"));

        if (jsonRoot.has("thirdparty_id"))
            user.setThirdPartyId(jsonRoot.getString("thirdparty_id"));

        if (jsonRoot.has("email"))
            user.setEmail(jsonRoot.getString("email"));

        if (jsonRoot.has("subscriptionid"))
            user.setSubscriptionId(jsonRoot.getString("subscriptionid"));

        if (jsonRoot.has("credit_card"))
            user.setCreditCard(jsonRoot.getString("credit_card"));

        if (jsonRoot.has("user_balance"))
            user.setUserBalance(jsonRoot.getString("user_balance"));

        if (jsonRoot.has("password"))
            user.setPassword(jsonRoot.getString("password"));

        if (jsonRoot.has("temp_pass"))
            user.setTempPassword(jsonRoot.getString("temp_pass"));

        if (jsonRoot.has("display_name"))
            user.setDisplayName(jsonRoot.getString("display_name"));

        if (jsonRoot.has("first_name"))
            user.setFirstName(jsonRoot.getString("first_name"));

        if (jsonRoot.has("last_name"))
            user.setLastName(jsonRoot.getString("last_name"));

        if (jsonRoot.has("profile_pic"))
            user.setProfile(jsonRoot.getString("profile_pic"));

        if (jsonRoot.has("latitude"))
            user.setLatitude(jsonRoot.getDouble("latitude"));

        if (jsonRoot.has("longitude"))
            user.setLongitude(jsonRoot.getDouble("longitude"));

        if (jsonRoot.has("gender"))
            user.setGender(jsonRoot.getString("gender"));

        if (jsonRoot.has("mobile"))
            user.setMobile(jsonRoot.getString("mobile"));

        if (jsonRoot.has("dob"))
            user.setDob(jsonRoot.getString("dob"));

        if (jsonRoot.has("address"))
            user.setAddress(jsonRoot.getString("address"));

        if (jsonRoot.has("civil_status"))
            user.setCivilStatus(jsonRoot.getString("civil_status"));

        if (jsonRoot.has("bio"))
            user.setBio(jsonRoot.getString("bio"));

        if (jsonRoot.has("job"))
            user.setJob(jsonRoot.getString("job"));

        if (jsonRoot.has("radius"))
            user.setRadius(jsonRoot.getString("radius"));

        if (jsonRoot.has("login_through"))
            user.setLoginThrough(jsonRoot.getString("login_through"));

        if (jsonRoot.has("is_push"))
            user.setIsPush(jsonRoot.getString("is_push"));

        if (jsonRoot.has("badge"))
            user.setBadge(jsonRoot.getString("badge"));

        if (jsonRoot.has("secret_token"))
            user.setSecretToken(jsonRoot.getString("secret_token"));

        if (jsonRoot.has("verification_code"))
            user.setVerificationCode(jsonRoot.getString("verification_code"));

        if (jsonRoot.has("is_booking_on"))
            user.setIsBookingOn(jsonRoot.getString("is_booking_on"));

        if (jsonRoot.has("is_verify"))
            user.setIsVerify(jsonRoot.getString("is_verify"));

        if (jsonRoot.has("is_blocked"))
            user.setIsBlocked(jsonRoot.getString("is_blocked"));

        if (jsonRoot.has("is_active"))
            user.setIsActive(jsonRoot.getString("is_active"));

        if (jsonRoot.has("is_cleaner"))
            user.setIsCleaner(jsonRoot.getString("is_cleaner"));

        if (jsonRoot.has("date"))
            user.setDate(jsonRoot.getString("date"));

        if (jsonRoot.has("opening_hours")) {
            JSONArray jsnArr = jsonRoot.getJSONArray("opening_hours");

            for (int i = 0; i < jsnArr.length(); i++) {
                opening_hours.add(parseHoursList(jsnArr.getJSONObject(i)));
            }
            user.setOpeningHours(opening_hours);
        }

        if (jsonRoot.has("user_token"))
            user.setUserToken(jsonRoot.getString("user_token"));

        if (jsonRoot.has("msg"))
            user.setMsg(jsonRoot.getString("msg"));

        if (jsonRoot.has("status_code"))
            user.setStatusCode(jsonRoot.getInt("status_code"));

        return user;
    }


    private static UserCutter parseUserCutter(JSONObject jsonRoot) throws JSONException {
        UserCutter user = new UserCutter();
        ArrayList<Service> services = new ArrayList<>();

        if (jsonRoot.has("user_id"))
            user.setUserId(jsonRoot.getString("user_id"));

        if (jsonRoot.has("image_count"))
            user.setImageCount(jsonRoot.getString("image_count"));

        if (jsonRoot.has("cover_image1"))
            user.setCoverImage1(jsonRoot.getString("cover_image1"));

        if (jsonRoot.has("cover_image2"))
            user.setCoverImage2(jsonRoot.getString("cover_image2"));

        if (jsonRoot.has("cover_image3"))
            user.setCoverImage3(jsonRoot.getString("cover_image3"));

        if (jsonRoot.has("cover_image4"))
            user.setCoverImage4(jsonRoot.getString("cover_image4"));

        if (jsonRoot.has("cover_image5"))
            user.setCoverImage5(jsonRoot.getString("cover_image5"));

        if (jsonRoot.has("thumb_image1"))
            user.setThumbImage1(jsonRoot.getString("thumb_image1"));

        if (jsonRoot.has("thumb_image2"))
            user.setThumbImage2(jsonRoot.getString("thumb_image2"));

        if (jsonRoot.has("thumb_image3"))
            user.setThumbImage3(jsonRoot.getString("thumb_image3"));

        if (jsonRoot.has("thumb_image4"))
            user.setThumbImage4(jsonRoot.getString("thumb_image4"));

        if (jsonRoot.has("thumb_image5"))
            user.setThumbImage5(jsonRoot.getString("thumb_image5"));

        if (jsonRoot.has("last_subscription_date"))
            user.setLastSubscriptionDate(jsonRoot.getString("last_subscription_date"));

        if (jsonRoot.has("role"))
            user.setRole(jsonRoot.getString("role"));

        if (jsonRoot.has("thirdparty_id"))
            user.setThirdPartyId(jsonRoot.getString("thirdparty_id"));

        if (jsonRoot.has("email"))
            user.setEmail(jsonRoot.getString("email"));

        if (jsonRoot.has("subscriptionid"))
            user.setSubscriptionId(jsonRoot.getString("subscriptionid"));

        if (jsonRoot.has("credit_card"))
            user.setCreditCard(jsonRoot.getString("credit_card"));

        if (jsonRoot.has("user_balance"))
            user.setUserBalance(jsonRoot.getString("user_balance"));

        if (jsonRoot.has("password"))
            user.setPassword(jsonRoot.getString("password"));

        if (jsonRoot.has("temp_pass"))
            user.setTempPassword(jsonRoot.getString("temp_pass"));

        if (jsonRoot.has("display_name"))
            user.setDisplayName(jsonRoot.getString("display_name"));

        if (jsonRoot.has("first_name"))
            user.setFirstName(jsonRoot.getString("first_name"));

        if (jsonRoot.has("last_name"))
            user.setLastName(jsonRoot.getString("last_name"));

        if (jsonRoot.has("profile_pic"))
            user.setProfile(jsonRoot.getString("profile_pic"));

        if (jsonRoot.has("latitude"))
            user.setLatitude(jsonRoot.getDouble("latitude"));

        if (jsonRoot.has("longitude"))
            user.setLongitude(jsonRoot.getDouble("longitude"));

        if (jsonRoot.has("gender"))
            user.setGender(jsonRoot.getString("gender"));

        if (jsonRoot.has("mobile"))
            user.setMobile(jsonRoot.getString("mobile"));

        if (jsonRoot.has("dob"))
            user.setDob(jsonRoot.getString("dob"));

        if (jsonRoot.has("address"))
            user.setAddress(jsonRoot.getString("address"));

        if (jsonRoot.has("civil_status"))
            user.setCivilStatus(jsonRoot.getString("civil_status"));

        if (jsonRoot.has("bio"))
            user.setBio(jsonRoot.getString("bio"));

        if (jsonRoot.has("job"))
            user.setJob(jsonRoot.getString("job"));

        if (jsonRoot.has("radius"))
            user.setRadius(jsonRoot.getString("radius"));

        if (jsonRoot.has("login_through"))
            user.setLoginThrough(jsonRoot.getString("login_through"));

        if (jsonRoot.has("is_push"))
            user.setIsPush(jsonRoot.getString("is_push"));

        if (jsonRoot.has("badge"))
            user.setBadge(jsonRoot.getString("badge"));

        if (jsonRoot.has("secret_token"))
            user.setSecretToken(jsonRoot.getString("secret_token"));

        if (jsonRoot.has("verification_code"))
            user.setVerificationCode(jsonRoot.getString("verification_code"));

        if (jsonRoot.has("is_booking_on"))
            user.setIsBookingOn(jsonRoot.getString("is_booking_on"));

        if (jsonRoot.has("is_verify"))
            user.setIsVerify(jsonRoot.getString("is_verify"));

        if (jsonRoot.has("is_blocked"))
            user.setIsBlocked(jsonRoot.getString("is_blocked"));

        if (jsonRoot.has("is_active"))
            user.setIsActive(jsonRoot.getString("is_active"));

        if (jsonRoot.has("is_cleaner"))
            user.setIsCleaner(jsonRoot.getString("is_cleaner"));

        if (jsonRoot.has("date"))
            user.setDate(jsonRoot.getString("date"));

        if (jsonRoot.has("distance"))
            user.setDistance(jsonRoot.getString("distance"));

        if (jsonRoot.has("min_rate"))
            user.setMin_rate(jsonRoot.getString("min_rate"));

        if (jsonRoot.has("avarage_rating"))
            user.setAvarage_rating(jsonRoot.getInt("avarage_rating"));

        if (jsonRoot.has("service_info")) {
            JSONArray jsnArr = jsonRoot.getJSONArray("service_info");

            for (int i = 0; i < jsnArr.length(); i++) {
                services.add(parseServiceList(jsnArr.getJSONObject(i)));
            }
            user.setService_info(services);
        }

        return user;
    }

    public static ListCutter parseCutterList(String response) throws JSONException {
        ListCutter listCutter = new ListCutter();

        //Log.e("resp",response);
        ArrayList<UserCutter> userCutters = new ArrayList<>();

        JSONObject jsonRoot = new JSONObject(response);

        if (jsonRoot.has("msg"))
            listCutter.setMsg(jsonRoot.getString("msg"));

        if (jsonRoot.has("status_code"))
            listCutter.setStatusCode(jsonRoot.getInt("status_code"));

        if (jsonRoot.has("info")) {
            JSONArray jsnArr = jsonRoot.getJSONArray("info");

            for (int i = 0; i < jsnArr.length(); i++) {

                userCutters.add(parseUserCutter(jsnArr.getJSONObject(i)));
            }
            listCutter.setInfo(userCutters);
            //  Log.e("info",userCutters.toString());

        }


        return listCutter;
    }

    public static ServiceResp parseListService(String response) throws JSONException {
        ServiceResp serviceResp = new ServiceResp();

        //Log.e("resp",response);
        ArrayList<Service> listBookings = new ArrayList<>();
        JSONObject jsonRoot = new JSONObject(response);

        if (jsonRoot.has("msg"))
            serviceResp.msg=(jsonRoot.getString("msg"));

        if (jsonRoot.has("status_code"))
            serviceResp.statusCode=(jsonRoot.getInt("status_code"));

        if (jsonRoot.has("info")) {
            JSONArray jsnArr = jsonRoot.getJSONArray("info");
            for (int i = 0; i < jsnArr.length(); i++) {
                listBookings.add(parseServiceList(jsnArr.getJSONObject(i)));
            }

            serviceResp.info=listBookings;
        }

        return serviceResp;
    }

    public static Service parseServiceList(JSONObject jsonRoot) throws JSONException {
        Service service = new Service();

        if (jsonRoot.has("service_name"))
            service.setService_name(jsonRoot.getString("service_name"));

        if (jsonRoot.has("service_id"))
            service.setService_id(jsonRoot.getString("service_id"));

        if (jsonRoot.has("is_service_active"))
            service.setIs_service_active(jsonRoot.getString("is_service_active"));

        if (jsonRoot.has("rate"))
            service.setRate(jsonRoot.getString("rate"));

        if (jsonRoot.has("user_id"))
            service.setUser_id(jsonRoot.getString("user_id"));

        return service;
    }

    public static OpeningHoursResp parseOpeningHoursService(String response) throws JSONException {
        OpeningHoursResp openingHoursResp = new OpeningHoursResp();

        //Log.e("resp",response);
        ArrayList<Hours> listHoues = new ArrayList<>();
        JSONObject jsonRoot = new JSONObject(response);

        if (jsonRoot.has("msg"))
            openingHoursResp.msg=(jsonRoot.getString("msg"));

        if (jsonRoot.has("status_code"))
            openingHoursResp.statusCode=(jsonRoot.getInt("status_code"));

        if (jsonRoot.has("info")) {
            JSONArray jsnArr = jsonRoot.getJSONArray("info");
            for (int i = 0; i < jsnArr.length(); i++) {
                listHoues.add(parseHoursList(jsnArr.getJSONObject(i)));
            }

            openingHoursResp.info=listHoues;
        }

        return openingHoursResp;
    }

    public static Hours parseHoursList(JSONObject jsonRoot) throws JSONException {
        Hours hours = new Hours();

        if (jsonRoot.has("start_time"))
            hours.setStartTime(jsonRoot.getString("start_time"));

        if (jsonRoot.has("end_time"))
            hours.setEndTime(jsonRoot.getString("end_time"));

        return hours;
    }

    private static UserCutter parseImageArray(JSONObject jsonRoot) throws JSONException {
        UserCutter userCutter = new UserCutter();

        if (jsonRoot.has("user_id"))
            userCutter.setUserId(jsonRoot.getString("user_id"));

        if (jsonRoot.has("image_count"))
            userCutter.setImageCount(jsonRoot.getString("image_count"));

        if (jsonRoot.has("cover_image1"))
            userCutter.setCoverImage1(jsonRoot.getString("cover_image1"));

        if (jsonRoot.has("cover_image2"))
            userCutter.setCoverImage2(jsonRoot.getString("cover_image2"));

        if (jsonRoot.has("cover_image3"))
            userCutter.setCoverImage3(jsonRoot.getString("cover_image3"));

        if (jsonRoot.has("cover_image4"))
            userCutter.setCoverImage4(jsonRoot.getString("cover_image4"));

        if (jsonRoot.has("cover_image5"))
            userCutter.setCoverImage5(jsonRoot.getString("cover_image5"));

        if (jsonRoot.has("thumb_image1"))
            userCutter.setThumbImage1(jsonRoot.getString("thumb_image1"));

        if (jsonRoot.has("thumb_image2"))
            userCutter.setThumbImage2(jsonRoot.getString("thumb_image2"));

        if (jsonRoot.has("thumb_image3"))
            userCutter.setThumbImage3(jsonRoot.getString("thumb_image3"));

        if (jsonRoot.has("thumb_image4"))
            userCutter.setThumbImage4(jsonRoot.getString("thumb_image4"));

        if (jsonRoot.has("thumb_image5"))
            userCutter.setThumbImage5(jsonRoot.getString("thumb_image5"));

        if (jsonRoot.has("distance"))
            userCutter.setDistance(jsonRoot.getString("distance"));

        return userCutter;
    }

    public static ListRating parseCutterRatingList(String response) throws JSONException {
        ListRating listCutterRating = new ListRating();

        //Log.e("resp",response);
        ArrayList<Rating> ratings = new ArrayList<>();

        JSONObject jsonRoot = new JSONObject(response);

        if (jsonRoot.has("msg"))
            listCutterRating.setMsg(jsonRoot.getString("msg"));

        if (jsonRoot.has("status_code"))
            listCutterRating.setStatusCode(jsonRoot.getInt("status_code"));

        if (jsonRoot.has("info")) {
            JSONArray jsnArr = jsonRoot.getJSONArray("info");

            for (int i = 0; i < jsnArr.length(); i++) {

                ratings.add(parseCutterRatingList(jsnArr.getJSONObject(i)));
            }
            listCutterRating.setInfo(ratings);
            //  Log.e("info",userCutters.toString());

        }


        return listCutterRating;
    }


    public static Rating parseCutterRatingList(JSONObject jsonRoot) throws JSONException {
        Rating rating = new Rating();

        if (jsonRoot.has("rate_id"))
            rating.setRate_id(jsonRoot.getString("rate_id"));

        if (jsonRoot.has("cutter_id"))
            rating.setCutter_id(jsonRoot.getString("cutter_id"));

        if (jsonRoot.has("review_text"))
            rating.setReview_text(jsonRoot.getString("review_text"));

        if (jsonRoot.has("review_star"))
            rating.setReview_star(jsonRoot.getInt("review_star"));

        if (jsonRoot.has("created_date"))
            rating.setCreated_date(jsonRoot.getString("created_date"));

        if (jsonRoot.has("display_name"))
            rating.setDisplay_name(jsonRoot.getString("display_name"));

        if (jsonRoot.has("profile_pic"))
            rating.setProfile_pic(jsonRoot.getString("profile_pic"));

        if (jsonRoot.has("user_id"))
            rating.setUser_id(jsonRoot.getString("user_id"));

        return rating;
    }

    public static BookingResp parseServiceBooking(String response) throws JSONException {
        BookingResp bookingResp = new BookingResp();

        //Log.e("resp",response);
        ArrayList<BookService> bookServices = new ArrayList<>();
        BookService bookService=new BookService();
        JSONObject jsonRoot = new JSONObject(response);

        if (jsonRoot.has("msg"))
            bookingResp.msg=(jsonRoot.getString("msg"));

        if (jsonRoot.has("status_code"))
            bookingResp.statusCode=(jsonRoot.getInt("status_code"));

        if (jsonRoot.has("info")) {
          //  JSONArray jsnArr = jsonRoot.getJSONArray("info");
            bookService=parseBookingService(jsonRoot.getJSONObject("info"));

            bookingResp.info=(bookService);
            //  Log.e("info",userCutters.toString());

        }

        return bookingResp;
    }

    public static BookService parseBookingService(JSONObject jsonRoot) throws JSONException {
        BookService bookService = new BookService();

        if (jsonRoot.has("user_id"))
            bookService.user_id=(jsonRoot.getString("user_id"));

        if (jsonRoot.has("service_provide_by"))
            bookService.service_provide_by=(jsonRoot.getString("service_provide_by"));

        if (jsonRoot.has("date_of_booking"))
            bookService.date_of_booking=(jsonRoot.getString("date_of_booking"));

        if (jsonRoot.has("price"))
            bookService.price=(jsonRoot.getString("price"));

        if (jsonRoot.has("service_id"))
            bookService.service_id=(jsonRoot.getString("service_id"));

        if (jsonRoot.has("localtime"))
            bookService.localtime=(jsonRoot.getString("localtime"));

        if (jsonRoot.has("localtime_UTC"))
            bookService.localtime_UTC=(jsonRoot.getString("localtime_UTC"));

        if (jsonRoot.has("book_id"))
            bookService.book_id=(jsonRoot.getString("book_id"));

        if (jsonRoot.has("payment_status"))
            bookService.payment_status=(jsonRoot.getString("payment_status"));

        if (jsonRoot.has("payment_status_msg"))
            bookService.payment_status_msg=(jsonRoot.getString("payment_status_msg"));
        return bookService;
    }

    public static ArrayList<Category> parseCategoryList(String response) throws JSONException {
        ArrayList<Category> alCategory = new ArrayList<>();

        JSONObject jsonRoot = new JSONObject(response);

        if (jsonRoot.has("info")) {
            JSONArray jsnArr = jsonRoot.getJSONArray("info");

            for (int i = 0; i < jsnArr.length(); i++) {
                alCategory.add(parseCategory(jsnArr.getJSONObject(i)));
            }
        }

        return alCategory;
    }

    private static Category parseCategory(JSONObject jsonCategory) throws JSONException {
        Category category = new Category();

        if (jsonCategory.has("cat_id"))
            category.setCategoryId(jsonCategory.getString("cat_id"));

        if (jsonCategory.has("category_name"))
            category.setCategoryName(jsonCategory.getString("category_name"));

        if (jsonCategory.has("category_image"))
            category.setCategoryImage(jsonCategory.getString("category_image"));

        if (jsonCategory.has("parent_id"))
            category.setParentId(jsonCategory.getString("parent_id"));

        if (jsonCategory.has("sub_category")) {
            JSONArray jsnArr = jsonCategory.getJSONArray("sub_category");

            for (int i = 0; i < jsnArr.length(); i++) {
                category.getAlSubCategory().add(parseCategory(jsnArr.getJSONObject(i)));
            }
        }

        return category;
    }

    public static ListBookingResp parseListBooking(String response) throws JSONException {
        ListBookingResp bookingResp = new ListBookingResp();

        //Log.e("resp",response);
        ArrayList<ListBooking> listBookings = new ArrayList<>();
         JSONObject jsonRoot = new JSONObject(response);

        if (jsonRoot.has("msg"))
            bookingResp.msg=(jsonRoot.getString("msg"));

        if (jsonRoot.has("status_code"))
            bookingResp.statusCode=(jsonRoot.getInt("status_code"));

        if (jsonRoot.has("info")) {
            JSONArray jsnArr = jsonRoot.getJSONArray("info");
            for (int i = 0; i < jsnArr.length(); i++) {
                listBookings.add(parseBoooking(jsnArr.getJSONObject(i)));
            }

            bookingResp.info=listBookings;
        }

        return bookingResp;
    }

    private static ListBooking parseBoooking(JSONObject jsonBooking) throws JSONException {
        ListBooking listBooking = new ListBooking();

        if (jsonBooking.has("user_id"))
            listBooking.user_id=(jsonBooking.getString("user_id"));

        if (jsonBooking.has("service_id"))
            listBooking.service_id=(jsonBooking.getString("service_id"));

        if (jsonBooking.has("book_id"))
            listBooking.book_id=(jsonBooking.getString("book_id"));

        if (jsonBooking.has("service_provide_by"))
            listBooking.service_provide_by=(jsonBooking.getString("service_provide_by"));

        if (jsonBooking.has("date_of_booking"))
            listBooking.date_of_booking=(jsonBooking.getString("date_of_booking"));

        if (jsonBooking.has("accepted_date"))
            listBooking.accepted_date=(jsonBooking.getString("accepted_date"));

        if (jsonBooking.has("created_date"))
            listBooking.created_date=(jsonBooking.getString("created_date"));

        if (jsonBooking.has("localtime"))
            listBooking.localtime=(jsonBooking.getString("localtime"));

        if (jsonBooking.has("localtime_UTC"))
            listBooking.localtime_UTC=(jsonBooking.getString("localtime_UTC"));

        if (jsonBooking.has("price"))
            listBooking.price=(jsonBooking.getString("price"));

        if (jsonBooking.has("is_service_accepted"))
            listBooking.is_service_accepted=(jsonBooking.getInt("is_service_accepted"));

        if (jsonBooking.has("is_reschedule"))
            listBooking.is_reschedule=(jsonBooking.getString("is_reschedule"));

        if (jsonBooking.has("is_done"))
            listBooking.is_done=(jsonBooking.getString("is_done"));

        if (jsonBooking.has("is_review_added"))
            listBooking.is_review_added=(jsonBooking.getString("is_review_added"));

        if (jsonBooking.has("hide_by_business"))
            listBooking.hide_by_business=(jsonBooking.getString("hide_by_business"));

        if (jsonBooking.has("display_name"))
            listBooking.display_name=(jsonBooking.getString("display_name"));

        if (jsonBooking.has("mobile"))
            listBooking.mobile=(jsonBooking.getString("mobile"));

        if (jsonBooking.has("email"))
            listBooking.email=(jsonBooking.getString("email"));

        if (jsonBooking.has("latitude"))
            listBooking.latitude=(jsonBooking.getString("latitude"));

        if (jsonBooking.has("longitude"))
            listBooking.longitude=(jsonBooking.getString("longitude"));

        if (jsonBooking.has("category_name"))
            listBooking.category_name = (jsonBooking.getString("category_name"));

        return listBooking;
    }

    public static ArrayList<PaymentHistory> parsePaymentHistory(String response) throws JSONException
    {
        ArrayList<PaymentHistory> alPayment = new ArrayList<>();

        JSONObject jsonRoot = new JSONObject(response);

        if(jsonRoot.has("info"))
        {
            JSONArray jsnArr = jsonRoot.getJSONArray("info");

            for(int i = 0; i < jsnArr.length(); i++)
            {
                JSONObject jsonInfo = jsnArr.getJSONObject(i);

                PaymentHistory payment = new PaymentHistory();

                if(jsonInfo.has("tauto_id"))
                    payment.setId(jsonInfo.getString("tauto_id"));

                if(jsonInfo.has("transaction_type"))
                    payment.setTransactionType(jsonInfo.getString("transaction_type"));

                if(jsonInfo.has("withdraw_status"))
                    payment.setWithdrawStatus(jsonInfo.getString("withdraw_status"));

                if(jsonInfo.has("transaction_id"))
                    payment.setTransactionId(jsonInfo.getString("transaction_id"));

                if(jsonInfo.has("uid_to"))
                    payment.setUidTo(jsonInfo.getString("uid_to"));

                if(jsonInfo.has("uid_from"))
                    payment.setUidFrom(jsonInfo.getString("uid_from"));

                if(jsonInfo.has("amount"))
                    payment.setAmount(jsonInfo.getString("amount"));

                if(jsonInfo.has("book_id"))
                    payment.setBook_id(jsonInfo.getString("book_id"));

                if(jsonInfo.has("payment_release_flag"))
                    payment.setReleaseDate(jsonInfo.getString("payment_release_flag"));

                if(jsonInfo.has("date"))
                    payment.setDate(jsonInfo.getString("date"));

                if(jsonInfo.has("profile_pic"))
                    payment.setProfilePic(jsonInfo.getString("profile_pic"));

                if(jsonInfo.has("display_name"))
                    payment.setDisplayName(jsonInfo.getString("display_name"));

                alPayment.add(payment);
            }
        }

        return alPayment;
    }

}
