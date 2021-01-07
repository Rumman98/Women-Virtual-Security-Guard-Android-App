package com.example.womenvirtualsecurityguard;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class View_Added_Contact extends AppCompatActivity {
ListView listView;
List<Insert_Contact> contactList;
DatabaseReference contactreference;
LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__added__contact);
        loadingDialog=new LoadingDialog(View_Added_Contact.this);
        loadingDialog.StartLoadingDialog();
        listView=findViewById(R.id.listview);
        contactList=new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String ID = user.getPhoneNumber();
        final String userEnteredMobile = Objects.requireNonNull(ID);
        contactreference= FirebaseDatabase.getInstance().getReference("Login");
        Query checkUser = contactreference.orderByChild("mobilenumber").equalTo(userEnteredMobile);

        checkUser.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key=contactreference.child(userEnteredMobile).child("Emergency_Contact").getKey();

                assert key != null;
                for (DataSnapshot ds:dataSnapshot.child(key).getChildren()){
                    Insert_Contact getcontact = ds.getValue(Insert_Contact.class);
                    contactList.add(getcontact);
                }
                contactListAdopter listAdopter = new contactListAdopter(View_Added_Contact.this, contactList);
                listView.setAdapter(listAdopter);
                loadingDialog.DismissDialog();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}