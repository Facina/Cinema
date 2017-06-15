package com.example.android.cinemusp;

import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.R.attr.type;
import static java.security.AccessController.getContext;

/**
 * Created by bruno on 6/10/17.
 */

public class MovieDetails extends AppCompatActivity{

    String adress = "https://web-hosting-test.000webhostapp.com/pesquisa_filme_id.php";

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            super.onBackPressed(); //replaced
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int position =(int) getIntent().getSerializableExtra("id");
        adress=adress+"?idfilme="+position;
        Log.e("adress","is "+adress);

        final Filme currMovie = new Filme();

        PostResponseAsyncTask task = new PostResponseAsyncTask(this, false,new AsyncResponse() {

            @Override
            public void processFinish(String s){

                try{
                    Log.e("getData","parsing");
                    JSONArray js = new JSONArray(s);
                    JSONObject filme = null;

                    for(int i=0; i  < js.length();i++) {
                        filme = js.getJSONObject(i);
                        Toast.makeText(MovieDetails.this, "" + 0, Toast.LENGTH_LONG);

                        currMovie.setNome(filme.getString("nomeFilme"));
                        currMovie.setClassificacao(filme.getString("classificacao"));
                        currMovie.setSinopse(filme.getString("sinopse"));
                        currMovie.setImgLink(filme.getString("imgLink"));
                        currMovie.setDuracao(filme.getInt("duracao"));
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        try {
                            currMovie.setDataEstreia(new java.sql.Date(dateFormat.parse(filme.getString("dataEstreia")).getTime()));
                            currMovie.setDataSaida(new java.sql.Date(dateFormat.parse(filme.getString("dataSaida")).getTime()));

                            currMovie.setIdFilme(filme.getInt("idFilme"));
                        } catch (Exception e) {
                            Log.e("Filmefragmetn", "" + filme.getString("nomeFilme"));
                            Log.e("FilmeFragment", "data exception" + filme.getString("dataEstreia") + " asd " + filme.getString("dataSaida"));
                            e.printStackTrace();
                        }

                    }

                    Log.e("getData","parsed");

                    setContentView(R.layout.movie_details);

                    // Find the ImageView in the movie_item.xml layout with the ID image
                    ImageView imageView = (ImageView) findViewById(R.id.movie_image);

                    // Using Picasso interface to display and image from a URL
                    Context context = getApplicationContext();
                    Picasso.with(context).load(currMovie.getImgLink()).into(imageView);

                    // Find the TextView in the movie_item.xml layout with the ID title.
                    TextView titleTextView = (TextView) findViewById(R.id.filme_titulo);
                    // Get the title from the currentMovie object and set this text on
                    // the Title TextView.
                    titleTextView.setText(currMovie.getNome());

                    // Find the TextView in the movie_item.xml layout with the ID title.
                    TextView classificacaoTextView = (TextView) findViewById(R.id.filme_classificacao);
                    // Get the title from the currentMovie object and set this text on
                    // the Title TextView.
                    classificacaoTextView.setText(currMovie.getClassificacao());

                    // Find the TextView in the movie_item.xml layout with the ID duration.
                    TextView durationTextView = (TextView) findViewById(R.id.filme_duracao);
                    // Get the duration from the currentMovie object and set this text on
                    // the duration TextView.
                    durationTextView.setText(""+currMovie.getDuracao()+" min");

                    // Find the TextView in the movie_item.xml layout with the ID rating.
                    TextView dataEstreiaTextView = (TextView) findViewById(R.id.filme_data_estreia);
                    // Get the rating from the currentMovie object and set this text on
                    // the rating TextView.
                    dataEstreiaTextView.setText(""+currMovie.getDataEstreia());

                    // Find the TextView in the movie_item.xml layout with the ID sinopse.
                    TextView dataSaidaTextView = (TextView) findViewById(R.id.filme_data_saida);
                    // Get the sinopse from the currentMovie object and set this text on
                    // the duration TextView.
                    dataSaidaTextView.setText(""+currMovie.getDataSaida());

                    // Find the TextView in the movie_item.xml layout with the ID sinopse.
                    TextView sinopseTextView = (TextView) findViewById(R.id.filme_sinopse);
                    // Get the sinopse from the currentMovie object and set this text on
                    // the duration TextView.
                    sinopseTextView.setText(currMovie.getSinopse());






                }catch (Exception e){
                    Toast.makeText(MovieDetails.this, "Detalhes não encontrados :(", Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }

            }
        });
        task.execute(adress);
        task.setEachExceptionsHandler(new EachExceptionsHandler() {
            @Override
            public void handleIOException(IOException e) {
                Toast.makeText(MovieDetails.this, "Error with internet or web server.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleMalformedURLException(MalformedURLException e) {
                Toast.makeText(MovieDetails.this, "Error with the URL.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleProtocolException(ProtocolException e) {
                Toast.makeText(MovieDetails.this, "Error with protocol.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                Toast.makeText(MovieDetails.this, "Error with text encoding.", Toast.LENGTH_LONG).show();
            }
        });



    }
}
