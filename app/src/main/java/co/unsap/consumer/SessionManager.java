package co.unsap.consumer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by mac on 7/2/18.
 */

public class SessionManager {

    static String SESSION_ID="session_id";
    static String DEVICE_ID="device_id";
    static String USER_NAME="user_name";
    static String USER_mobile="user_mobile";
    static String USER_IMAGE="user_image";


    public static void setSessionId(Context context, String session_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SESSION_ID, session_id);
        editor.apply();
    }



    public static String getSessionId(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(SESSION_ID, "0");
    }




    public static void setDeviceId(Context context, String session_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DEVICE_ID, session_id);
        editor.apply();
    }



    public static String getDeviceId(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(DEVICE_ID, "0");
    }


    public static String getUserName(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USER_NAME, "user name");
    }


    public static void setUserName(Context context, String session_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, session_id);
        editor.apply();
    }



    public static String getUSER_mobile(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USER_mobile, "Mobile");
    }



    public static void setUSER_mobile(Context context, String session_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_mobile, session_id);
        editor.apply();
    }



    public static String getUserImage(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USER_IMAGE, "Mobile");
    }



    public static void setUserImage(Context context, String session_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_IMAGE, session_id);
        editor.apply();
    }



}
