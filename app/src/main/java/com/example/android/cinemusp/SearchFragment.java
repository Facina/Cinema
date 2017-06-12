package com.example.android.cinemusp;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.onClick;
import static com.example.android.cinemusp.R.mipmap.search;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.search_list, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        ImageView button = (ImageView) rootView.findViewById(R.id.glass);
        final EditText editText = (EditText) rootView.findViewById(R.id.seach_edit);
        editText.clearFocus();
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        InputMethodManager inputManager = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(rootView.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("entrou", "mas eae");
                final String stringRead ;
               stringRead = editText.getText().toString();

                MovieList movies = new MovieList();
                final ArrayList<Movie> list = movies.getMovies();
                final ArrayList<Movie> found = new ArrayList<Movie>();


                for(Movie curr : list){
                    Log.e("SearchFragment","sera q acha?");

                    if(stringRead.matches("-all") || curr.getName() != null && curr.getName().toLowerCase().contains(stringRead.toLowerCase()) && !stringRead.matches("")){
                        found.add(curr);
                        Log.v("SearchFragment","achou");

                    }


                }

                // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
                // adapter knows how to create list items for each item in the list.

                MovieAdapter adapter = new MovieAdapter(getActivity(), found);


                // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
                // There should be a {@link ListView} with the view ID called list, which is declared in the
                // movie_list.xml layout file.
                ListView listView = (ListView) getActivity().findViewById(R.id.search_list);

                // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
                // {@link ListView} will display list items for each {@link Word} in the list.
                listView.setAdapter(adapter);



            }
        });


        return rootView;
    }

}
