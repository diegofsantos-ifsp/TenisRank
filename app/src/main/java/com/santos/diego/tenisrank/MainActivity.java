package com.santos.diego.tenisrank;

//@dimen/activity_vertical_margin
//@color/background_material_light

/*
android:centerColor="#4CAF50"
        android:endColor="#2E7D32"
        android:startColor="#81C784"
        android:type="linear" />
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String nome = null;
    private String email = null;
    private Integer idUsuario = null;
    private Integer idTenista = null;
    private Integer idCoordenador = null;
    private Integer posicaoUsuario=null;
    private ArrayList<Categoria> categorias = null; //armazena todas as categorias existentes
    private ArrayList<String> nomesCategorias = null; //usado para armazenar os nomes das categorias no spinner

    private ArrayList<RankingHistorico> ranking = null; //armazena os dados do último ranking gerado
    private ArrayList<Tenista> tenistas = null; //armazena os dados de todos os tenistas do ranking gerado
   // private Integer idUsuario = null;
    CustomArrayRankingAdapter adapter = null;
    private TenistasRankingAsyncTask tenistasrankingAsync = null;
    private ArrayAdapter<String> adapter_spinner = null;


    private Toolbar toolbar = null;
    private Spinner spinner = null;
    private TextView textView_Data = null;
    private TextView textView_Hora = null;

    private ListView lv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackground(ContextCompat.getDrawable(this,R.drawable.side_nav_bar));
        //getSupportActionBar().setTitle("TESTE");


        spinner = (Spinner) findViewById(R.id.spinner_nav);
        textView_Data = (TextView) findViewById(R.id.textview_data);
        textView_Hora = (TextView) findViewById(R.id.textview_hora);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Intent intent = getIntent();
        idUsuario = intent.getIntExtra("idUsuario", 0);
        idTenista = intent.getIntExtra("idTenista", 0);
        nome = intent.getStringExtra("Nome");
        email = intent.getStringExtra("Email");


        nomesCategorias = new ArrayList<String>();
        adapter_spinner = new ArrayAdapter<String>(this,R.layout.spinner_item,nomesCategorias);
        adapter_spinner.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter_spinner);



        CategoriaAsyncTask catAsync = new CategoriaAsyncTask(0);

        catAsync.execute((Void) null);



        //toda a comunicação para pegar os dados do Ranking e dos Tenistas começa aqui
        //Essa função é ativada a primeira vez que é criado o spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Integer pos = spinner.getSelectedItemPosition();

                RankingAsyncTask r = new RankingAsyncTask(0, categorias.get(pos).getIdCategoria());
                r.execute((Void) null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        View header = navigationView.getHeaderView(0);
        TextView tvnome = (TextView) header.findViewById(R.id.textViewNome);
        tvnome.setText(nome);
        TextView tv = (TextView) header.findViewById(R.id.textView);
        tv.setText(email);

        //lista do ranking
        lv = (ListView) findViewById(R.id.listView_Ranking);

/*
        Tenista item = new Tenista();
        Usuario item1 = new Usuario();

        item1.setNome("Diego Ferreira dos Santos");
        item.setPosicaoAtualRanking(3);
        item.setUsuario(item1);
        items.add(item);

        Tenista item2 = new Tenista();
        Usuario item3 = new Usuario();

        item3.setNome("Rebeca Buzzo Feltrin");
        item2.setPosicaoAtualRanking(2);
        item2.setUsuario(item3);
        items.add(item2);

  */
        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);


        //ranking = new ArrayList<RankingHistorico>();

        //popula a variável ranking com os dados do último ranking gerado
//        RankingAsyncTask rasync = new RankingAsyncTask(0,categorias.get(spinner.getSelectedItemPosition()).getIdCategoria());
  //      rasync.execute((Void) null);

        //Log.i("RANKING",ranking.get(0).getData());
        // popula a lista com os tenistas de acordo com o ID do último ranking gerado
        tenistas = new ArrayList<Tenista>();

        adapter = new CustomArrayRankingAdapter(this, tenistas);
        lv.setAdapter(adapter);
        adapter.setNome(nome);
        adapter.setEmail(email);
        adapter.setidUsuario(idUsuario);


        /*
        if (ranking!=null) {
            Log.i("RANKING","ANTES DO RANKING.GET(0)");
            final RankingHistorico rhistorico = ranking.get(0);

            tenistasrankingAsync = new TenistasRankingAsyncTask(rhistorico);


            tenistasrankingAsync.execute((Void) null);
        }
*/


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RankingAsyncTask rasync = new RankingAsyncTask(0,categorias.get(spinner.getSelectedItemPosition()).getIdCategoria());
                rasync.execute((Void) null);
                refreshLayout.setRefreshing(false);
              //  Log.i("REFRESH", "DEU CERTO");
                /*
                if (ranking!=null) {
                    tenistasrankingAsync = new TenistasRankingAsyncTask(ranking.get(0));
                    tenistasrankingAsync.execute((Void) null);
                    refreshLayout.setRefreshing(false);
                }*/
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private class RankingAsyncTask extends AsyncTask<Void, Integer,ArrayList<RankingHistorico> > {

        private ArrayList<RankingHistorico> temp = null;
        // private DatabaseJson json = null;
        private Integer tipo = null;
        private Boolean updateTenistas=true;
        private Integer idcat = null;

        RankingAsyncTask (Integer t, Integer idCat)
        {
            tipo=t;
            idcat = idCat;
        } //se tipo = 1 ler o último registro adicionado


        public Boolean getUpdateTenistas() {
            return updateTenistas;
        }

        public void setUpdateTenistas(Boolean updateTenistas) {
            this.updateTenistas = updateTenistas;
        }





        @Override
        protected ArrayList<RankingHistorico> doInBackground(Void... values) {


            //  if (ranking!=null || ranking==null) {
            publishProgress(10);
            //Thread.sleep(1000);
            DatabaseJson json = new DatabaseJson();
            // publishProgress(30);
            //Thread.sleep(1000);

            temp = json.getRanking(idcat);
            //temp = json.getTenistasByRankingID(2);

            //publishProgress(60);
            //Thread.sleep(1000);

            return temp;

            //}


            //return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(ArrayList<RankingHistorico> rank) {
            super.onPostExecute(rank);
            ranking = rank;
            if (rank!=null && !rank.isEmpty()) {
           //     adapter.setItem(tenistas);
                final RankingHistorico rhistorico = ranking.get(0);

               // toolbar.setSubtitle("Gerado em: "+rhistorico.getData()+" as " + rhistorico.getHora());
                textView_Data.setText(rhistorico.getData());
                textView_Hora.setText(rhistorico.getHora());

                if (updateTenistas) {
                    tenistasrankingAsync = new TenistasRankingAsyncTask(rhistorico);


                    tenistasrankingAsync.execute((Void) null);
                }

                //   adapter.notifyDataSetChanged();

            }
            else
            {
                if (tenistas!=null) {
                    tenistas.clear();
                    adapter.setItem(tenistas);
                    adapter.notifyDataSetChanged();
                }

                textView_Data.setText("");
                textView_Hora.setText("");

            }

        }

    }






    private class TenistasRankingAsyncTask extends AsyncTask<Void, Integer,ArrayList<Tenista> > {

        private ArrayList<Tenista> temp = null;
        private ArrayList<Desafio> tempDesafio = null;
       // private DatabaseJson json = null;
        private RankingHistorico rank_temp;

        TenistasRankingAsyncTask (RankingHistorico historico)
        {
            rank_temp = historico;
        }

        @Override
        protected ArrayList<Tenista> doInBackground(Void... values) {


          //  if (ranking!=null || ranking==null) {
//                publishProgress(10);
                //Thread.sleep(1000);
                DatabaseJson json = new DatabaseJson();
               // publishProgress(30);
                //Thread.sleep(1000);
  //              Log.i("RANKING","DEPOIS");
                //ArrayList<Usuario> users = json.getUsersByEmail(mEmail);
                temp = json.getTenistasByRankingID(rank_temp.getIdRanking());

            //atualiza a posição atual no ranking do usuário
                if (temp!=null) {
                    for (int x = 0; x < temp.size(); x++)
                        if (temp.get(x).getUsuario().getEmail().compareToIgnoreCase(email) == 0) {
                            posicaoUsuario = temp.get(x).getPosicaoAtualRanking();
    //                        Log.i("POSICAO", posicaoUsuario.toString());
                            break;

                        }
                }

            //verifica (de acordo com as regras) os jogadores acima do jogador atual que possui jogos marcados
            //e, portanto, não poderá aceitar desafios





                //publishProgress(60);
                //Thread.sleep(1000);

                    return temp;

            //}


            //return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(ArrayList<Tenista> tenistas) {
            super.onPostExecute(tenistas);
            if (tenistas!=null) {
                adapter.setPosicaoUsuario(posicaoUsuario);
                adapter.setItem(tenistas);
                adapter.notifyDataSetChanged();

            }
        }

    }



//popula a variável Categorias com todas as categorias existentes e atualizar o spinner
    private class CategoriaAsyncTask extends AsyncTask<Void, Integer,ArrayList<Categoria> > {

        private ArrayList<Categoria> temp = null;
        // private DatabaseJson json = null;
        private Integer idCategoria=null;

        CategoriaAsyncTask (Integer i)
        {
            idCategoria=i;
        }

        @Override
        protected ArrayList<Categoria> doInBackground(Void... values) {


            //  if (ranking!=null || ranking==null) {
            publishProgress(10);
            //Thread.sleep(1000);
            DatabaseJson json = new DatabaseJson();
            // publishProgress(30);
            //Thread.sleep(1000);
            Log.i("RANKING","DEPOIS");
            //ArrayList<Usuario> users = json.getUsersByEmail(mEmail);
            temp = json.getCategorias();

            //publishProgress(60);
            //Thread.sleep(1000);

            return temp;

            //}


            //return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(ArrayList<Categoria> cat) {
            super.onPostExecute(cat);
            if (cat!=null) {
                //;
                categorias = cat;
                if(nomesCategorias!=null)
                    nomesCategorias.clear();
                for (int x=0; x<categorias.size(); x++) {
                    nomesCategorias.add(categorias.get(x).getNome());
                    Log.i("CAT",categorias.get(x).getNome());
                }
                adapter_spinner.notifyDataSetChanged();


            }
        }

    }

}







