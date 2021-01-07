package com.example.womenvirtualsecurityguard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class contactListAdopter extends ArrayAdapter {
    private Activity mContext;
    List<Insert_Contact> contactList;

    public contactListAdopter(Activity mContext,List<Insert_Contact> contactList){
        super(mContext,R.layout.addedcontactview,contactList);
        this.mContext=mContext;
        this.contactList=contactList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=mContext.getLayoutInflater();
        @SuppressLint("ViewHolder") View listItemView=inflater.inflate(R.layout.addedcontactview,null,true);
        TextView contactName=listItemView.findViewById(R.id.contactName);
        TextView contactNumber=listItemView.findViewById(R.id.contactNumber);
        Insert_Contact insert_contact=contactList.get(position);

        contactName.setText(insert_contact.getContact_name());
        contactNumber.setText(insert_contact.getContact_number());
        return listItemView;

    }
}
