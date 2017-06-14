/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.cinemusp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * {@link MovieAdapter} is an {@link ArrayAdapter} that can provide the layout for each list item
 * based on a data source, which is a list of {@link Movie} objects.
 */
public class FilmeAdapter extends ArrayAdapter<Filme> {



    /**
     * Create a new {@link MovieAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param movies is the list of {@link Movie}s to be displayed.
     *
     */
    public FilmeAdapter(Context context, ArrayList<Filme> movies) {
        super(context, 0, movies);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Filme currentMovie = getItem(position);



        // Find the ImageView in the movie_item.xml layout with the ID image
        ImageView image = (ImageView) listItemView.findViewById(R.id.image);

        // Using Picasso interface to display and image from a URL
        Context context = listItemView.getContext();
        Picasso.with(context).load(currentMovie.getImgLink()).into(image);

        // Find the TextView in the movie_item.xml layout with the ID title.
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        // Get the title from the currentMovie object and set this text on
        // the Title TextView.
        titleTextView.setText(currentMovie.getNome());

        // Find the TextView in the movie_item.xml layout with the ID duration.
        TextView durationTextView = (TextView) listItemView.findViewById(R.id.duration);
        // Get the duration from the currentMovie object and set this text on
        // the duration TextView.
        durationTextView.setText(""+currentMovie.getDuracao());

        // Find the TextView in the movie_item.xml layout with the ID duration.
        TextView ratingTextView = (TextView) listItemView.findViewById(R.id.rating);
        // Get the rating from the currentMovie object and set this text on
        // the rating TextView.
        ratingTextView.setText(currentMovie.getClassificacao());

        // Find the TextView in the movie_item.xml layout with the ID duration.
        TextView sinopseTextView = (TextView) listItemView.findViewById(R.id.text);
        // Get the duration from the currentMovie object and set this text on
        // the duration TextView.
        sinopseTextView.setText(currentMovie.getSinopse());



        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}