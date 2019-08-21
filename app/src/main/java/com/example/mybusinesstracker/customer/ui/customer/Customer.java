package com.example.mybusinesstracker.customer.ui.customer;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.mybusinesstracker.BR;
import com.example.mybusinesstracker.cloud_firestore.OnCloudFireStoreInteraction;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Customer extends BaseObservable implements Serializable, Parcelable, OnCloudFireStoreInteraction {
    public static final String KEY_CUSTOMER_NAME = "customerName";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PHONE_NUMBER = "phoneNumber";
    public static final String KEY_EMAIL_ID = "emailID";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_LABOR_CHARGE = "laborCharge";
    public static final String KEY_COLOR_ID = "colorID";
    private String customerName = "";
    private String address = "";
    private String phoneNumber = "";
    private String emailID = "";
    private int amount;
    private int laborCharge;
    private int colorID;

    public Customer() {

    }
    public Customer(Map<String, Object> data) {
        customerName = (String) data.get(KEY_CUSTOMER_NAME);
        address = (String) data.get(KEY_ADDRESS);
        phoneNumber = (String) data.get(KEY_PHONE_NUMBER);
        emailID = (String) data.get(KEY_EMAIL_ID);
        String temp = (String) data.get(KEY_AMOUNT);
        if(null != temp) {
            amount = Integer.parseInt(temp);

        }
        temp = (String) data.get(KEY_LABOR_CHARGE);
        if(null != temp) {
            laborCharge = Integer.parseInt(temp);

        }
        temp = (String) data.get(KEY_COLOR_ID);
        if(null != temp) {
            colorID = Integer.parseInt(temp);

        }
    }

    protected Customer(Parcel in) {
        customerName = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
        emailID = in.readString();
        amount = in.readInt();
        laborCharge = in.readInt();
        colorID = in.readInt();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    @Bindable
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
        notifyPropertyChanged(BR.customerName);
    }
    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }
    @Bindable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        notifyPropertyChanged(BR.phoneNumber);
    }
    @Bindable
    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
        notifyPropertyChanged(BR.emailID);
    }
    @Bindable
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        notifyPropertyChanged(BR.amount);
    }
    @Bindable
    public int getLaborCharge() {
        return laborCharge;
    }

    public void setLaborCharge(int laborCharge) {
        this.laborCharge = laborCharge;
        notifyPropertyChanged(BR.laborCharge);
    }
    @Bindable
    public int getColorID() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
        notifyPropertyChanged(BR.colorID);
    }
    @Override
    public HashMap<String, Object> getHashMap() {
        HashMap<String, String> data = new HashMap<>();
        data.put(KEY_CUSTOMER_NAME,customerName);
        data.put(KEY_ADDRESS,address);
        data.put(KEY_PHONE_NUMBER,phoneNumber);
        data.put(KEY_EMAIL_ID,emailID);
        data.put(KEY_AMOUNT, String.valueOf(amount));
        data.put(KEY_LABOR_CHARGE, String.valueOf(laborCharge));
        data.put(KEY_COLOR_ID, String.valueOf(colorID));
        return data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(customerName);
        dest.writeString(address);
        dest.writeString(phoneNumber);
        dest.writeString(emailID);
        dest.writeInt(amount);
        dest.writeInt(laborCharge);
        dest.writeLong(colorID);
    }
}
