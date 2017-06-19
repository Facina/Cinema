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


    /**
     * @author Grupo 4 - Turma B POO
     * Setando o viewPager inicialmente , criando os fragmentos
     * @param savedInstanceState
     */
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //inflou o xml
            setContentView(R.layout.activity_main);

            //criou o viewPager
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

            //Criou o adaptador de fragmentos
            FragmentAdapter adapter = new FragmentAdapter(this, getSupportFragmentManager());

            viewPager.setAdapter(adapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

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
