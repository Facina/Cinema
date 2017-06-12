package com.example.android.cinemusp;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static android.R.attr.id;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_page, container, false);

        ImageView session = (ImageView) rootView.findViewById(R.id.home_session);

        Context context = rootView.getContext();
        Picasso.with(context).load("http://cdn2-www.comingsoon.net/assets/uploads/gallery/wonder-woman/wwposter5.jpg").into(session);


        TextView support = (TextView) rootView.findViewById(R.id.suppor_textView);

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"support@cinemusp.com"});
                intent.putExtra(Intent.EXTRA_TEXT,"Tell us what's on your mind !");

                intent.setData(Uri.parse("mailto:"));

                if(intent.resolveActivity(getActivity().getPackageManager()) != null)
                    startActivity(Intent.createChooser(intent, "Send Email"));
                else
                    Log.v( "MainActivity","cannot resolveActivity mail");
            }


        });



        return rootView;
    }

}
