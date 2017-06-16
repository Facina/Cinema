package com.example.android.cinemusp.android;


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
import android.widget.Toast;

import com.example.android.cinemusp.R;
import com.example.android.cinemusp.modelo.Filme;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import static android.R.attr.id;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    String adress = "https://web-hosting-test.000webhostapp.com/test.php";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_page, container, false);

        final ImageView session = (ImageView) rootView.findViewById(R.id.home_session);

        final Context context = rootView.getContext();

        final Filme movie = new Filme();


        //Create a list of movies
        PostResponseAsyncTask task = new PostResponseAsyncTask(getActivity(), false, new AsyncResponse() {

            @Override
            public void processFinish(String s) {

                try {
                    Log.e("getData", "parsing");
                    JSONArray js = new JSONArray(s);
                    JSONObject filme = null;

                    for (int i = 0; i < js.length(); i++) {
                        filme = js.getJSONObject(i);
                        Toast.makeText(getActivity(), "" + i, Toast.LENGTH_LONG);
                        movie.setNome(filme.getString("nomeFilme"));
                        movie.setClassificacao(filme.getString("classificacao"));
                        movie.setSinopse(filme.getString("sinopse"));
                        movie.setImgLink(filme.getString("imgLink"));
                        movie.setDuracao(filme.getInt("duracao"));
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        try {
                            movie.setDataEstreia(new java.sql.Date(dateFormat.parse(filme.getString("dataEstreia")).getTime()));
                            movie.setDataSaida(new java.sql.Date(dateFormat.parse(filme.getString("dataSaida")).getTime()));

                            movie.setIdFilme(filme.getInt("idFilme"));
                        } catch (Exception e) {
                            Log.e("Filmefragmetn", "" + filme.getString("nomeFilme"));
                            Log.e("FilmeFragment", "data exception" + filme.getString("dataEstreia") + " asd " + filme.getString("dataSaida"));
                            e.printStackTrace();
                        }


                    }
                    Log.e("getData", "parsed");

                    Picasso.with(context).load(movie.getImgLink()).into(session);


                } catch (Exception e) {
                    //Toast.makeText(getContext(), "Filme não encontrado :(", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });

        task.execute(adress);



        TextView support = (TextView) rootView.findViewById(R.id.suppor_textView);


        session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Intent filmeIntent = new Intent(getActivity(), MovieDetails.class);
                filmeIntent.putExtra("id", movie.getidFilme());

                startActivity(filmeIntent);
            }
        });

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
