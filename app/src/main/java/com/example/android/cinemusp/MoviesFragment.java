
package com.example.android.cinemusp;


import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * {@link Fragment} that displays a list of number vocabulary words.
 */
public class MoviesFragment extends Fragment {



    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_list, container, false);

        MovieList movies = new MovieList();
        final ArrayList<Movie> list = movies.getMovies();

        // Create a list of movies


        //Button addButton = (Button) rootView.findViewById(R.id.add_button);





            // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.

            MovieAdapter adapter = new MovieAdapter(getActivity(), list);


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
                Movie movie = list.get(position);




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


}
