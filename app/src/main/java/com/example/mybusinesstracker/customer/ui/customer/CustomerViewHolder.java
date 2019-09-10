package com.example.mybusinesstracker.customer.ui.customer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinesstracker.R;

public class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    static final int REQUEST_PHONE_CALL = 10;
    public View mRoot;
    TextView mName;
    TextView mNumber;
    LinearLayout mCustomer;
    Button mCall, mMessage;
    Context mContext;
    private Intent intent;


    public CustomerViewHolder(@NonNull View itemView, Context mContext) {
        super(itemView);
        mRoot = itemView;
        mName = itemView.findViewById(R.id.customer_name);
        mNumber = itemView.findViewById(R.id.customer_number);
        mCustomer = itemView.findViewById(R.id.customer_details);
        mCall = itemView.findViewById(R.id.call);
        this.mContext = mContext;
        mCustomer.setOnClickListener(this);
        itemView.findViewById(R.id.call).setOnClickListener(this);
        itemView.findViewById(R.id.msg).setOnClickListener(this);
    }

    public Intent initiateCall(){
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call:
                String number = mNumber.getText().toString();
                 intent  = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(number));
                break;
            case R.id.msg:
                break;
            case R.id.customer_details:

                break;
        }
    }
}
