
package com.example.android.cinemusp.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.cinemusp.R;
import com.example.android.cinemusp.modelo.Filme;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Classe que mostra a lista de filme na tela de acordo com um ArrayList<Filme>
 * @author Grupo 4 - Turma B POO
 */
public class FilmeAdapter extends ArrayAdapter<Filme> {

    /**
     * Construtor
     * @param context
     * @param movies
     */
    public FilmeAdapter(Context context, ArrayList<Filme> movies) {

            super(context, 0, movies);

    }

    /**
     * Cria o view da lista com todos seus filhos.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
        }

        Filme currentMovie = getItem(position);



        ImageView image = (ImageView) listItemView.findViewById(R.id.image);

        Context context = listItemView.getContext();
        Picasso.with(context).load(currentMovie.getImgLink()).into(image);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        titleTextView.setText(currentMovie.getNome());

        TextView durationTextView = (TextView) listItemView.findViewById(R.id.duration);
        durationTextView.setText(""+currentMovie.getDuracao()+"min");

        TextView ratingTextView = (TextView) listItemView.findViewById(R.id.rating);
        ratingTextView.setText(currentMovie.getClassificacao());

        TextView sinopseTextView = (TextView) listItemView.findViewById(R.id.text);
        sinopseTextView.setText(currentMovie.getSinopse());



        return listItemView;
    }
}