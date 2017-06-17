
package com.example.android.cinemusp.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.cinemusp.R;
import com.example.android.cinemusp.modelo.Assento;
import com.example.android.cinemusp.modelo.Filme;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AssentoAdapter extends ArrayAdapter<Assento> {



    public AssentoAdapter(Context context, ArrayList<Assento> movies) {

        super(context, 0, movies);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.assento_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Assento currentMovie = getItem(position);

        ImageView cadeira = (ImageView) listItemView.findViewById(R.id.assento);

        cadeira.setBackgroundResource(R.drawable.assento_padrao_cinza);




        // the ListView.
        return listItemView;
    }
}