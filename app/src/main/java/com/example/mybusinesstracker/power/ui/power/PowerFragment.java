package com.example.mybusinesstracker.power.ui.power;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mybusinesstracker.R;

public class PowerFragment extends Fragment {

    private PowerViewModel mViewModel;
    WebView webView;
    public static PowerFragment newInstance() {
        return new PowerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.power_fragment, container, false);

        webView = view.findViewById(R.id.webview);
        init();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = ViewModelProviders.of(this).get(PowerViewModel.class);
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        String url = getString(R.string.WEBSITE_ADDRESS);

        if (!url.isEmpty()) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(url);
        }
    }
}
