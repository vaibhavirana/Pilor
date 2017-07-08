package com.moonsted.pilors;

public class AppConstants {

    // EMPTYVIEW
    public static final int NO_BARBER = 111;
    public static final int NO_BOOKING = 222;
    public static final int NO_RATING = 333;
    public static final int NO_HISTORY = 444;


    public static final boolean DEBUG = true;

    public static final String DEBUG_TAG = "PILOR";

    public static final String PREFS_FILE_NAME_PARAM = "PILOR.PREF";

    public static final int DEVICE_TYPE = 2;

    //public static final String MERCHANT_ID = "8027756";//"1027756";
    public static final String MERCHANT_ID = "1027756";//"1027756";

    public static final String AES128_KEY = "5kJLG3ZgEMYJJhPjEanNDBwg7H7EfNqB"; //"5kJLG3ZgEMYJJhPjEanNDBwg7H7EfNqB";

    public static final String URL_FAQ = "http://www.pilors.dk/faq";

    public static final String URL_CONTACT = "http://www.pilors.dk/contact";

    public static final String URL_TERMS = "http://www.pilors.dk/terms";

    public static final String URL_RECOMMENDUS = "https://play.google.com/store/apps/details?id=com.barodacoder.pilor";

    public static final String URL_BECOME_CUTTER = "http://pilors.dk/signup";

    //public static final String URL_BASE = "http://inmomenthosting.info/pilors_test/api_pilors/";
    public static final String URL_BASE = "http://inmomenthosting.info/pilors/api_pilors/";

    public static final String URL_LOGIN = URL_BASE + "login";
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
    public static final String URL_LIST_USER_SERVICES = URL_BASE + "get_user_services";
    /*
    user_id(*)

     */
    public static final String URL_UPDATE_USER_SERVICES = URL_BASE + "update_service";
    /*
    user_id(*)

     */

    public static final String URL_LIST_CUTTER = URL_BASE + "list_cutter";
    /*
    user_id(*)
    user_token(*)
     latitude
    longitude
    redius
     */

    public static final String URL_SEARCH_CUTTER = URL_BASE + "search_cutter";
    /*
    user_id(*)
    user_token(*)
    search_string(*)
     */

    public static final String URL_LIST_RATING = URL_BASE + "list_rating";
    /*
   user_id(*)
   user_token(*)
   cutter_id(*)"
     */

    public static final String URL_BOOK_SERVICE = URL_BASE + "book_sericev2";
    /*"user_id(*)
    user_token(*)
    service_provide_by(*)
    date_of_booking(*)
    service_id(*)
    price(*)
    booking_date(*)

    instantcapture(*) [pass 0]
    merchantnumber(*)
    subscriptionid(*)
    credit_card(*)
    localtime(*) [yyyy-MM-dd HH:mm:ss]
    localtime_UTC(*) [yyyy-MM-dd HH:mm:ss] [send UTC time of current device time]"*/

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
    public static final String URL_UPDATE_CUTTER_PROFILE = URL_BASE + "update_cutter_profile";
    /*
    "user_id(*)
user_token(*)
other_user_id(*)
display_name
first_name
last_name
profile_pic
email
address
cover_image1
cover_image2
cover_image3
cover_image4
cover_image5
profile_pic
bio
thumb_image1
thumb_image2
thumb_image3
thumb_image4
thumb_image5"
     */

    public static final String URL_ADD_RATING = URL_BASE + "add_rating";
    /*

user_id(*)
user_token(*)
cutter_id(*)
review_text(*)
review_start(*)
book_id(*)
     */

    public static final String URL_LIST_BOOKING = URL_BASE + "list_booking";
    /*user_id(*)
    user_token(*)
    */

    public static final String URL_CANCEL_SERVICE = URL_BASE + "cancel_service";
    /*user_id(*)
    user_token(*)
    book_id(*)
    is_service_accepted(*) [pass 4 = cancel]
    localtime(*) [yyyy-MM-dd HH:mm:ss]
    localtime_UTC(*) [yyyy-MM-dd HH:mm:ss] [send UTC time of current device time]
    */


    public static final String URL_LIST_MY_TRANSACTION = URL_BASE + "list_my_transaction";

    /*
    user_id(*)
    user_token(*)
     */
    public static final String URL_UPDATE_TIME = URL_BASE + "update_time";

    /*
    "user_id(*)
    user_token(*)
    opening_hours(*)"
     */

    public static final String URL_LIST_BOOKING_FOR_CUTTER = URL_BASE + "list_booking_for_cleaner";

    /*
    "user_id(*)
    user_token(*)
    opening_hours(*)"
     */

    public static final String URL_UPDATE_SERVICE_STATUS_BY_CUTTER = URL_BASE + "update_service_status";

    /*
   "user_id(*)
    user_token(*)
    book_id(*)
    is_service_accepted(*) [2: if cutter cancel 1: if cutter accept  3: if cutter mark as done]"
     */


    public static final String URL_CHANGE_PASSWORD = URL_BASE + "change_password";
    /*
    user_id(*)
    user_token(*)
    current_password(*)
    new_password(*)
     */

    public static final String URL_RESCHEDULE_REQUEST = URL_BASE + "request_for_reschedule";
   /* user_id(*)
    user_token(*)
    book_id(*)
    is_service_accepted(*) [pass 0]
    localtime(*)
    localtime_UTC(*)
    date_of_booking(*)*/

    public static final String URL_UPDATE_RESCHEDULE_REQUEST = URL_BASE + "reschedule_status_update";
   /* user_id(*)
      user_token(*)
      book_id(*)
      is_service_accepted(*)  [pass 1 if accept  and  pass 4 if cancel]
      localtime(*)*/
}
