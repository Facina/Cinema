
package com.example.android.cinemusp.android;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.cinemusp.R;
import com.example.android.cinemusp.modelo.Sessao;


import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Grupo 4 - Turma B POO
 *
 * SubClasse de ArrayAdapter
 * Transforma um arraylist de Sessao em um ExtendedHeightGridView
 */

public class SessaoAdapter extends ArrayAdapter<Sessao> {


    public SessaoAdapter(Context context, ArrayList<Sessao> movies) {

        super(context, 0, movies);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.sessao_item, parent, false);
        }

        Sessao currentMovie = getItem(position);

        LinearLayout fundo = (LinearLayout) listItemView.findViewById(R.id.fundo_grid);
        TextView legendado = (TextView) listItemView.findViewById(R.id.legendado);
        TextView tresD = (TextView) listItemView.findViewById(R.id.tresD);
        TextView horas = (TextView) listItemView.findViewById(R.id.horario_item);
        TextView diaSemana = (TextView)listItemView.findViewById(R.id.dia_da_semana);

        horas.setText(currentMovie.getHorarioString());

        Calendar c = Calendar.getInstance();

        c.setTime(currentMovie.getData());

        int dia = c.get(Calendar.DAY_OF_WEEK);

            switch (dia){
                case 1://Domingo
                    diaSemana.setText("Domingo");
                    break;
                case 2:
                    diaSemana.setText("Segunda-feira");
                    break;
                case 3:
                    diaSemana.setText("Terça-feira");
                    break;
                case 4:
                    diaSemana.setText("Quarta-feira");
                    break;
                case 5:
                    diaSemana.setText("Quinta-feira");
                    break;
                case 6:
                    diaSemana.setText("Sexta-feira");
                    break;

                case 7:
                    diaSemana.setText("Sábado");
                    break;
            }



        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
        TextView legend=new TextView(getContext());
        legend.setLayoutParams(lparams);
        legend.setTextSize(R.dimen.small);
        java.sql.Time horario = new java.sql.Time((new java.util.Date()).getTime());
        java.sql.Date data = new java.sql.Date((new java.util.Date()).getTime());

        if(!currentMovie.isLegendado()){

            legendado.setText("Dublado");

        }else {
            legendado.setText("Legendado");
        }
        if(currentMovie.isTresD()){

            tresD.setText("3D");

        }else {
            tresD.setText("2D");
        }
        if(currentMovie.isLotada() ){
           fundo.setBackgroundResource(R.drawable.my_button);

        }
        if (data.getMonth() > currentMovie.getData().getMonth() || (data.getDate() > currentMovie.getData().getDate() ||
                (data.getDate() == currentMovie.getData().getDate() &&
                horario.getHours() > currentMovie.getHorario().getHours()) ||
                (data.getDate() == currentMovie.getData().getDate() && horario.getHours() == currentMovie.getHorario().getHours()) &&
                        (horario.getMinutes() > currentMovie.getHorario().getMinutes() ))) {
                fundo.setBackgroundResource(R.drawable.my_button);
            }





        return listItemView;
    }
}