package com.example.mybusinesstracker.services;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.power.ui.power.PowerViewModel;

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
//        String url = getString(R.string.WEBSITE_ADDRESS);
        String url = "https://www.tssouthernpower.com/CPDCL_Home.portal?_nfpb=true&_pageLabel=CPDCL_Home_portal_page_289";

        if (!url.isEmpty()) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    Toast.makeText(getContext(),"Web page loading error", Toast.LENGTH_SHORT).show();
                }
            });
            webView.loadUrl(url);
        }
    }
}
