
package com.example.android.cinemusp.android;
import java.text.SimpleDateFormat;

import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.ArrayList;


/**
 * Classe de fragmento que mostra os filmes em cartaz utilizando uma listView
 * @author Grupo 4 - Turma B POO
 */
public class FilmeFragment extends Fragment {

    View rootView;
    String adress = "https://web-hosting-test.000webhostapp.com/conn_cartaz.php";


    /**
     * Método padrão da classe fragment permite saber de qual fragmento se trata
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Método padrao da classe fragment
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("not null",1);
    }

    /**
     * Método que cria o fragmento inflando o arquivo xml e executando-o e permiter a reciclagem de fragmentos.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Filme movie = new Filme();
        rootView = inflater.inflate(R.layout.movie_list, container, false);

        final ArrayList<Filme> listaFilme = new ArrayList<Filme>();
        final FilmeAdapter adapter = new FilmeAdapter(getActivity(), listaFilme);
        final ListView listView = (ListView) rootView.findViewById(R.id.list);


        listView.setAdapter(adapter);

        /**
         * Método de conexao assincrona com o banco de dados importado da biblioteca de KosalGeek
         */
        PostResponseAsyncTask task = new PostResponseAsyncTask(getActivity(), false, new AsyncResponse() {

            @Override
            public void processFinish(String s) {

                try {
                    JSONArray js = new JSONArray(s);
                    JSONObject filme = null;

                    for (int i = 0; i < js.length(); i++) {
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


                } catch (Exception e) {
                    Toast.makeText(getContext(), "Nenhum filme foi encontrado :(", Toast.LENGTH_LONG).show();
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

        /**
         * Método de escuta se algum filme da lista foi clicado.
         */
        final ArrayList<Filme> finalListaFilme = listaFilme;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                Filme movie = finalListaFilme.get(position);


                Intent filmeIntent = new Intent(getActivity(), MovieDetails.class);
                filmeIntent.putExtra("id", movie.getidFilme());
                filmeIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(filmeIntent);

            }
        });


        return rootView;
    }



}