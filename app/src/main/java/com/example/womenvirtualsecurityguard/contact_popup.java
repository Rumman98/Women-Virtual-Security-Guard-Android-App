package com.example.womenvirtualsecurityguard;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class contact_popup extends AppCompatActivity implements ContactAdapter.SelectedUser {
    RecyclerView recyclerView;
    List<Contact> contactList;
    ContactAdapter adapter;
    TextView new_customer;
    int maxid=0;
    String MobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_popup);

        EditText editText = findViewById(R.id.search_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        TextView textView = findViewById(R.id.textclose);
        textView.setOnClickListener(v -> contact_popup.this.finish());

        recyclerView = findViewById(R.id.contact_recyler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactList = new ArrayList<>();
        adapter = new ContactAdapter(this,contactList);
        recyclerView.setAdapter(adapter);
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_CONTACTS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (response.getPermissionName().equals(Manifest.permission.READ_CONTACTS)){
                            getContacts();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(contact_popup.this, "Grant Permission",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

        Button viewcontact=findViewById(R.id.View_addedContact);
        viewcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(contact_popup.this,View_Added_Contact.class);
                intent.putExtra("UserNumber",MobileNumber);
                startActivity(intent);
            }
        });
    }

    private void filter(String text){
        ArrayList<Contact> filteredList = new ArrayList<>();
        for (Contact item: contactList){
            if (item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    private void getContacts() {
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        while (phones.moveToNext()){
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String phoneUri = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

            Contact contact = new Contact(name, phoneNumber,phoneUri);
            contactList.add(contact);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void selectedUser(Contact contact) {

       String AddedName=contact.getName();
       String AddedNumber=contact.getPhone();
       MobileNumber=getIntent().getStringExtra("loginNumber");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Login");
        Insert_Contact insert_contact=new Insert_Contact(AddedName,AddedNumber);
        reference.child(MobileNumber).child("Emergency_Contact").push().setValue(insert_contact);
        Intent intent=new Intent(contact_popup.this,View_Selected_Contact.class);
        intent.putExtra("selectedName",AddedName);
        intent.putExtra("selectedNumber",AddedNumber);
        startActivity(intent);
    }
}