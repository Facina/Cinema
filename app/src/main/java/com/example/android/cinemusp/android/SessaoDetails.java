package com.example.android.cinemusp.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cinemusp.R;
import com.example.android.cinemusp.modelo.Assento;
import com.example.android.cinemusp.modelo.Preco;
import com.example.android.cinemusp.modelo.Sala;
import com.example.android.cinemusp.modelo.Sessao;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author Grupo 4 - Turma B POO
 *
 * Classe que mostra os detalhes da sessao
 */

public class SessaoDetails extends AppCompatActivity {
    int rowSize = 0;
    int colSize = 0;
    String classificacao;
    Preco preco;
    Sessao atual;
    Sala salaAtual;
    String adress = "https://web-hosting-test.000webhostapp.com/mapa_assentos.php?idsessao=";


    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            super.onBackPressed();
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

    /**
     * Método que mostra os detalhes da sessao de acordo com o banco de dados.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int id = (int) getIntent().getSerializableExtra("idSessao");
        classificacao = (String) getIntent().getSerializableExtra("classificacao");
        adress+=id;
        atual = new Sessao();
        atual.setSala(new Sala());
        salaAtual = atual.getSala();


        final PostResponseAsyncTask task = new PostResponseAsyncTask(this, false, new AsyncResponse() {

            @Override
            public void processFinish(String s) {

                try {
                    JSONArray js = new JSONArray(s);
                    Log.e("jsAray", "size= " + js.length());
                    String bool;

                    JSONObject sessao = null;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


                    for (int i = 0; i < js.length(); i++) {
                        sessao = js.getJSONObject(i);

                        if (i == 0) {

                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                            atual.setData(new java.sql.Date(dateFormat.parse(sessao.getString("data")).getTime()));
                            atual.setHorario(new java.sql.Time(timeFormat.parse(sessao.getString("horario")).getTime()));

                            atual.setHorarioString(sessao.getString("horario"));
                            atual.setIdSessao(sessao.getInt("idSessao"));
                            bool = sessao.getString("imax");
                            if (bool.equals("0")) {
                                atual.setImax(false);
                            } else {
                                atual.setImax(true);
                            }
                            bool = sessao.getString("legendado");
                            if (bool.equals("0")) {
                                atual.setLegendado(false);
                            } else {
                                atual.setLegendado(true);
                            }
                            bool = sessao.getString("tresD");
                            if (bool.equals("0")) {
                                atual.setTresD(false);
                            } else {
                                atual.setTresD(true);
                            }
                            bool = sessao.getString("lotada");
                            if (bool.equals("0")) {
                                atual.setLotada(false);
                            } else {
                                atual.setLotada(true);
                            }
                            bool = sessao.getString("quatroK");
                            if (bool.equals("0")) {
                                atual.setQuatroK(false);
                            } else {
                                atual.setQuatroK(true);
                            }
                            rowSize = sessao.getInt("fileiras");
                            colSize = sessao.getInt("maxAssentos");
                            salaAtual.setIdSala(sessao.getInt("idSala"));
                            salaAtual.setNumeroSala(sessao.getInt("numeroSala"));
                            salaAtual.setNFileiras(sessao.getInt("fileiras"));
                            salaAtual.setMaxAssentos(sessao.getInt("maxAssentos"));
                            preco = new Preco();
                            preco.setPrecoCadeirante((float) sessao.getDouble("precoCadeirante"));
                            preco.setPrecoCasal((float) sessao.getDouble("precoCasal"));
                            preco.setPrecoMovel((float) sessao.getDouble("precoMovel"));
                            preco.setPrecoObeso((float) sessao.getDouble("precoObeso"));
                            preco.setPrecoPadrao((float) sessao.getDouble("precoPadrao"));
                            preco.setPrecoReclinavel((float) sessao.getDouble("precoReclinavel"));
                            preco.setIdPreco(sessao.getInt("idPreco"));
                            salaAtual.setAssentos(new Assento[sessao.getInt("fileiras")][sessao.getInt("maxAssentos")]);
                        }

                        int numAss = sessao.getInt("numeroAssento");
                        int maxAss = sessao.getInt("maxAssentos");
                        int x = numAss / maxAss;
                        int y = numAss % maxAss;

                        salaAtual.setAssento(sessao.getInt("tipoAssento"), x, y);
                        Assento colocarIdAssento = salaAtual.getAssento(x, y);
                        colocarIdAssento.setIdAssento(sessao.getInt("idAssento"));
                        bool = sessao.getString("statusAssento");
                        if (bool.equals("0")) {
                            colocarIdAssento.setStatus(false);
                        } else {
                            colocarIdAssento.setStatus(true);
                        }

                    }
                    setContentView(R.layout.activity_sessao_details);

                    TextView salaHorario = (TextView)findViewById(R.id.sala_horario);
                    TextView salaAudio = (TextView)findViewById(R.id.sala_tipo);
                    TextView salaClassificao = (TextView)findViewById(R.id.sala_classificacao);
                    TextView sala3D = (TextView)findViewById(R.id.sala_3d);
                    TextView salaImax = (TextView)findViewById(R.id.sala_imax);
                    TextView sala4K = (TextView)findViewById(R.id.sala_4k);


                    TextView precoPadrao = (TextView)findViewById(R.id.assento_padrao);
                    TextView precoReclinavel = (TextView)findViewById(R.id.assento_reclinavel);
                    TextView precoCadeirante = (TextView)findViewById(R.id.assento_cadeirante);
                    TextView precoCasal = (TextView)findViewById(R.id.assento_casal);
                    TextView precoObeso = (TextView)findViewById(R.id.assento_obeso);
                    TextView precoMovel = (TextView)findViewById(R.id.assento_movel);


                    salaHorario.setText(atual.getHorarioString());

                    if(atual.isLegendado())
                    salaAudio.setText("Legendado");
                    else  salaAudio.setText("Dublado");

                    salaClassificao.setText(classificacao);

                    if(atual.isTresD())
                    sala3D.setText("sim");
                    else sala3D.setText("não");

                    if(atual.isImax()) salaImax.setText("sim");
                    else salaImax.setText("não");

                    if(atual.isQuatroK()) sala4K.setText("sim");
                    else sala4K.setText("não");
                    Locale Brasil = new Locale("pt", "BR");
                    if(preco.getPrecoPadrao()!=0){
                        precoPadrao.setText(NumberFormat.getCurrencyInstance(Brasil).format(preco.getPrecoPadrao()));;
                    }else precoPadrao.setText("indisponível");
                    if(preco.getPrecoReclinavel()!=0){
                        precoReclinavel.setText(NumberFormat.getCurrencyInstance(Brasil).format(preco.getPrecoReclinavel()));;
                    }else precoReclinavel.setText("indisponível");
                    if(preco.getPrecoCadeirante()!=0){
                        precoCadeirante.setText(NumberFormat.getCurrencyInstance(Brasil).format(preco.getPrecoCadeirante()));;
                    }else precoCadeirante.setText("indisponível");
                    if(preco.getPrecoCasal()!=0){
                        precoCasal.setText(NumberFormat.getCurrencyInstance(Brasil).format(preco.getPrecoCasal()));;
                    }else precoCasal.setText("indisponível");
                    if(preco.getPrecoObeso()!=0){
                        precoObeso.setText(NumberFormat.getCurrencyInstance(Brasil).format(preco.getPrecoObeso()));;
                    }else precoObeso.setText("indisponível");
                    if(preco.getPrecoMovel()!=0){
                        precoMovel.setText(NumberFormat.getCurrencyInstance(Brasil).format(preco.getPrecoMovel()));;
                    }else precoMovel.setText("indisponível");

                    Button prosseguir = (Button) findViewById(R.id.prosseguir);


                    prosseguir.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Intent filmeIntent = new Intent(SessaoDetails.this, SalaChooser.class);
                            filmeIntent.putExtra("idSessao", id);
                            filmeIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);

                            startActivity(filmeIntent);
                            finish();
                        }
                    });


                } catch (Exception e) {
                    Toast.makeText(SessaoDetails.this, "Detalhes não encontrados :(", Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }


            }
        });
        task.execute(adress);


    }
}