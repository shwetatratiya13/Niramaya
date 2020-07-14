package com.technoplanet.p360.FragmentUtils;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.technoplanet.p360.R;

/**
 * Created by BHARGAV on 02/11/2018.
 */

public class HealthLibFrag extends Fragment {

    private WebView webview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_healthlib,container,false);

        webview = v.findViewById(R.id.webview);
        webview.loadUrl("https://www.livescience.com/health");
        return v;
    }
}
