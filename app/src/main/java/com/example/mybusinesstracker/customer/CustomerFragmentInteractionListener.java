package com.example.mybusinesstracker.customer;

import androidx.fragment.app.Fragment;

import com.example.mybusinesstracker.customer.ui.customer.Customer;

import java.util.ArrayList;

public interface CustomerFragmentInteractionListener {
    void createCustomer(Customer customer, boolean isUpdateCustomer);
    void deleteCustomer(Customer customer, Fragment createCustomer);
    ArrayList<Customer> getAllCustomers();
    void goToCreateCustomer();
    void goToUpdateCustomer(Customer customer);
}
