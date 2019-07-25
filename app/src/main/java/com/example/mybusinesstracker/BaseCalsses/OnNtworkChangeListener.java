package com.example.mybusinesstracker.BaseCalsses;

public interface OnNtworkChangeListener {

    void onNetworkConnected();
    void onNetworkChanging();
    void onNetworkDisConnected();
    void checkNetworkConnection();
    boolean isNetworkAvailable();

}
