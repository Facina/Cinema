
package com.example.android.cinemusp;
import java.text.SimpleDateFormat;

import java.sql.Date;
import android.content.Intent;
import android.content.res.Configuration;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;



/**
 * {@link Fragment} that displays a list of number vocabulary words.
 */
public class FilmeFragment extends Fragment {

    View rootView;

    String adress = "https://web-hosting-test.000webhostapp.com/conn_cartaz.php";
    InputStream data = null;
    String line = null;
    String result = null;
    ArrayList<Filme> listaFilme = new ArrayList<Filme>();



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getActivity().setContentView(R.layout.movie_list);

        } else {
            getActivity().setContentView(R.layout.movie_list);
        }
    }

    public FilmeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.movie_list, container, false);


        // MovieList movies = new MovieList();
        // final ArrayList<Movie> list = movies.getMovies();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        // MovieAdapter adapter = new MovieAdapter(getActivity(), list);
        final FilmeAdapter adapter = new FilmeAdapter(getActivity(), listaFilme);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // movie_list.xml layout file.
        final ListView listView = (ListView) rootView.findViewById(R.id.list);


        // Create a list of movies
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
                        Filme teste = new Filme();
                        teste.setNome(filme.getString("nomeFilme"));
                        teste.setClassificacao(filme.getString("classificacao"));
                        teste.setSinopse(filme.getString("sinopse"));
                        teste.setImgLink(filme.getString("imgLink"));
                        teste.setDuracao(filme.getInt("duracao"));
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        try {
                            teste.setDataEstreia(new java.sql.Date(dateFormat.parse(filme.getString("dataEstreia")).getTime()));
                            teste.setDataSaida(new java.sql.Date(dateFormat.parse(filme.getString("dataSaida")).getTime()));

                            teste.setIdFilme(filme.getInt("idFilme"));
                        } catch (Exception e) {
                            Log.e("Filmefragmetn", "" + filme.getString("nomeFilme"));
                            Log.e("FilmeFragment", "data exception" + filme.getString("dataEstreia") + " asd " + filme.getString("dataSaida"));
                            e.printStackTrace();
                        }

                        listaFilme.add(teste);
                    }
                    Log.e("getData", "parsed");
                    listView.setAdapter(adapter);


                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Filme não encontrado :(", Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }

            }
        });
        task.execute(adress);
        task.setEachExceptionsHandler(new EachExceptionsHandler() {
            @Override
            public void handleIOException(IOException e) {
                Toast.makeText(getActivity(), "Error with internet or web server.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleMalformedURLException(MalformedURLException e) {
                Toast.makeText(getActivity(), "Error with the URL.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleProtocolException(ProtocolException e) {
                Toast.makeText(getActivity(), "Error with protocol.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                Toast.makeText(getActivity(), "Error with text encoding.", Toast.LENGTH_LONG).show();
            }
        });

        Log.e("teste", "" + listaFilme.size());


        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                Filme movie = listaFilme.get(position);


                Intent filmeIntent = new Intent(getActivity(), MovieDetails.class);
                filmeIntent.putExtra("id", movie.getidFilme());

                startActivity(filmeIntent);

            }
        });


        return rootView;
    }



}