package com.OneWindowSol.waqarbscs.onewindownamaztimer;


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
        /*
        String text = "<html><body>"
                + "<img src=\"logo.png\" alt=\"LOGO\">"

                +" <h2 align=\"right\">One Window Solution</h2>"
                +"<h5 align=\"right\">| Complete Solution Under One Roof |</h5>"
                +"<h1> About Us</h1>"
                +"<p align=\"justify\">OneWindow solution is all about providing business & security solutions under one roof with the help of latest technology. We strive to facilitate your business with a set of skills which we have learnt over the years in this sector.</p> "
                + "<p align=\"justify\"> We are the team of technology lover who love technology and implement the technology according to clients need in order to resolve business problems. we believe that the technology world is changing rapidly and business which walk side by side with technology will succeed in future, we are providing best technology services to our clients by using latest web technologies, mobile application development and security solutions.</p>"

                +"<h1> Contact Us </h1>"
                +"<a href=\"https://www.facebook.com/OneWindowSol\"><img src=\"facebook.png\" alt=\"Facebook\"></a>"
                +"<a href=\"https://www.facebook.com/OneWindowSol\"><img src=\"linklend-01.png\" alt=\"linklend\"></a>"
                +"<a href=\"https://www.facebook.com/OneWindowSol\"><img src=\"skype-01.png\" alt=\"Skype\"></a>"
                +"<a href=\"https://www.facebook.com/OneWindowSol\"><img src=\"twitter-01.png\" alt=\"Twitter\"></a>"
                 +"<a href=\"https://www.facebook.com/OneWindowSol\"><img src=\"web-01.png\" alt=\"Web\"></a>"
                 +"<a href=\"https://www.facebook.com/OneWindowSol\"><img src=\"Email-01.png\" alt=\"Email\"></a"
                 +"<a href=\"https://www.facebook.com/OneWindowSol\"><img src=\"call-01.png\" alt=\"Call\"></a>"

                + "<p align=\"justify\">"

                + getString(R.string.Content)

                + "</p> "

                + "</body></html>";*/
        webView.loadUrl("file:///android_asset/www/about.html");
       // webView.loadData(text,"text/html", "utf-8");
        return ParentView;
    }

}
