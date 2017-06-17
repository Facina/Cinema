package com.example.android.cinemusp.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cinemusp.R;
import com.example.android.cinemusp.modelo.Filme;

public class SessaoDetails extends AppCompatActivity {

    int numMeias=0;

    int numIngresso=1;

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
        setContentView(R.layout.activity_sessao_details);


        Button numIcr = (Button) findViewById(R.id.numero_increment);
        Button numDec = (Button) findViewById(R.id.numero_decrement);
        Button meiaIcr = (Button) findViewById(R.id.meia_icrement);
        Button meiaDec = (Button) findViewById(R.id.meia_decrement);
        Button prosseguir = (Button) findViewById(R.id.prosseguir);


        final TextView numIngr = (TextView) findViewById(R.id.numero);
        final TextView numMeia = (TextView) findViewById(R.id.meias);



        numIngr.setText(""+numIngresso);
        numMeia.setText(""+numMeias);

        precoRefresh();

        prosseguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent filmeIntent = new Intent(SessaoDetails.this, SalaChooser.class);


                startActivity(filmeIntent);

            }
        });

        numIcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numIngresso++;
                precoRefresh();
                numIngr.setText(""+numIngresso);

            }
        });

        numDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numIngresso>1){
                    numIngresso--;
                    precoRefresh();
                    numIngr.setText(""+numIngresso);


                }
                else {
                    Toast.makeText(SessaoDetails.this,"Não pode comprar menos de um ingresso",Toast.LENGTH_LONG).show();
                }
            }
        });

        meiaIcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numMeias<numIngresso){
                    numMeias++;
                    precoRefresh();
                    numMeia.setText(""+numMeias);

                }else{
                    Toast.makeText(SessaoDetails.this,"Número de meias não pode exceder o número de ingresso",Toast.LENGTH_LONG).show();

                }
            }
        });

        meiaDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numMeias>=1){
                    numMeias--;
                    precoRefresh();
                    numMeia.setText(""+numMeias);

                }
            }
        });

    }


    private void precoRefresh(){
        TextView preco = (TextView)findViewById(R.id.sessao_preco);
        int total = (numIngresso-numMeias)*10 + numMeias*5;
        preco.setText(""+total);

    }
}
