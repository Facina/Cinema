package com.example.android.cinemusp.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.android.cinemusp.R;
import com.example.android.cinemusp.modelo.Assento;
import com.example.android.cinemusp.modelo.AssentoObeso;
import com.example.android.cinemusp.modelo.Sala;

import java.util.ArrayList;

public class SalaChooser extends AppCompatActivity {

    ArrayList<Assento> lista_assentos;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_chooser);

        Sala estaSala = new Sala(2,20,200);
        lista_assentos = new ArrayList<>();

        for(int i=0;i<200;i++){
            lista_assentos.add(new AssentoObeso());

        }

        GridView gridList = (GridView) findViewById(R.id.grid_assentos);
        gridList.setNumColumns(20);
        AssentoAdapter adapter = new AssentoAdapter(SalaChooser.this, lista_assentos);

        gridList.setAdapter(adapter);


    }
}
