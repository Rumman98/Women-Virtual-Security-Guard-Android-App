package com.example.womenvirtualsecurityguard;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;
    public static final String Is_Login="isloggedin";
    public static final String KEY_UserName="username";
    public static final String KEY_Mobile="mobile";

    public SessionManager (Context _context,String sessionName){
        context=_context;
        usersSession=context.getSharedPreferences(sessionName,Context.MODE_PRIVATE);
        editor=usersSession.edit();
    }

    public void createLoginSession(String username,String mobile){
        editor.putBoolean(Is_Login,true);
        editor.putString(KEY_UserName,username);
        editor.putString(KEY_Mobile,mobile);
        editor.commit();
    }

    public HashMap<String,String> getUserDetailFromSession(){
        HashMap<String,String> userData=new HashMap<String, String>();
        userData.put(KEY_UserName,usersSession.getString(KEY_UserName,null));
        userData.put(KEY_Mobile,usersSession.getString(KEY_Mobile,null));
        return userData;
    }
    public boolean checkLogin(){
        if (usersSession.getBoolean(Is_Login,true)){
            return true;
        }
        else {
            return false;
        }
    }
}
