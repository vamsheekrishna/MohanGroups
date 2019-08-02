package com.example.mybusinesstracker.viewmodels;

import androidx.databinding.BaseObservable;

import com.example.mybusinesstracker.cloud_firestore.OnCloudFireStoreInteraction;
import com.example.mybusinesstracker.customer.ui.customer.Customer;
import com.example.mybusinesstracker.utilities.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class SalesViewModel extends BaseObservable implements Serializable, OnCloudFireStoreInteraction {
    public static final String DATE = "date";
    public static final String CABIN_ID = "cabinID";
    public static final String CUSTOMER_ID = "customerID";
    public static final String SALES_TYPE = "salesType";
    public static final String TOTAL_BLOCKS = "totalBlocks";
    public static final String ICE_AMOUNT = "iceAmount";
    public static final String LABOUR_CHARGES = "labourCharges";
    public static final String OTHER_AMOUNT = "otherAmount";
    public static final String TOTAL_AMOUNT = "totalAmount";
    public static final String PAID_AMOUNT = "paidAmount";
    public static final String DUE_AMOUNT = "dueAmount";
    public static final String NOTE = "note";
    private int ID;
    private Long date ;
    private String cabinID = null;
    private String customerID = null;
    private String salesType = null;
    private float totalBlocks = 0;
    private int iceAmount = 0;
    private int labourCharges = 0;
    private int otherAmount = 0;
    private int totalAmount = 0;
    private int paidAmount = 0;
    private int dueAmount = 0;
    private String note = null;
    private String dateString;
    private Customer selectedCustomer;
    private ArrayList<Integer> blockIDs = new ArrayList<>();

    public SalesViewModel( Map<String, Object> hashMap) {
        setDate(Long.valueOf((String) requireNonNull(hashMap.get(DATE))), "yyyy-MM-dd 'T'HH:mm");// ;
        cabinID = (String) hashMap.get(CABIN_ID);
        customerID = (String) hashMap.get(CUSTOMER_ID);
        salesType = (String) hashMap.get(SALES_TYPE);
        totalBlocks = Float.parseFloat((String) requireNonNull(hashMap.get(TOTAL_BLOCKS)));
        iceAmount = Integer.parseInt((String) requireNonNull(hashMap.get(ICE_AMOUNT)));
        labourCharges = Integer.parseInt((String) requireNonNull(hashMap.get(LABOUR_CHARGES)));
        otherAmount = Integer.parseInt((String) requireNonNull(hashMap.get(OTHER_AMOUNT)));
        totalAmount = Integer.parseInt((String) requireNonNull(hashMap.get(TOTAL_AMOUNT)));
        paidAmount= Integer.parseInt((String) requireNonNull(hashMap.get(PAID_AMOUNT)));
        dueAmount = Integer.parseInt((String) requireNonNull(hashMap.get(DUE_AMOUNT)));
        note = (String) hashMap.get(NOTE);
    }
    public SalesViewModel() {

    }

    @Override
    public HashMap<String, String> getHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(DATE, String.valueOf(date));
        hashMap.put(CABIN_ID, cabinID);
        hashMap.put(CUSTOMER_ID, customerID);
        hashMap.put(SALES_TYPE, salesType);
        hashMap.put(TOTAL_BLOCKS, String.valueOf(totalBlocks));
        hashMap.put(ICE_AMOUNT, String.valueOf(iceAmount));
        hashMap.put(LABOUR_CHARGES, String.valueOf(labourCharges));
        hashMap.put(OTHER_AMOUNT, String.valueOf(otherAmount));
        hashMap.put(TOTAL_AMOUNT, String.valueOf(totalAmount));
        hashMap.put(PAID_AMOUNT, String.valueOf(paidAmount));
        hashMap.put(DUE_AMOUNT, String.valueOf(dueAmount));
        hashMap.put(NOTE, String.valueOf(note));
        return hashMap;
    }

    public int getID() {
        return ID;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
        notifyChange();
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        this.date = date;
        dateString = Utils.getStringFromDate(calendar, pattern);
    }

    public String getCabinID() {
        return cabinID;
    }

    public void setCabinID(String cabinID) {
        this.cabinID = cabinID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public float getTotalBlocks() {
        return totalBlocks;
    }

    public void setTotalBlocks(float totalBlocks) {
        this.totalBlocks = totalBlocks;
        setIceAmount((int) (selectedCustomer.getAmount()*totalBlocks));
        setLabourCharges((int) (selectedCustomer.getLaborCharge()*totalBlocks));
    }

    public int getIceAmount() {
        return iceAmount;
    }

    public void setIceAmount(int iceAmount) {
        this.iceAmount = iceAmount;
        setTotalAmount(getIceAmount()+getLabourCharges()+getOtherAmount());
        notifyChange();
    }

    public int getLabourCharges() {
        return labourCharges;
    }

    public void setLabourCharges(int labourCharges) {
        this.labourCharges = labourCharges;
        setTotalAmount(getIceAmount()+getLabourCharges()+getOtherAmount());
        notifyChange();
    }

    public int getOtherAmount() {
        return otherAmount;
    }

    public void setOtherAmount(int otherAmount) {
        this.otherAmount = otherAmount;
        setTotalAmount(getIceAmount()+getLabourCharges()+getOtherAmount());
        notifyChange();
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
        setDueAmount(getTotalAmount()-getPaidAmount());
        notifyChange();
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(int paidAmount) {
        this.paidAmount = paidAmount;
        setDueAmount(getTotalAmount()-getPaidAmount());
    }

    public int getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(int dueAmount) {
        this.dueAmount = dueAmount;
        notifyChange();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    private Calendar getCalendarObject() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar;
    }

    public int getYear(){
        return getCalendarObject().get(Calendar.YEAR);
    }
    public int getMonth(){
        return getCalendarObject().get(Calendar.MONTH);
    }
    public int getDay(){
        return getCalendarObject().get(Calendar.DAY_OF_MONTH);
    }
}
