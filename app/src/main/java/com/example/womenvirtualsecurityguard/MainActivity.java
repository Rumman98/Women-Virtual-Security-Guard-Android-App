package com.example.womenvirtualsecurityguard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {
    EditText uname, mobile;
    Button login_button;
    FirebaseAuth firebaseAuth;
    CountryCodePicker ccp;
    String Uname,Umobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager connectivityManager= (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo==null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            Dialog dialog=new Dialog(this);
            dialog.setContentView(R.layout.alert_dialog);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            Button wbutton=dialog.findViewById(R.id.retry);
            wbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            });
            dialog.show();
        }
        else {
            uname=findViewById(R.id.username);
            mobile=findViewById(R.id.Number);
            login_button=findViewById(R.id.login);
            firebaseAuth = FirebaseAuth.getInstance();
            ccp=findViewById(R.id.ccp);
            ccp.registerCarrierNumberEditText(mobile);

            login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Uname=uname.getText().toString();
                    Umobile=mobile.getText().toString();

                    if (TextUtils.isEmpty(Uname)){
                        Toast.makeText(MainActivity.this,"Please Enter your name",Toast.LENGTH_SHORT).show();
                    }
                    else if (TextUtils.isEmpty(Umobile)){
                        Toast.makeText(MainActivity.this,"Please Enter your Mobile Number",Toast.LENGTH_SHORT).show();
                    }
                    else if(!TextUtils.isEmpty(Uname) && !TextUtils.isEmpty(Umobile)){
                        Toast.makeText(MainActivity.this,"Registration Success",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,OTPVerification.class);
                        intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace("",""));
                        intent.putExtra("Username",Uname);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(MainActivity.this,"Something wrong",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent=new Intent(MainActivity.this,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}


