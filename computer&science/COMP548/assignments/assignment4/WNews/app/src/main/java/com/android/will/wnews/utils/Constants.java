package com.android.will.wnews.utils;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 * <p/>
 * Created by Shuzu Li on 19/05/15.
 */
public class Constants {

    //fragment
    public static final String CLIENT_FRAGMENT_TAG = "ClientFragment";
    public static final String NEWS_LIST_FRAGMENT_TAG = "NewsListFragment";
    public static final String USER_LOGIN_FRAGMENT_TAG = "UserLoginFragment";
    public static final String NEWS_DETAIL_FRAGMENT_TAG = "NewsDetailFragment";

    public static final String SELECTED_CATEGORY_ID = "selectedCategoryId";
    public static final int NO_SELECTION = -1;
    public static final int REQUEST_SOCKET_TIMEOUT = 20000;
    public static final String DEFAULT_USER_NOTIFICATIONS_NEW_MESSAGE_RINGTONE = "content://settings/system/notification_sound";
    public static final boolean IS_UPDATE_ACTIONBAR = false;
    public static final String KEY_UPDATE_ACTIONBAR = "keyUpdateActionbar";

    //Key
    //MainActivity
    public static final String KEY_WAITING = "waitingStatus";
    public static final String KEY_SEARCH_VIEW_TEXT = "keySearchViewText";
    public static final String KEY_SEARCH_VIEW_EXPANDED = "keySearchViewExpanded";

    //NewsListFragment
    public static final String KEY_SELECTED_NEWS_ID = "keySelectedNewsId";
    public static final String KEY_NEWS_LIST = "keyNewsList";
    //NewsDetailFragment
    public static final String KEY_NEWS_DETAIL = "keyNewsDetail";

//    UserSession
    public static final String IS_USER_LOGIN = "isUserLoggedIn";
    public static final String KEY_USER_NAME = "general_user_name";
    public static final String KEY_USER_ID = "general_user_id";
    public static final String KEY_USER_EMAIL = "general_user_email";
    public static final String KEY_SETTINGS_USER_NOTIFICATIONS_NEW_MESSAGE_VIBRATE = "notifications_new_message_vibrate";
    public static final String KEY_SETTINGS_USER_NOTIFICATIONS_NEW_MESSAGE = "notifications_new_message";
    public static final String KEY_SETTINGS_USER_NOTIFICATIONS_NEW_MESSAGE_RINGTONE = "notifications_new_message_ringtone";

    //Api
    public static final String API_BASE_URL = "http://wnews.timepic.net/wnews/api/";
    public static final String API_KEY = "hXVBQPSennzh46Xp";
    public static final String API_ARGUMENTS = "key=" + API_KEY;
    /*
    get news list
    exp:http://wnews.timepic.net/wnews/api/newslist/category_id/0?key=hXVBQPSennzh46Xp
     */
    public static final String API_NEWS_LIST = API_BASE_URL +"newslist/category_id/%d?"+API_ARGUMENTS;
    public static final String API_NEWS_SEARCH = API_BASE_URL +"search/keyword/%s?"+ API_ARGUMENTS;
    public static final String API_USER_LOGIN = API_BASE_URL +"login/?username=%s&password=%s&"+API_ARGUMENTS;
    public static final String API_USER_SYNC_SETTINGS = API_BASE_URL +"settingsUpdate/?uid=%d&settings=%s&"+API_ARGUMENTS;


}
