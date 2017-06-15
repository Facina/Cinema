package com.example.android.cinemusp;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.button;
import static android.R.attr.onClick;
import static com.example.android.cinemusp.R.mipmap.search;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    String adress1 = "https://web-hosting-test.000webhostapp.com/conn_pesquisa_cartaz.php";
    String adress = null;
    InputStream data = null;
    String line = null;
    String result = null;

    ArrayList<Filme> listaFilme = new ArrayList<Filme>();



    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //listView.setAdapter(adapter);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.search_list, container, false);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        // Create a list of movies
       // getData();

        Log.e("teste", "" +listaFilme.size());

       ImageView button = (ImageView) rootView.findViewById(R.id.glass);





        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.

        // MovieAdapter adapter = new MovieAdapter(getActivity(), list);
        final FilmeAdapter adapter = new FilmeAdapter(getActivity(), listaFilme);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // movie_list.xml layout file.
        final ListView listView = (ListView) rootView.findViewById(R.id.search_list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText editText = (EditText) rootView.findViewById(R.id.seach_edit);
                String text = editText.getText().toString();




                if(text.equals("")){
                    Toast.makeText(getActivity(), "Nada foi digitado ;)", Toast.LENGTH_LONG).show();

                }
                else {

                    adress = adress1+"?nomefilme="+text;


                    PostResponseAsyncTask task = new PostResponseAsyncTask(getActivity(), true,new AsyncResponse() {

                        @Override
                        public void processFinish(String s){

                            try{
                                Log.e("getData","parsing");
                                JSONArray js = new JSONArray(s);
                                JSONObject filme = null;

                                for(int i=0; i  < js.length();i++) {
                                    filme = js.getJSONObject(i);
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
                                    listView.setAdapter(adapter);

                                    Log.e("getData","parsed");


                            }catch (Exception e){
                                Toast.makeText(getActivity(), "Filme não encontrado :(", Toast.LENGTH_LONG).show();

                                e.printStackTrace();
                            }

                        }
                    });
                        task.setLoadingMessage("Procurando filme...");
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



                }




            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                Filme movie = listaFilme.get(position);

                Log.e("o id era", " test:"+movie.getidFilme());

                Intent filmeIntent = new Intent(getActivity(), MovieDetails.class);
                filmeIntent.putExtra("id", movie.getidFilme());

                startActivity(filmeIntent);

            }
        });

        return rootView;
    }




}
