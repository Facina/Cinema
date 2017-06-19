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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cinemusp.R;
import com.example.android.cinemusp.modelo.Filme;
import com.example.android.cinemusp.persistencia.CinemaException;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import static android.R.attr.port;


/**
 * @author Grupo 4 - Turma B POO
 * SubClasse de {@link Fragment} da tela principal.
 */
public class HomeFragment extends Fragment {

    String adress = "https://web-hosting-test.000webhostapp.com/pesquisa_proxima_sessao.php";

    public HomeFragment() {
    }

    /**
     * Cria um novo fragmento inflando o arquivo xml
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return rootView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_page, container, false);

        final ImageView session = (ImageView) rootView.findViewById(R.id.home_session);

        final Context context = rootView.getContext();

        final Filme movie = new Filme();


        //Efetuando pesquisa no banco de dados da proxima sessao não lotada da semana
        PostResponseAsyncTask task = new PostResponseAsyncTask(getActivity(), false, new AsyncResponse() {

            @Override
            public void processFinish(String s) {

                try {
                    JSONObject js = new JSONObject(s);
                    JSONObject filme = null;


                        filme = js;
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
                            e.printStackTrace();
                        }


                    } catch (CinemaException e1) {
                    e1.printStackTrace();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                    Picasso.with(context).load(movie.getImgLink()).into(session);


                }


        });

        task.execute(adress);



        TextView support = (TextView) rootView.findViewById(R.id.suppor_textView);


        session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent filmeIntent = new Intent(getActivity(), MovieDetails.class);
                filmeIntent.putExtra("id", movie.getidFilme());
                filmeIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(filmeIntent);

            }
        });

        //Mandando e-mail para o suporte
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
