package com.example.womenvirtualsecurityguard;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialog {
    Activity activity;
    AlertDialog alertDialog;
    LoadingDialog(Activity myActivity){
        activity=myActivity;
    }
    void StartLoadingDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater=activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.progress_dialog,null));
        builder.setCancelable(false);
        alertDialog=builder.create();
        alertDialog.show();

    }

    void DismissDialog(){
        alertDialog.dismiss();
    }
}
