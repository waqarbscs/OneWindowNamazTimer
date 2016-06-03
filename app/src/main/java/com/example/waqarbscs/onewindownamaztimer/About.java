package com.example.waqarbscs.onewindownamaztimer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class About extends Fragment {

    WebView webView;
    public About() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ParentView=inflater.inflate(R.layout.fragment_about, container, false);
        webView=(WebView)ParentView.findViewById(R.id.textView);
        String text = "<html><body>"

                + "<p align=\"justify\">"

                + getString(R.string.Content)

                + "</p> "

                + "</body></html>";
        webView.loadData(text,"text/html", "utf-8");
        return ParentView;
    }

}
