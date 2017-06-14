package com.example.android.cinemusp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.type;
import static java.security.AccessController.getContext;

/**
 * Created by bruno on 6/10/17.
 */

public class MovieDetails extends AppCompatActivity{

    String adress = "https://web-hosting-test.000webhostapp.com/login.php";
    InputStream data = null;
    String line = null;
    String result = null;
    ArrayList<Filme> listaFilme = new ArrayList<Filme>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        int position =(int) getIntent().getSerializableExtra("position");

        /*
        String nome = (String) getIntent().getSerializableExtra("nome");

        String image = (String) getIntent().getSerializableExtra("img");
        String ratingNote = (String) getIntent().getSerializableExtra("rating");
        String duration = (String) getIntent().getSerializableExtra("duration");
        String sinopse = (String) getIntent().getSerializableExtra("sinopse");
        String director = (String) getIntent().getSerializableExtra("director");
        String main = (String) getIntent().getSerializableExtra("main");
        String trailer = (String) getIntent().getSerializableExtra("trailer");
        String release = (String) getIntent().getSerializableExtra("release");
        String type = (String) getIntent().getSerializableExtra("type");
        */
        /*
        Log.e("nas", nome);
        Log.e("asd", image);
        Log.e("asd", ratingNote);
        Log.e("asd", duration);
        Log.e("asd", sinopse);
        Log.e("asd", director);
        Log.e("asd", main);
        Log.e("asd", trailer);
        Log.e("asd", release);

        */

        MovieList movies = new MovieList();
        final ArrayList<Movie> list = movies.getMovies();

        Movie currentMovie = list.get(position);




        // Find the ImageView in the movie_item.xml layout with the ID image
        ImageView imageView = (ImageView) findViewById(R.id.movie_image);

        // Using Picasso interface to display and image from a URL
        Context context = this.getApplicationContext();
        Picasso.with(context).load(currentMovie.getImgLink()).into(imageView);

        // Find the TextView in the movie_item.xml layout with the ID title.
        TextView titleTextView = (TextView) findViewById(R.id.movie_title);
        // Get the title from the currentMovie object and set this text on
        // the Title TextView.
        titleTextView.setText(currentMovie.getName());

        // Find the TextView in the movie_item.xml layout with the ID title.
        TextView typeTextView = (TextView) findViewById(R.id.movie_type);
        // Get the title from the currentMovie object and set this text on
        // the Title TextView.
        typeTextView.setText(currentMovie.getmType());

        // Find the TextView in the movie_item.xml layout with the ID duration.
        TextView durationTextView = (TextView) findViewById(R.id.movie_duration);
        // Get the duration from the currentMovie object and set this text on
        // the duration TextView.
        durationTextView.setText(currentMovie.getDuration());

        // Find the TextView in the movie_item.xml layout with the ID rating.
        TextView ratingTextView = (TextView) findViewById(R.id.movie_rating);
        // Get the rating from the currentMovie object and set this text on
        // the rating TextView.
        ratingTextView.setText(currentMovie.getRating());

        // Find the TextView in the movie_item.xml layout with the ID sinopse.
        TextView sinopseTextView = (TextView) findViewById(R.id.movie_sinopse);
        // Get the sinopse from the currentMovie object and set this text on
        // the duration TextView.
        sinopseTextView.setText(currentMovie.getSinopse());


        // Find the TextView in the movie_item.xml layout with the ID release.
        TextView releaseTextView = (TextView) findViewById(R.id.movie_release);
        // Get the release from the currentMovie object and set this text on
        // the duration TextView.
        releaseTextView.setText(currentMovie.getmReleaseDate());

        // Find the TextView in the movie_item.xml layout with the ID main.
        TextView actorTextView = (TextView) findViewById(R.id.movie_main);
        // Get the main actor from the currentMovie object and set this text on
        // the duration TextView.
        actorTextView.setText(currentMovie.getmMainActor());

        // Find the TextView in the movie_item.xml layout with the ID director.
        TextView directorTextView = (TextView) findViewById(R.id.movie_director);
        // Get the director from the currentMovie object and set this text on
        // the duration TextView.
        directorTextView.setText(currentMovie.getmDirector());

        // Find the TextView in the movie_item.xml layout with the ID trailer.
        TextView trailerTextView = (TextView) findViewById(R.id.movie_trailer);
        // Get the trailer from the currentMovie object and set this text on
        // the duration TextView.
        trailerTextView.setText(currentMovie.getMtrailer());




    }


    public void getData(){
        try {
            URL url = new URL(adress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            data = new BufferedInputStream(conn.getInputStream());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(data));
            StringBuilder sb = new StringBuilder();
            while((line = br.readLine()) != null){
                sb.append(line+"\n");

            }
            data.close();
            result=sb.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //Parsin json data

        try{

            JSONArray  js = new JSONArray(result);
            JSONObject filme = null;

            for(int i=0; i  < js.length();i++){
                filme = js.getJSONObject(i);
                Filme teste = new Filme();
                teste.setNome(filme.getString("nomeFilme"));
                teste.setClassificacao(filme.getString("classificacao"));
                teste.setSinopse(filme.getString("sinopse"));
                teste.setImgLink(filme.getString("imgLink"));
                teste.setDuracao(filme.getInt("duracao"));



            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }





    public static Object deserialize(String fileName) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }
}
