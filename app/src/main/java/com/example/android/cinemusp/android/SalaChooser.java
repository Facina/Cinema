package com.example.android.cinemusp.android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cinemusp.R;
import com.example.android.cinemusp.modelo.Assento;
import com.example.android.cinemusp.modelo.AssentoObeso;
import com.example.android.cinemusp.modelo.Ingresso;
import com.example.android.cinemusp.modelo.Preco;
import com.example.android.cinemusp.modelo.Sala;
import com.example.android.cinemusp.modelo.Sessao;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static android.R.attr.x;
import static android.R.attr.y;

public class SalaChooser extends AppCompatActivity implements View.OnClickListener {
    int rowSize=0;
    int colSize=0;
    Preco preco ;
    Sessao atual;
    Sala salaAtual;
    boolean[][] isMeia;
    boolean[][] mapaIsChecked;
    float precoTotal = 0;
    String adress = "https://web-hosting-test.000webhostapp.com/mapa_assentos.php?idsessao=";
    String updateAdress = "https://web-hosting-test.000webhostapp.com/update.php";

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
        //setContentView(R.layout.activity_sala_chooser);

        final int id =(int) getIntent().getSerializableExtra("idSessao");


        atual = new Sessao();
        atual.setSala(new Sala());
        salaAtual = atual.getSala();


        adress=adress+id;
        Log.e("adress","is "+adress);


        PostResponseAsyncTask task = new PostResponseAsyncTask(this, false,new AsyncResponse() {

            @Override
            public void processFinish(String s){

                try{
                    Log.e("getData","parsing");
                    JSONArray js = new JSONArray(s);
                    Log.e("jsAray","size= "+js.length());
                    String bool;

                    JSONObject sessao = null;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


                    for(int i=0; i  < js.length();i++){
                        sessao = js.getJSONObject(i);

                            if(i==0) {
                                Log.e("entrou no else", "ae carai");

                                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                                //  SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
                                atual.setData(new java.sql.Date(dateFormat.parse(sessao.getString("data")).getTime()));
                                //  atual.setHorario(String.valueOf(new java.sql.Date(timeFormat.parse(sessao.getString("horario")).getTime())));
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
                                rowSize=sessao.getInt("fileiras");
                                colSize=sessao.getInt("maxAssentos");
                                salaAtual.setIdSala(sessao.getInt("idSala"));
                                salaAtual.setNumeroSala(sessao.getInt("numeroSala"));
                                salaAtual.setNFileiras(sessao.getInt("fileiras"));
                                salaAtual.setMaxAssentos(sessao.getInt("maxAssentos"));
                                preco = new Preco();
                                preco.setPrecoCadeirante((float)sessao.getDouble("precoCadeirante"));
                                preco.setPrecoCasal((float)sessao.getDouble("precoCasal"));
                                preco.setPrecoMovel((float)sessao.getDouble("precoMovel"));
                                preco.setPrecoObeso((float)sessao.getDouble("precoObeso"));
                                preco.setPrecoPadrao((float)sessao.getDouble("precoPadrao"));
                                preco.setPrecoReclinavel((float)sessao.getDouble("precoReclinavel"));
                                preco.setIdPreco(sessao.getInt("idPreco"));
                                salaAtual.setAssentos(new Assento[sessao.getInt("fileiras")][sessao.getInt("maxAssentos")]);
                            }

                            int numAss = sessao.getInt("numeroAssento");
                            int maxAss = sessao.getInt("maxAssentos");
                            int x = numAss /maxAss;
                            int y = numAss %maxAss;

                            salaAtual.setAssento(sessao.getInt("tipoAssento"), x,y);
                            Assento colocarIdAssento = salaAtual.getAssento(x,y);
                            colocarIdAssento.setIdAssento(sessao.getInt("idAssento"));
                            colocarIdAssento.setIdAssentoSessao(sessao.getInt("idAssentoSessao"));
                            bool = sessao.getString("statusAssento");
                             if (bool.equals("0")) {
                                 colocarIdAssento.setStatus(false);
                             } else {
                                 colocarIdAssento.setStatus(true);
                             }


                            Log.e("numero do assento", " x = " + x+ " y = "+y+ " numAss= "+numAss+ " maxAss = "+maxAss );
                          // Log.e("4k", "= " + atual.isQuatroK());
                            //Log.e("legendado", "= " + atual.isLegendado());
                           // Log.e("lotada", "= " + atual.isLotada());
                            //Log.e("imax", "= " + atual.isImax());
                            //Log.e("idSessao", "= " + atual.getIdSessao());


                    }

                    Log.e("getData","parsed");

                    setContentView(R.layout.activity_sala_chooser);


                    TableLayout gridList = (TableLayout) findViewById(R.id.grid_assentos);
                    View image;
                    Assento insercaoAssento;
                    TableRow row;
                    boolean esqObeso = false;
                    boolean esqCasal = false;
                    TextView numSala = (TextView) findViewById(R.id.sala_num);
                    numSala.setText("Sala "+salaAtual.getNumeroSala());
                    mapaIsChecked = new boolean[salaAtual.getNFileiras()][salaAtual.getMaxAssentos()];
                    isMeia = new boolean[salaAtual.getNFileiras()][salaAtual.getMaxAssentos()];
                    int k=0;
                    // Tabela com todos as imagens;
                    for(int i=0;i<rowSize;i++){
                        row = new TableRow(getApplicationContext());

                        for(int j=0;j<salaAtual.getMaxAssentos();j++) {
                            Log.e("entao bora ver","i= "+i+" j = "+j);
                            mapaIsChecked[i][j]=false;
                            isMeia[i][j]=false;
                            image = getLayoutInflater().inflate(R.layout.assento_item, null);


                                 insercaoAssento = salaAtual.getAssento(i,j);

                            if(insercaoAssento == null) {
                                image.setBackgroundResource(R.color.transparent);
                                image.setTag(k++);
                            }else{

                                switch (insercaoAssento.getTipo()){
                                    case 1://PADRAO
                                        if(insercaoAssento.getStatus()){//vermelho
                                            image.setBackgroundResource(R.drawable.assento_padrao_vermelho);
                                            image.setTag(k++);

                                        }else{//verde
                                            image.setBackgroundResource(R.drawable.assento_padrao_verde);
                                            image.setTag(k++);
                                            image.setOnClickListener(SalaChooser.this);

                                        }
                                    break;
                                    case 2://reclinavel
                                        if(insercaoAssento.getStatus()){//vermelho
                                            image.setBackgroundResource(R.drawable.assento_reclinavel_vermelho);
                                            image.setTag(k++);


                                        }else{//verde
                                            image.setBackgroundResource(R.drawable.assento_reclinavel_verde);
                                            image.setTag(k++);
                                            image.setOnClickListener(SalaChooser.this);

                                        }
                                        break;
                                    case 3://cadeirante
                                        if(insercaoAssento.getStatus()){//vermelho
                                            image.setBackgroundResource(R.drawable.assento_cadeirante_vermelho);
                                            image.setTag(k++);

                                        }else{//verde
                                            image.setBackgroundResource(R.drawable.assento_cadeirante_verde);
                                            image.setTag(k++);
                                            image.setOnClickListener(SalaChooser.this);

                                        }
                                        break;
                                    case 4://casal
                                        if(insercaoAssento.getStatus()){//vermelho
                                            image.setBackgroundResource(R.drawable.assento_casal_vermelho_esquerdo);
                                            image.setTag(k++);

                                            row.addView(image);
                                            j++;
                                            mapaIsChecked[i][j]=false;
                                            isMeia[i][j]=false;

                                            image = getLayoutInflater().inflate(R.layout.assento_item, null);
                                            image.setBackgroundResource(R.drawable.assento_casal_vermelho_direita);
                                            image.setTag(k++);


                                        }else{//verde
                                            image.setBackgroundResource(R.drawable.assento_casal_verde_esquerda);
                                            image.setTag(k++);
                                            image.setOnClickListener(SalaChooser.this);

                                            row.addView(image);
                                            j++;
                                            mapaIsChecked[i][j]=false;
                                            isMeia[i][j]=false;

                                            image = getLayoutInflater().inflate(R.layout.assento_item, null);
                                            image.setBackgroundResource(R.drawable.assento_casal_verde_direita);
                                            image.setTag(k++);
                                            image.setOnClickListener(SalaChooser.this);

                                        }
                                        break;
                                    case 5://obeso
                                        if(insercaoAssento.getStatus()){//vermelho
                                            image.setBackgroundResource(R.drawable.assento_obeso_vermelho_esquerda);
                                            image.setTag(k++);

                                            row.addView(image);
                                            j++;
                                            mapaIsChecked[i][j]=false;
                                            isMeia[i][j]=false;

                                            image = getLayoutInflater().inflate(R.layout.assento_item, null);
                                            image.setBackgroundResource(R.drawable.assento_obeso_vermelho_direita);
                                            image.setTag(k++);



                                        }else{//verde
                                            image.setBackgroundResource(R.drawable.assento_obeso_verde_esquerdo);
                                            image.setTag(k++);
                                            image.setOnClickListener(SalaChooser.this);
                                            row.addView(image);
                                            j++;
                                            mapaIsChecked[i][j]=false;
                                            isMeia[i][j]=false;

                                            image = getLayoutInflater().inflate(R.layout.assento_item, null);
                                            image.setBackgroundResource(R.drawable.assento_obeso_verde_direito);
                                            image.setTag(k++);
                                            image.setOnClickListener(SalaChooser.this);


                                        }
                                        break;

                                    case 6://movel
                                        if(insercaoAssento.getStatus()){//vermelho
                                            image.setBackgroundResource(R.drawable.assento_movel_vermelho);
                                            image.setTag(k++);


                                        }else{//verde
                                            image.setBackgroundResource(R.drawable.assento_movel_verde);
                                            image.setTag(k++);
                                            image.setOnClickListener(SalaChooser.this);

                                        }
                                        break;
                                }


                            }



                            row.addView(image);
                        }
                        gridList.addView(row);

                    }
                    final ArrayList<Ingresso> ingressosComprados = new ArrayList<>();
                    Button compra = (Button) findViewById(R.id.comprar);

                    compra.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            ingressosComprados.clear();
                            for (int i = 0; i < rowSize; i++) {
                                for (int j = 0; j < colSize; j++) {
                                    if (mapaIsChecked[i][j]) {//Vai comprar esse

                                        ingressosComprados.add(new Ingresso(salaAtual.getAssento(i, j), preco, isMeia[i][j],i,j));

                                    }
                                }
                            }
                            Log.e("quantida", "igual = " + ingressosComprados.size());
                            precoTotal = 0;
                            for (Ingresso umIngresso : ingressosComprados) {
                                precoTotal += umIngresso.getPreco2();
                                Log.e("idAssento=","= "+umIngresso.getAssento().getIdAssento()+ " true");

                            }


                            DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            final int[] tot = {0};
                                            for(int i=0;i<ingressosComprados.size();i++) {
                                                HashMap postdata = new HashMap();


                                                postdata.put("idAssento", "" + ingressosComprados.get(i).getAssento().getIdAssento());
                                                postdata.put("mobile", "android");
                                                postdata.put("idSessao", "" + atual.getIdSessao());
                                                postdata.put("idAssentoSessao", "" + ingressosComprados.get(i).getAssento().getIdAssentoSessao());
                                                postdata.put("valor", "" + ingressosComprados.get(i).getPreco2());
                                                if (isMeia[ingressosComprados.get(i).getX()][ingressosComprados.get(i).getY()])
                                                    postdata.put("meia", "1");
                                                else postdata.put("meia", "0");


                                                PostResponseAsyncTask updateTask = new PostResponseAsyncTask(SalaChooser.this, postdata, new AsyncResponse() {
                                                    @Override
                                                    public void processFinish(String s) {
                                                        Log.e("update", "sera?" + s);
                                                        tot[0]++;
                                                        Toast.makeText(SalaChooser.this, s, Toast.LENGTH_SHORT).show();
                                                        if(tot[0] ==ingressosComprados.size())finish();
                                                    }

                                                });

                                                updateTask.setLoadingMessage("Efetuando a compra do(s) ingresso(s)");
                                                updateTask.execute(updateAdress);


                                            }

                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:

                                            break;
                                    }
                                }

                            };
                            Locale Brasil = new Locale("pt", "BR");
                            String valorTotal = NumberFormat.getCurrencyInstance(Brasil).format(precoTotal);
                            if (ingressosComprados.size() == 0) {
                                Toast.makeText(SalaChooser.this,"Nenhum assento seleciona",Toast.LENGTH_LONG).show();
                            }
                            else {
                                AlertDialog.Builder meiaConfirm = new AlertDialog.Builder(v.getContext());
                                meiaConfirm.setMessage("Total de : " + valorTotal + "\nDeseja efetuar a compra?")
                                        .setPositiveButton("Comprar", dialog).setNegativeButton("Cancelar", dialog).show();

                            }



                        }
                    });




                }catch (Exception e){
                    Toast.makeText(SalaChooser.this, "Detalhes não encontrados :(", Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }

            }
        });
        task.execute(adress);



    }

    @Override
    public void onClick(View v) {

        int clickedID = (int)  v.getTag();
        final int x =clickedID/colSize;
        final int y =clickedID%colSize;
        Log.e("x do role =" +x, "y do role  ="+y);
        TableLayout tabela = (TableLayout) findViewById(R.id.grid_assentos);
        TableRow rowNext =  (TableRow) tabela.getChildAt(x);
        View vizinho;

        Log.e("o cliked é "," isso ="+clickedID);
        Log.e( "row = "+rowSize, "col "+ colSize);

        Assento ass = salaAtual.getAssento(x,y);
        if(ass == null){
            Log.e("veio null","mas q porra");
            return;
        }
        int tipo = ass.getTipo();

        if(mapaIsChecked[x][y]){
            isMeia[x][y]=false;

            mapaIsChecked[x][y]=false;
                Log.e("x ="+x,"y = "+y);
            switch (tipo){
                case 1://padrao
                    v.setBackgroundResource(R.drawable.assento_padrao_verde);

                    break;
                case 2://reclina
                    v.setBackgroundResource(R.drawable.assento_reclinavel_verde);

                    break;
                case 3://cadeirante
                    v.setBackgroundResource(R.drawable.assento_cadeirante_verde);

                    break;
                case 4://casal
                    v.setBackgroundResource(R.drawable.assento_casal_verde_esquerda);
                    vizinho =  rowNext.getChildAt(y+1);
                    vizinho.setBackgroundResource(R.drawable.assento_casal_verde_direita);
                 //   ImageView proxima =(ImageView)findViewById(Integer.parseInt(clickedID)+1);
                   // proxima.setBackgroundResource(R.drawable.assento_casal_verde_direita);
                    break;
                case 5://obeso
                    v.setBackgroundResource(R.drawable.assento_obeso_verde_esquerdo);
                    vizinho =  rowNext.getChildAt(y+1);
                    vizinho.setBackgroundResource(R.drawable.assento_obeso_verde_direito);
                    //ImageView proxima2 =(ImageView)findViewById(Integer.parseInt(clickedID)+1);
                    //proxima2.setBackgroundResource(R.drawable.assento_obeso_verde_direito);
                    break;
                case 6://movel
                    v.setBackgroundResource(R.drawable.assento_movel_verde);
                    break;
            }

        }else{
            mapaIsChecked[x][y]=true;

            DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            isMeia[x][y]=false;
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            isMeia[x][y]=true;
                            break;
                    }
                }

            };
            AlertDialog.Builder meiaConfirm = new AlertDialog.Builder(v.getContext());
            meiaConfirm.setMessage("Qual tipo do ingresso?")
                    .setPositiveButton("Inteira",dialog).setNegativeButton("Meia",dialog).show();


            switch (tipo){
                case 1://padrao
                    v.setBackgroundResource(R.drawable.assento_padrao_cinza);

                    break;
                case 2://reclina
                    v.setBackgroundResource(R.drawable.assento_reclinavel_cinza);

                    break;
                case 3://cadeirante
                    v.setBackgroundResource(R.drawable.assento_cadeirante_cinza);

                    break;
                case 4://casal
                    v.setBackgroundResource(R.drawable.assento_casal_esquerdo_cinza);
                    vizinho =  rowNext.getChildAt(y+1);
                    vizinho.setBackgroundResource(R.drawable.assento_casal_cinza_direita);
                   // ImageView proxima =(ImageView)findViewById(clickedID+1);
                    ////proxima.setBackgroundResource(R.drawable.assento_casal_cinza_direita);
                    break;
                case 5://obeso
                    v.setBackgroundResource(R.drawable.assento_obeso_cinza_esquerdo);
                    vizinho =  rowNext.getChildAt(y+1);
                    vizinho.setBackgroundResource(R.drawable.assento_obeso_cinza_direito);
                    //ImageView proxima2 =(ImageView)findViewById(clickedID+1);
                    //proxima2.setBackgroundResource(R.drawable.assento_obeso_cinza_direito);
                    break;
                case 6://movel
                    v.setBackgroundResource(R.drawable.assento_movel_cinza);
                    break;
            }


        }


    }
}
