package com.example.android.cinemusp.android;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.cinemusp.R;
import com.example.android.cinemusp.modelo.Filme;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author Grupo 4 - Turma B POO
 * SubClasse {@link Fragment}.
 * Classe de pesquisa de filme.
 */
public class SearchFragment extends Fragment {

    String adress1 = "https://web-hosting-test.000webhostapp.com/conn_pesquisa_cartaz.php";
    String adress = null;

    ArrayList<Filme> listaFilme = new ArrayList<Filme>();



    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.search_list, container, false);


        ImageView button = (ImageView) rootView.findViewById(R.id.glass);

        final FilmeAdapter adapter = new FilmeAdapter(getActivity(), listaFilme);

        final ListView listView = (ListView) rootView.findViewById(R.id.search_list);

        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //EDIT VIEW QUE RECEBE O NOME DO FILME
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

                            listaFilme.clear();
                            try{
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
                                        e.printStackTrace();
                                    }
                                    listaFilme.add(teste);
                                }
                                    listView.setAdapter(adapter);



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

        /**
         * Ve se a lupa foi clicada  e eftua a pesquisa.
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                Filme movie = listaFilme.get(position);

                Intent filmeIntent = new Intent(getActivity(), MovieDetails.class);
                filmeIntent.putExtra("id", movie.getidFilme());
                filmeIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(filmeIntent);


            }
        });

        return rootView;
    }




}
