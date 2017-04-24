package com.barodacoder.pilor;

public class AppConstants
{
    public static final boolean DEBUG = true;

    public static final String DEBUG_TAG = "PILOR";

    public static final String PREFS_FILE_NAME_PARAM = "PILOR.PREF";

    public static final int DEVICE_TYPE = 2;

    public static final String MERCHANT_ID = "8026162";//"1021528";

    public static final String AES128_KEY = "5kJLG3ZgEMYJJhPjEanNDBwg7H7EfNqB"; //"5kJLG3ZgEMYJJhPjEanNDBwg7H7EfNqB";

    public static final String URL_CONTACT = "http://www.snapfixed.dk/contact";

    public static final String URL_FACEBOOK = "http://www.snapfixed.dk/fb";

    public static final String URL_WEBSITE = "http://www.snapfixed.dk";

    public static final String URL_WANT_JOB = "http://www.snapfixed.dk/getstarted";

    public static final String URL_BASE = "http://inmomenthosting.info/pilors_test/api_pilors/";
   // public static final String URL_BASE = "http://inmomenthosting.info/pilors/api_pilors/";

    public static final String URL_LOGIN =  URL_BASE + "login";
    /*
    email(*)
    password(*)
    localtime(*)
    device_token
    device_id (only for android)
    device_type( 1 for ios and 2 for android and 3 for windows)
     */

    public static final String URL_LOGIN_FACEBOOK = URL_BASE + "login_by_thirdparty";
    /*
    thirdparty_id(*) (facebook id) display_name(*)
    first_name(*)
    last_name
    email(*)
    latitude
    longitude
    gender dob
    device_token(*)
    device_id (only for android)
    device_type(*)( 1 for ios and 2 for android and 3 for windows)
     */

    public static final String URL_FORGOT_PASSWORD = URL_BASE + " forgot_password";
    /*
    email(*)
     */

    public static final String URL_SIGNUP = URL_BASE + "signup";
    /*
    email(*)
    password(*)
    device_token
    device_id (only for android)
    device_type( 1 for ios and 2 for android and 3 for windows)
    display_name
    first_name
    last_name
    profile_pic
    latitude
    longitude
    gender
     */

    public static final String URL_LIST_CATEGORY = URL_BASE + "list_category";
    /*
    user_id(*)
    user_token(*)
     */

    public static final String URL_UPDATE_PROFILE = URL_BASE + "update_my_profile";
    /*
    user_id(*)
    user_token(*)
    first_name
    last_name
    display_name
    mobile
    bio
    profile_pic
     */

    public static final String URL_CHANGE_PASSWORD = URL_BASE + "change_password";
    /*
    user_id(*)
    user_token(*)
    current_password(*)
    new_password(*)
     */

    public static final String URL_LIST_MY_TASK = URL_BASE + "list_my_tasks";
    /*
    user_id(*)
    user_token(*)
     */

    public static final String URL_ADD_TASK = URL_BASE + "add_task";
    /*
    task_description
    task_headline
    task_requirement
    task_distance
    task_budget
    task_expire_date
    task_cat_id
    image_path1
    image_path2
    image_path3
    image_path4
    image_path5
    thumb_image1
    thumb_image2
    thumb_image3
    thumb_image4
    thumb_image5
    total_images_count
    task_latitude
    task_longitude
     */

    public static final String URL_LIST_BIDS = URL_BASE + "list_bids";
    /*
    user_id(*)
    user_token(*)
    task_id(*)
     */

    public static final String URL_GET_BUSINESS_DETAIL = URL_BASE + "get_business_details";
    /*
    user_id(*)
    user_token(*)
    business_id(*)
    day
    task_id
     */

    public static final String URL_SELECT_WINNER = URL_BASE + "select_winner";
    /*
    user_id(*)
    user_token(*)
    winner_id(*)
    task_id(*)
     */


    //****************** BUSINESS SIDE ***********************

    public static final String URL_LIST_TASKS = URL_BASE + "list_tasks";
    /*
    user_id(*)
    user_token(*)
     */

    public static final String URL_LIST_TASKS_BUSINESS = URL_BASE + "list_tasks_business";
    /*
    user_id(*)
    user_token(*)
    business_id(*)
    latitude
    longitude
    radius
     */

    public static final String URL_UPDATE_BUSINESS = URL_BASE + "update_business";
    /*
    user_id(*)
    user_token(*)
    business_id(*)
    business_name
    business_email
    business_address
    business_description
    latitude
    longitude
    thumb_path (Return business thumb_image url)
    img_path (Return business image path)
    cover_image (Return business image path)
    website_link
    phone
     */

    public static final String URL_ADD_BID = URL_BASE + "add_bid";
    /*
    user_id(*)
    user_token(*)
    deadline_date(*)
    task_id(*)
    bid_value(*)
    comments
     */

    public static final String URL_ADD_REFERENCE = URL_BASE + "add_reference";
    /*
    user_id(*)
    user_token(*)
    reference_headline
    reference_photo
     */

    public static final String URL_LIST_REFERENCE = URL_BASE + "list_reference";
    /*
    user_id(*)
    user_token(*)
    business_id(*)
     */

    public static final String URL_DELETE_REFERENCE = URL_BASE + "delete_reference";
    /*
    user_id(*)
    user_token(*)
    reference_id(*)
     */

    public static final String URL_LIST_MY_TRANSACTION = URL_BASE + "list_my_transaction";
    /*
    user_id(*)
    user_token(*)
     */

}
