package com.example.android.cinemusp.android;

import android.support.design.widget.TabLayout;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.android.cinemusp.R;

public class MainActivity extends AppCompatActivity {


         //ON CREATE ACTIVITY LIFECYCLE

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Set the content of the activity to use the activity_main.xml layout file
            setContentView(R.layout.activity_main);

            // Find the view pager that will allow the user to swipe between fragments
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

            // Create an adapter that knows which fragment should be shown on each page
            FragmentAdapter adapter = new FragmentAdapter(this, getSupportFragmentManager());

            // Set the adapter onto the view pager
            viewPager.setAdapter(adapter);

            // Find the tab layout that shows the tabs
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

            // Connect the tab layout with the view pager. This will
            //   1. Update the tab layout when the view pager is swiped
            //   2. Update the view pager when a tab is selected
            //   3. Set the tab layout's tab names with the view pager's adapter's titles
            //      by calling onPageTitle()
            tabLayout.setupWithViewPager(viewPager);






        }





    protected void contactUs(View button){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"support@cinemusp.com"});
        intent.putExtra(Intent.EXTRA_TEXT,"Tell us what's on your mind !");

        intent.setData(Uri.parse("mailto:"));

        if(intent.resolveActivity(getPackageManager()) != null)
            startActivity(Intent.createChooser(intent, "Send Email"));
        else
            Log.v( "MainActivity","cannot resolveActivity mail");
    }




}
