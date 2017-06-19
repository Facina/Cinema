package com.example.android.cinemusp.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cinemusp.R;
import com.example.android.cinemusp.modelo.Filme;
import com.example.android.cinemusp.modelo.Preco;
import com.example.android.cinemusp.modelo.Sala;
import com.example.android.cinemusp.modelo.Sessao;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

/**
 * Created by bruno on 6/10/17.
 */

public class MovieDetails extends AppCompatActivity{

    String adress = "https://web-hosting-test.000webhostapp.com/movie_details.php";
    ArrayList<Sessao> lista_sessao = null;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id =(int) getIntent().getSerializableExtra("id");
        adress=adress+"?idfilme="+id;
        Log.e("adress","is "+adress);
        lista_sessao = new ArrayList<Sessao>();

        final Filme currMovie = new Filme();

        PostResponseAsyncTask task = new PostResponseAsyncTask(this, false,new AsyncResponse() {

            @Override
            public void processFinish(String s){

                try{
                    Log.e("getData","parsing");
                    JSONArray js = new JSONArray(s);
                    Log.e("jsAray","size= "+js.length());
                    String bool;
                    JSONObject filme = null;
                    JSONObject sessao = null;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


                    for(int i=0; i  < js.length();i++){

                        if(i==0){

                            filme = js.getJSONObject(0);

                            currMovie.setNome(filme.getString("nomeFilme"));
                            currMovie.setClassificacao(filme.getString("classificacao"));
                            currMovie.setSinopse(filme.getString("sinopse"));
                            currMovie.setImgLink(filme.getString("imgLink"));
                            currMovie.setDuracao(filme.getInt("duracao"));

                            try {
                                currMovie.setDataEstreia(new java.sql.Date(dateFormat.parse(filme.getString("dataEstreia")).getTime()));
                                currMovie.setDataSaida(new java.sql.Date(dateFormat.parse(filme.getString("dataSaida")).getTime()));

                                currMovie.setIdFilme(filme.getInt("idFilme"));
                            } catch (Exception e) {
                                Log.e("Filmefragmetn", "" + filme.getString("nomeFilme"));
                                Log.e("FilmeFragment", "data exception" + filme.getString("dataEstreia") + " asd " + filme.getString("dataSaida"));
                                e.printStackTrace();
                            }

                        }
                        else {
                            Log.e("entrou no else", "ae carai");
                            sessao = js.getJSONObject(i);
                            Sessao atual = new Sessao();
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                            //  SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
                            atual.setData(new java.sql.Date(dateFormat.parse(sessao.getString("data")).getTime()));
                            //  atual.setHorario(String.valueOf(new java.sql.Date(timeFormat.parse(sessao.getString("horario")).getTime())));
                            atual.setHorario(new java.sql.Time(timeFormat.parse(sessao.getString("horario")).getTime()));

                            atual.setHorarioString(sessao.getString("horario"));
                            atual.setIdSessao(sessao.getInt("idSessao"));
                            bool = sessao.getString("imax");
                            if(bool .equals("0")){
                            atual.setImax(false);
                            }else{
                                atual.setImax(true);
                            }
                            bool = sessao.getString("legendado");
                            if(bool.equals("0")){
                                atual.setLegendado(false);
                            }else{
                                atual.setLegendado(true);
                            }
                            bool = sessao.getString("tresD");
                            if(bool.equals("0")){
                                atual.setTresD(false);
                            }else{
                                atual.setTresD(true);
                            }
                            bool = sessao.getString("lotada");
                            if(bool.equals("0")){
                                atual.setLotada(false);
                            }else{
                                atual.setLotada(true);
                            }
                            bool = sessao.getString("quatroK");
                            if(bool.equals("0")){
                                atual.setQuatroK(false);
                            }else{
                                atual.setQuatroK(true);
                            }
                            Sala sala = new Sala();
                            sala.setIdSala(sessao.getInt("idSala"));
                            atual.setSala(sala);
                            Preco preco = new Preco();
                            preco.setIdPreco(sessao.getInt("idPreco"));

                            Log.e("tresD", "= " + atual.isTresD());
                            Log.e("4k", "= " + atual.isQuatroK());
                            Log.e("legendado", "= " + atual.isLegendado());
                            Log.e("lotada", "= " + atual.isLotada());
                            Log.e("imax", "= " + atual.isImax());
                            Log.e("idSessao", "= " + atual.getIdSessao());

                            lista_sessao.add(atual);
                        }
                    }

                        Log.e("getData","parsed");

                    Log.e("size list","who ="+lista_sessao.size());
                    setContentView(R.layout.movie_details);

                    // Find the ImageView in the movie_item.xml layout with the ID image
                    ImageView imageView = (ImageView) findViewById(R.id.movie_image);

                    // Using Picasso interface to display and image from a URL
                    Context context = getApplicationContext();
                    Picasso.with(context).load(currMovie.getImgLink()).into(imageView);

                    // Find the TextView in the movie_item.xml layout with the ID title.
                    TextView titleTextView = (TextView) findViewById(R.id.filme_titulo);
                    // Get the title from the currentMovie object and set this text on
                    // the Title TextView.
                    titleTextView.setText(currMovie.getNome());

                    // Find the TextView in the movie_item.xml layout with the ID title.
                    TextView classificacaoTextView = (TextView) findViewById(R.id.filme_classificacao);
                    // Get the title from the currentMovie object and set this text on
                    // the Title TextView.
                    classificacaoTextView.setText(currMovie.getClassificacao());

                    // Find the TextView in the movie_item.xml layout with the ID duration.
                    TextView durationTextView = (TextView) findViewById(R.id.filme_duracao);
                    // Get the duration from the currentMovie object and set this text on
                    // the duration TextView.
                    durationTextView.setText(""+currMovie.getDuracao()+" min");

                    // Find the TextView in the movie_item.xml layout with the ID rating.
                    TextView dataEstreiaTextView = (TextView) findViewById(R.id.filme_data_estreia);
                    // Get the rating from the currentMovie object and set this text on
                    // the rating TextView.
                    dataEstreiaTextView.setText(""+currMovie.getDataEstreia());

                    // Find the TextView in the movie_item.xml layout with the ID sinopse.
                    TextView dataSaidaTextView = (TextView) findViewById(R.id.filme_data_saida);
                    // Get the sinopse from the currentMovie object and set this text on
                    // the duration TextView.
                    dataSaidaTextView.setText(""+currMovie.getDataSaida());

                    // Find the TextView in the movie_item.xml layout with the ID sinopse.
                    TextView sinopseTextView = (TextView) findViewById(R.id.filme_sinopse);
                    // Get the sinopse from the currentMovie object and set this text on
                    // the duration TextView.
                    sinopseTextView.setText(currMovie.getSinopse());

                    TextView set = (TextView) findViewById(R.id.sessoes);

                    if(lista_sessao.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Não há nenhuma sessão deste filme hoje.",Toast.LENGTH_LONG).show();
                    }else
                    set.setText("Sessões da semana");

                    ExpandableHeightGridView gridList = (ExpandableHeightGridView) findViewById(R.id.sessao_grid);
                    SessaoAdapter adapter = new SessaoAdapter(MovieDetails.this, lista_sessao);

                    gridList.setAdapter(adapter);
                    gridList.setExpanded(true);


                    gridList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Log.e("gridlistclick", "clicked");
                            java.sql.Time horario = new java.sql.Time((new java.util.Date()).getTime());
                            java.sql.Date data = new java.sql.Date((new java.util.Date()).getTime());

                            Sessao sessaoClicada = lista_sessao.get(position);
                            if(!sessaoClicada.isLotada()) {
                                if (data.getDate() > sessaoClicada.getData().getDate() || (data.getDate() == sessaoClicada.getData().getDate() && horario.getHours() > sessaoClicada.getHorario().getHours()) ||
                                        (data.getDate() == sessaoClicada.getData().getDate() && horario.getHours() == sessaoClicada.getHorario().getHours()) &&
                                                (horario.getMinutes() >sessaoClicada.getHorario().getMinutes()  )){
                                    Toast.makeText(MovieDetails.this,"Sessão já passou",Toast.LENGTH_LONG).show();


                                }else{

                                    Intent sessaoIntent = new Intent(MovieDetails.this, SessaoDetails.class);
                                    sessaoIntent.putExtra("idSessao", sessaoClicada.getIdSessao());
                                    sessaoIntent.putExtra("classificacao",currMovie.getClassificacao());
                                    sessaoIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(sessaoIntent);
                                    finish();
                                }

                            }else{
                                Toast.makeText(MovieDetails.this,"Sessão esgotada",Toast.LENGTH_LONG).show();
                            }


                        }
                    });





                }catch (Exception e){
                    Toast.makeText(MovieDetails.this, "Detalhes não encontrados :(", Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }

            }
        });
        task.execute(adress);
        task.setEachExceptionsHandler(new EachExceptionsHandler() {
            @Override
            public void handleIOException(IOException e) {
                Toast.makeText(MovieDetails.this, "Error with internet or web server.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleMalformedURLException(MalformedURLException e) {
                Toast.makeText(MovieDetails.this, "Error with the URL.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleProtocolException(ProtocolException e) {
                Toast.makeText(MovieDetails.this, "Error with protocol.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                Toast.makeText(MovieDetails.this, "Error with text encoding.", Toast.LENGTH_LONG).show();
            }
        });





    }



}
