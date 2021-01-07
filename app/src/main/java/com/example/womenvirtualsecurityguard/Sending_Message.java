package com.example.womenvirtualsecurityguard;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Sending_Message extends AppCompatActivity {
EditText message;
LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending__message);
        loadingDialog=new LoadingDialog(Sending_Message.this);
        loadingDialog.StartLoadingDialog();
        message=findViewById(R.id.message);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String ID = user.getPhoneNumber();
        final String userEnteredMobile = Objects.requireNonNull(ID);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Login");
        Query checkUser = reference.orderByChild("mobilenumber").equalTo(userEnteredMobile);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String NameFromDB = dataSnapshot.child(userEnteredMobile).child("username").getValue(String.class);
                    Double longitudefromDB = dataSnapshot.child(userEnteredMobile).child("Location").child("longtitude").getValue(Double.class);
                    Double latitudefromDB = dataSnapshot.child(userEnteredMobile).child("Location").child("latitude").getValue(Double.class);
                    message.setText("I am "+NameFromDB+"\nI am in trouble. Please Help me.\nMy location is:"+latitudefromDB.toString()+","+longitudefromDB.toString()+"");
                    loadingDialog.DismissDialog();
                }
                else {
                    Toast.makeText(getApplicationContext(),"No Location found",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
