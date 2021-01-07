package com.example.womenvirtualsecurityguard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    TextView currentname, currentnumber;
    private FirebaseAuth mfirebaseauth;
    String User_name, User_Mobile, MobileFromDB;
    String NameFromDB;
    LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alert_dialog);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            Button wbutton = dialog.findViewById(R.id.retry);
            wbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            });
            dialog.show();
        } else{
            loadingDialog=new LoadingDialog(HomeActivity.this);
            loadingDialog.StartLoadingDialog();

            currentname = findViewById(R.id.currentusername);
        currentnumber = findViewById(R.id.currentusernumber);
        mfirebaseauth = FirebaseAuth.getInstance();
        final MediaPlayer siren = MediaPlayer.create(this, R.raw.police_siren);
        CardView police_sound = this.findViewById(R.id.policesiren);
        CardView location = findViewById(R.id.locationtracking);
        CardView AddContact = findViewById(R.id.addContact);
        CardView Alert = findViewById(R.id.alert);


        police_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siren.start();
            }

        });


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
                intent.putExtra("loginNumber", MobileFromDB);
                startActivity(intent);

            }
        });

        AddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, contact_popup.class);
                intent.putExtra("loginNumber", MobileFromDB);
                startActivity(intent);
            }
        });

        Alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,Sending_Message.class);
                startActivity(intent);
            }
        });


    }

}

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
         isUser();

        } else {
            // No user is signed in
        }


    }
    public void isUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String ID = user.getPhoneNumber();
        final String userEnteredMobile = Objects.requireNonNull(ID);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Login");
        Query checkUser = reference.orderByChild("mobilenumber").equalTo(userEnteredMobile);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                      NameFromDB = dataSnapshot.child(userEnteredMobile).child("username").getValue(String.class);
                      MobileFromDB = dataSnapshot.child(userEnteredMobile).child("mobilenumber").getValue(String.class);
                      currentname.setText(NameFromDB);
                      currentnumber.setText(MobileFromDB);
                      loadingDialog.DismissDialog();


                }
                else {
                    Toast.makeText(getApplicationContext(),"No ID",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
