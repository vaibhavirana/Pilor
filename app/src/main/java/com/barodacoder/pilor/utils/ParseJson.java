package com.barodacoder.pilor.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParseJson {
    public static UserData parseSignUp(String response) throws JSONException {
        UserData user = new UserData();

        JSONObject jsonRoot = new JSONObject(response);

        user = parseUserData(jsonRoot);

        return user;
    }

    private static UserData parseUserData(JSONObject jsonRoot) throws JSONException {
        UserData user = new UserData();

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

        if (jsonRoot.has("opening_hours"))
            user.setOpeningHours(jsonRoot.getString("opening_hours"));

        if (jsonRoot.has("user_token"))
            user.setUserToken(jsonRoot.getString("user_token"));

        if (jsonRoot.has("msg"))
            user.setMsg(jsonRoot.getString("msg"));

        if (jsonRoot.has("status_code"))
            user.setStatusCode(jsonRoot.getInt("status_code"));

        return user;
    }

   /* public static Business parseBusinessDetail(String response) throws JSONException
    {
        Business business = new Business();

        JSONObject jsonROot = new JSONObject(response);

        if(jsonROot.has("info"))
        {
            JSONObject jsonInfo = jsonROot.getJSONObject("info");

            business = parseBusiness(jsonInfo);
        }

        return business;
    }

    private static Business parseBusiness(JSONObject jsonBusiness) throws JSONException
    {
        Business business = new Business();

        if(jsonBusiness.has("id"))
            business.setId(jsonBusiness.getString("id"));

        if(jsonBusiness.has("business_id"))
            business.setBusinessId(jsonBusiness.getString("business_id"));

        if(jsonBusiness.has("is_vip_business"))
            business.setIsVipBusiness(jsonBusiness.getString("is_vip_business"));

        if(jsonBusiness.has("business_name"))
            business.setBusinessName(jsonBusiness.getString("business_name"));

        if(jsonBusiness.has("business_email"))
            business.setBusinessEmail(jsonBusiness.getString("business_email"));

        if(jsonBusiness.has("email_link"))
            business.setEmailLink(jsonBusiness.getString("email_link"));

        if(jsonBusiness.has("business_address"))
            business.setAddress(jsonBusiness.getString("business_address"));

        if(jsonBusiness.has("business_website"))
            business.setWebsite(jsonBusiness.getString("business_website"));

        if(jsonBusiness.has("business_description"))
            business.setDescription(jsonBusiness.getString("business_description"));

        if(jsonBusiness.has("website_link"))
            business.setWebsiteLink(jsonBusiness.getString("website_link"));

        if(jsonBusiness.has("twitter_link"))
            business.setTwitterLink(jsonBusiness.getString("twitter_link"));

        if(jsonBusiness.has("phone"))
            business.setPhone(jsonBusiness.getString("phone"));

        if(jsonBusiness.has("fb_link"))
            business.setFbLink(jsonBusiness.getString("fb_link"));

        if(jsonBusiness.has("instagram_link"))
            business.setInstagramLink(jsonBusiness.getString("instagram_link"));

        if(jsonBusiness.has("yelp_link"))
            business.setYelpLink(jsonBusiness.getString("yelp_link"));

        if(jsonBusiness.has("foursquare_link"))
            business.setFourSquareLink(jsonBusiness.getString("foursquare_link"));

        if(jsonBusiness.has("latitude"))
            business.setLatitude(jsonBusiness.getString("latitude"));

        if(jsonBusiness.has("longitude"))
            business.setLongitude(jsonBusiness.getString("longitude"));

        if(jsonBusiness.has("img_path"))
            business.setImagePath(jsonBusiness.getString("img_path"));

        if(jsonBusiness.has("cover_image"))
            business.setCoverImage(jsonBusiness.getString("cover_image"));

        if(jsonBusiness.has("thumb_path"))
            business.setThumbPath(jsonBusiness.getString("thumb_path"));

        if(jsonBusiness.has("distance"))
            business.setDistance(jsonBusiness.getString("distance"));

        if(jsonBusiness.has("is_blocked"))
            business.setIsBlocked(jsonBusiness.getString("is_blocked"));

        if(jsonBusiness.has("cat_ids"))
            business.setCatIds(jsonBusiness.getString("cat_ids"));

        if(jsonBusiness.has("category_names"))
            business.setCategoryNames(jsonBusiness.getString("category_names"));

        if(jsonBusiness.has("business_plan"))
            business.setBusinessPlan(jsonBusiness.getString("business_plan"));

        if(jsonBusiness.has("is_business_fevorite"))
            business.setIsBusinessFavorite(jsonBusiness.getString("is_business_fevorite"));

        if(jsonBusiness.has("opening_hours"))
        {
            JSONArray jsnArr = jsonBusiness.getJSONArray("opening_hours");

            for(int i = 0; i < jsnArr.length(); i++)
            {
                JSONObject jsonHours = jsnArr.getJSONObject(i);

                if(jsonHours.has("start_time"))
                    business.getAlStartTime().add(jsonHours.getString("start_time"));

                if(jsonHours.has("end_time"))
                    business.getAlEndTime().add(jsonHours.getString("end_time"));
            }
        }

        if(jsonBusiness.has("reference"))
        {
            JSONArray jsnArr = jsonBusiness.getJSONArray("reference");

            for(int i = 0; i < jsnArr.length(); i++)
            {
                JSONObject jsonReference = jsnArr.getJSONObject(i);

                business.getAlReference().add(parseReferenceObject(jsonReference));
            }
        }

        if(jsonBusiness.has("task_timeline"))
            business.setTaskTimeline(jsonBusiness.getString("task_timeline"));

        return business;
    }*/

    public static ArrayList<Category> parseCategoryList(String response) throws JSONException
    {
        ArrayList<Category> alCategory = new ArrayList<>();

        JSONObject jsonRoot = new JSONObject(response);

        if(jsonRoot.has("info"))
        {
            JSONArray jsnArr = jsonRoot.getJSONArray("info");

            for(int i = 0; i < jsnArr.length(); i++)
            {
                alCategory.add(parseCategory(jsnArr.getJSONObject(i)));
            }
        }

        return alCategory;
    }

    private static Category parseCategory(JSONObject jsonCategory) throws JSONException
    {
        Category category = new Category();

        if(jsonCategory.has("cat_id"))
            category.setCategoryId(jsonCategory.getString("cat_id"));

        if(jsonCategory.has("category_name"))
            category.setCategoryName(jsonCategory.getString("category_name"));

        if(jsonCategory.has("category_image"))
            category.setCategoryImage(jsonCategory.getString("category_image"));

        if(jsonCategory.has("parent_id"))
            category.setParentId(jsonCategory.getString("parent_id"));

        if(jsonCategory.has("sub_category"))
        {
            JSONArray jsnArr = jsonCategory.getJSONArray("sub_category");

            for(int i = 0; i < jsnArr.length(); i++)
            {
                category.getAlSubCategory().add(parseCategory(jsnArr.getJSONObject(i)));
            }
        }

        return category;
    }

   /* public static ArrayList<Tasks> parseTasksList(String response) throws JSONException
    {
        ArrayList<Tasks> alTasks = new ArrayList<>();

        JSONObject jsonRoot = new JSONObject(response);

        if(jsonRoot.has("info"))
        {
            JSONObject jsonInfo = jsonRoot.getJSONObject("info");

            if(jsonInfo.has("open"))
            {
                JSONArray jsnArr = jsonInfo.getJSONArray("open");

                for(int i = 0; i < jsnArr.length(); i++)
                {
                    alTasks.add(parseTask(jsnArr.getJSONObject(i)));
                }
            }
            if(jsonInfo.has("close"))
            {
                JSONArray jsnArr = jsonInfo.getJSONArray("close");

                for(int i = 0; i < jsnArr.length(); i++)
                {
                    alTasks.add(parseTask(jsnArr.getJSONObject(i)));
                }
            }
        }

        return alTasks;
    }

    public static BusinessTasks parseBusinessTasksList(String response) throws JSONException
    {
        BusinessTasks businessTask = new BusinessTasks();

        //ArrayList<Tasks> alTasks = new ArrayList<>();

        JSONObject jsonRoot = new JSONObject(response);

        if(jsonRoot.has("info"))
        {
            JSONObject jsonInfo = jsonRoot.getJSONObject("info");

            if(jsonInfo.has("new"))
            {
                JSONArray jsnArr = jsonInfo.getJSONArray("new");

                for(int i = 0; i < jsnArr.length(); i++)
                {
                    businessTask.getAlNewTask().add(parseTask(jsnArr.getJSONObject(i)));
                }
            }

            if(jsonInfo.has("old"))
            {
                JSONArray jsnArr = jsonInfo.getJSONArray("old");

                for(int i = 0; i < jsnArr.length(); i++)
                {
                    businessTask.getAlOldTask().add(parseTask(jsnArr.getJSONObject(i)));
                }
            }
        }

        return businessTask;
    }

    private static Tasks parseTask(JSONObject jsonTask) throws JSONException
    {
        Tasks task = new Tasks();

        if(jsonTask.has("task_id"))
            task.setTaskId(jsonTask.getString("task_id"));

        if(jsonTask.has("task_latitude"))
            task.setLatitude(jsonTask.getString("task_latitude"));

        if(jsonTask.has("task_longitude"))
            task.setLongitude(jsonTask.getString("task_longitude"));

        if(jsonTask.has("created_by"))
            task.setCreatedBy(jsonTask.getString("created_by"));

        if(jsonTask.has("task_description"))
            task.setTaskDescription(jsonTask.getString("task_description"));

        if(jsonTask.has("task_headline"))
            task.setTaskHeadline(jsonTask.getString("task_headline"));

        if(jsonTask.has("task_requirement"))
            task.setRequirement(jsonTask.getString("task_requirement"));

        if(jsonTask.has("task_distance"))
            task.setDistance(jsonTask.getString("task_distance"));

        if(jsonTask.has("task_budget"))
            task.setBudget(jsonTask.getString("task_budget"));

        if(jsonTask.has("task_expire_date"))
            task.setExpiryDate(jsonTask.getString("task_expire_date"));

        if(jsonTask.has("task_cat_id"))
            task.setCategoryId(jsonTask.getString("task_cat_id"));

        if(jsonTask.has("image_path1"))
            task.setImagePath1(jsonTask.getString("image_path1"));

        if(jsonTask.has("image_path2"))
            task.setImagePath2(jsonTask.getString("image_path2"));

        if(jsonTask.has("image_path3"))
            task.setImagePath3(jsonTask.getString("image_path3"));

        if(jsonTask.has("image_path4"))
            task.setImagePath4(jsonTask.getString("image_path4"));

        if(jsonTask.has("image_path5"))
            task.setImagePath5(jsonTask.getString("image_path5"));

        if(jsonTask.has("thumb_image1"))
            task.setThumbImage1(jsonTask.getString("thumb_image1"));

        if(jsonTask.has("thumb_image2"))
            task.setThumbImage2(jsonTask.getString("thumb_image2"));

        if(jsonTask.has("thumb_image3"))
            task.setThumbImage3(jsonTask.getString("thumb_image3"));

        if(jsonTask.has("thumb_image4"))
            task.setThumbImage4(jsonTask.getString("thumb_image4"));

        if(jsonTask.has("thumb_image5"))
            task.setThumbImage5(jsonTask.getString("thumb_image5"));

        if(jsonTask.has("total_images_count"))
            task.setTotalImagesCount(jsonTask.getString("total_images_count"));

        if(jsonTask.has("is_task_active"))
            task.setIsTaskActive(jsonTask.getString("is_task_active"));

        if(jsonTask.has("created_date"))
            task.setCreatedDate(jsonTask.getString("created_date"));

        if(jsonTask.has("day_left"))
            task.setDaysLeft(jsonTask.getString("day_left"));

        if(jsonTask.has("profile_pic"))
            task.setProfilePic(jsonTask.getString("profile_pic"));

        if(jsonTask.has("display_name"))
            task.setDisplayName(jsonTask.getString("display_name"));

        if(jsonTask.has("mobile"))
            task.setMobile(jsonTask.getString("mobile"));

        if(jsonTask.has("total_bid_count"))
            task.setTotalBidCOunt(jsonTask.getString("total_bid_count"));

        if(jsonTask.has("is_bid_by_me"))
            task.setIsBidByMe(jsonTask.getString("is_bid_by_me"));

        if(jsonTask.has("task_timeline"))
            task.setTaskTimeline(jsonTask.getString("task_timeline"));

        if(jsonTask.has("is_winner_declared"))
            task.setIsWinnerDeclared(jsonTask.getString("is_winner_declared"));

        if(jsonTask.has("winner_info") && jsonTask.get("winner_info") instanceof JSONObject)
        {
            JSONObject jsonWinner = jsonTask.getJSONObject("winner_info");

            if(jsonWinner.has("profile_pic"))
                task.setWinnerProfilePic(jsonWinner.getString("profile_pic"));

            if(jsonWinner.has("display_name"))
                task.setWinnerName(jsonWinner.getString("display_name"));
        }

        return task;
    }

    public static String parseCreateTask(String response) throws JSONException
    {
        JSONObject jsonRoot = new JSONObject(response);

        if(jsonRoot.has("status_code") && jsonRoot.get("status_code").toString().equals("1"))
            return "OK";

        return "FAILED";
    }

    public static ArrayList<Bids> parseBids(String response) throws JSONException
    {
        ArrayList<Bids> alBids = new ArrayList<>();

        JSONObject jsonRoot = new JSONObject(response);

        if(jsonRoot.has("info"))
        {
            JSONArray jsnArr = jsonRoot.getJSONArray("info");

            for(int i = 0; i < jsnArr.length(); i++)
            {
                alBids.add(parseBidObject(jsnArr.getJSONObject(i)));
            }
        }

        return alBids;
    }

    private static Bids parseBidObject(JSONObject jsonBid) throws JSONException
    {
        Bids bid = new Bids();

        if(jsonBid.has("bid_id"))
            bid.setBidId(jsonBid.getString("bid_id"));

        if(jsonBid.has("bid_by"))
            bid.setBidBy(jsonBid.getString("bid_by"));

        if(jsonBid.has("bid_value"))
            bid.setBidValue(jsonBid.getString("bid_value"));

        if(jsonBid.has("task_id"))
            bid.setTaskId(jsonBid.getString("task_id"));

        if(jsonBid.has("comments"))
            bid.setComments(jsonBid.getString("comments"));

        if(jsonBid.has("deadline_date"))
            bid.setDeadlineDate(jsonBid.getString("deadline_date"));

        if(jsonBid.has("display_name"))
            bid.setDisplayName(jsonBid.getString("display_name"));

        if(jsonBid.has("profile_pic"))
            bid.setProfilePic(jsonBid.getString("profile_pic"));

        if(jsonBid.has("img_path"))
            bid.setImagePath(jsonBid.getString("img_path"));

        if(jsonBid.has("thumb_path"))
            bid.setThumbPath(jsonBid.getString("thumb_path"));

        return bid;
    }

    public static ArrayList<Reference> parseReference(String response) throws JSONException
    {
        ArrayList<Reference> alReference = new ArrayList<>();

        JSONObject jsonRoot = new JSONObject(response);

        if(jsonRoot.has("info"))
        {
            JSONArray jsnArr = jsonRoot.getJSONArray("info");

            for(int i = 0; i < jsnArr.length(); i++)
            {
                JSONObject jsonReference = jsnArr.getJSONObject(i);

                alReference.add(parseReferenceObject(jsonReference));
            }
        }

        return alReference;
    }

    private static Reference parseReferenceObject(JSONObject jsonReference) throws JSONException
    {
        Reference reference = new Reference();

        if(jsonReference.has("reference_id"))
            reference.setId(jsonReference.getString("reference_id"));

        if(jsonReference.has("reference_headline"))
            reference.setHeadline(jsonReference.getString("reference_headline"));

        if(jsonReference.has("reference_photo"))
            reference.setPhoto(jsonReference.getString("reference_photo"));

        if(jsonReference.has("reference_business_id"))
            reference.setBusinessId(jsonReference.getString("reference_business_id"));

        if(jsonReference.has("is_reference_active"))
            reference.setIsActive(jsonReference.getString("is_reference_active"));

        return reference;
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

                if(jsonInfo.has("item_id"))
                    payment.setItemId(jsonInfo.getString("item_id"));

                if(jsonInfo.has("payment_release_flag"))
                    payment.setReleaseDate(jsonInfo.getString("payment_release_flag"));

                if(jsonInfo.has("date"))
                    payment.setDate(jsonInfo.getString("date"));

                if(jsonInfo.has("profile_pic"))
                    payment.setProfilePic(jsonInfo.getString("profile_pic"));

                if(jsonInfo.has("display_name"))
                    payment.setDisplayName(jsonInfo.getString("display_name"));

                if(jsonInfo.has("img_path"))
                    payment.setImagePath(jsonInfo.getString("img_path"));

                if(jsonInfo.has("thumb_path"))
                    payment.setThumbImage(jsonInfo.getString("thumb_path"));

                if(jsonInfo.has("task_image"))
                    payment.setTaskPath(jsonInfo.getString("task_image"));

                if(jsonInfo.has("task_created_by"))
                    payment.setTaskCreatedBy(jsonInfo.getString("task_created_by"));

                alPayment.add(payment);
            }
        }

        return alPayment;
    }*/

}
