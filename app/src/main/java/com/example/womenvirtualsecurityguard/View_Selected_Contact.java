package com.example.womenvirtualsecurityguard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class View_Selected_Contact extends AppCompatActivity {
    TextView Name,Number;
    Button okbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__selected__contact);

        String SelectedContactName=getIntent().getStringExtra("selectedName");
        String SelectedContactNumber=getIntent().getStringExtra("selectedNumber");
        Name=findViewById(R.id.contactName);
        Number=findViewById(R.id.contactNumber);
        Name.setText(SelectedContactName);
        Number.setText(SelectedContactNumber);
        okbtn=findViewById(R.id.View_addedContact);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View_Selected_Contact.this.finish();
            }
        });

    }
}