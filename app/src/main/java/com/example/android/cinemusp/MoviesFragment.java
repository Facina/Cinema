
package com.example.android.cinemusp;


import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.media.CamcorderProfile.get;


/**
 * {@link Fragment} that displays a list of number vocabulary words.
 */
public class MoviesFragment extends Fragment {


    String adress = "https://web-hosting-test.000webhostapp.com/login.php";
    InputStream data = null;
    String line = null;
    String result = null;
    ArrayList<Filme> listaFilme = new ArrayList<Filme>();

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_list, container, false);

       // MovieList movies = new MovieList();
       // final ArrayList<Movie> list = movies.getMovies();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        // Create a list of movies
            getData();

            Log.e("teste", "" +listaFilme.size());

        //Button addButton = (Button) rootView.findViewById(R.id.add_button);





            // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.

           // MovieAdapter adapter = new MovieAdapter(getActivity(), list);
             FilmeAdapter adapter = new FilmeAdapter(getActivity(), listaFilme);

            // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
            // There should be a {@link ListView} with the view ID called list, which is declared in the
            // movie_list.xml layout file.
            ListView listView = (ListView) rootView.findViewById(R.id.list);

            // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
            // {@link ListView} will display list items for each {@link Word} in the list.
            listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                // Get the {@link Word} object at the given position the user clicked on
                Filme movie = listaFilme.get(position);




                Intent movieIntent = new Intent(getActivity(), MovieDetails.class);
                movieIntent.putExtra("position",position);
                /*movieIntent.putExtra("nome", movie.getName());
                movieIntent.putExtra("sinopse", movie.getSinopse());
                movieIntent.putExtra("img", movie.getImgLink());

                movieIntent.putExtra("rating", movie.getRating());

                movieIntent.putExtra("release", movie.getmReleaseDate());
                movieIntent.putExtra("duration", movie.getDuration());
                movieIntent.putExtra("director", movie.getmDirector());
                movieIntent.putExtra("main", movie.getmMainActor());
                movieIntent.putExtra("type", movie.getmType());

                movieIntent.putExtra("trailer", movie.getMtrailer());
                */



                // Start the new activity
                startActivity(movieIntent);

            }
        });



        return rootView;
    }



    public void getData(){
        try {
            Log.e("getData","connecting");
            URL url = new URL(adress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            Log.e("getData","finished connecting");

            data = new BufferedInputStream(conn.getInputStream());
            Log.e("getData","finished connecting");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{
            Log.e("getData","buffering");
            BufferedReader br = new BufferedReader(new InputStreamReader(data));
            StringBuilder sb = new StringBuilder();
            while((line = br.readLine()) != null){
                sb.append(line+"\n");

            }
            data.close();
            Log.e("getData","creating result");
            result=sb.toString();
            Log.e("getData","read");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //Parsin json data

        try{
            Log.e("getData","parsing");
            JSONArray js = new JSONArray(result);
            JSONObject filme = null;

            for(int i=0; i  < js.length();i++){
                filme = js.getJSONObject(i);
                Filme teste = new Filme();
                teste.setNome(filme.getString("nomeFilme"));
                teste.setClassificacao(filme.getString("classificacao"));
                teste.setSinopse(filme.getString("sinopse"));
                teste.setImgLink(filme.getString("imgLink"));
                teste.setDuracao(filme.getInt("duracao"));
                Log.e("Fetching","oi+"+ filme.getString("nomeFilme"));

                listaFilme.add(teste);


                Log.e("getData","parsed");
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }



}
