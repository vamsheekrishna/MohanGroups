package com.example.mybusinesstracker.basecalss;

public interface OnNtworkChangeListener {

    void onNetworkConnected();
    void onNetworkChanging();
    void onNetworkDisConnected();
    void checkNetworkConnection();
    boolean isNetworkAvailable();

}
