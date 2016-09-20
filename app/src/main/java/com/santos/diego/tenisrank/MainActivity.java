package com.santos.diego.tenisrank;

//@dimen/activity_vertical_margin
//@color/background_material_light

/*
android:centerColor="#4CAF50"
        android:endColor="#2E7D32"
        android:startColor="#81C784"
        android:type="linear" />
 */

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.santos.diego.tenisrank.CustomDialogDesafioMarcado.OnCustomDialogDesafioInteractionListener;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentJogos.OnFragmentInteractionListener, OnCustomDialogDesafioInteractionListener,CustomDialogMarcarResultado.OnCustomDialogMarcarResultadoListener{

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
 //   private TenistasRankingAsyncTask tenistasrankingAsync = null;
    private ArrayAdapter<String> adapter_spinner = null;


    private Toolbar toolbar = null;
    private Spinner spinner = null;
    private TextView textView_Data = null;
    private TextView textView_Hora = null;
    //ProgressDialog progressDialog = null;

    private ListView lv = null;

    private String IP = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackground(ContextCompat.getDrawable(this,R.drawable.side_nav_bar));
        //getSupportActionBar().setTitle("TESTE");

/*
        spinner = (Spinner) findViewById(R.id.spinner_nav);
        textView_Data = (TextView) findViewById(R.id.textview_data);
        textView_Hora = (TextView) findViewById(R.id.textview_hora);
*/
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(1).setChecked(true);

        Intent intent = getIntent();
        idUsuario = intent.getIntExtra("idUsuario", 0);
        idTenista = intent.getIntExtra("idTenista", 0);
        nome = intent.getStringExtra("Nome");
        email = intent.getStringExtra("Email");
        idCoordenador = intent.getIntExtra("idCoordenador",0);



/*
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

        */

        View header = navigationView.getHeaderView(0);
        TextView tvnome = (TextView) header.findViewById(R.id.textViewNome);
        tvnome.setText(nome);
        TextView tv = (TextView) header.findViewById(R.id.textView);
        tv.setText(email);




        //lista do ranking
        //lv = (ListView) findViewById(R.id.listView_Ranking);   ///aqui tava

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
       // final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh); //aqui tava


        //ranking = new ArrayList<RankingHistorico>();

        //popula a variável ranking com os dados do último ranking gerado
//        RankingAsyncTask rasync = new RankingAsyncTask(0,categorias.get(spinner.getSelectedItemPosition()).getIdCategoria());
  //      rasync.execute((Void) null);

        //Log.i("RANKING",ranking.get(0).getData());
        // popula a lista com os tenistas de acordo com o ID do último ranking gerado
//        tenistas = new ArrayList<Tenista>();
//
  //      adapter = new CustomArrayRankingAdapter(this, tenistas);
    //    lv.setAdapter(adapter);
      //  adapter.setNome(nome);
        //adapter.setEmail(email);
        //adapter.setidUsuario(idUsuario);


        /*
        if (ranking!=null) {
            Log.i("RANKING","ANTES DO RANKING.GET(0)");
            final RankingHistorico rhistorico = ranking.get(0);

            tenistasrankingAsync = new TenistasRankingAsyncTask(rhistorico);


            tenistasrankingAsync.execute((Void) null);
        }
*/


//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
  //          @Override
    //        public void onRefresh() {
      //          RankingAsyncTask rasync = new RankingAsyncTask(0,categorias.get(spinner.getSelectedItemPosition()).getIdCategoria());
        //        rasync.execute((Void) null);
          //      refreshLayout.setRefreshing(false);
              //  Log.i("REFRESH", "DEU CERTO");
                /*
                if (ranking!=null) {
                    tenistasrankingAsync = new TenistasRankingAsyncTask(ranking.get(0));
                    tenistasrankingAsync.execute((Void) null);
                    refreshLayout.setRefreshing(false);
                }*/
          //  }
       // });


       // SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
     //   IP = shared.getString("edit_text_preference_ip","192.168.0.15");
        //IP = "192.168.0.15";


//        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
  //      SharedPreferences.Editor editor = pref.edit();
    //    editor.putBoolean("ligou",false);
      //  editor.commit();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        IP = pref.getString("ip","0");


        ConsultaRegrasAsyncTask consultaRegras = new ConsultaRegrasAsyncTask();
        consultaRegras.execute((Void) null);

    }


    public void showDialog(String Msg)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(Msg);
        builder1.setCancelable(true);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        final int idTenistaDesafiado = pref.getInt("idTenistaDesafiado",0);

        builder1.setPositiveButton(
                "Sim",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (idTenistaDesafiado>0) {
                           CustomDialogDesafioMarcado customDialogDesafio = CustomDialogDesafioMarcado.newInstance(idUsuario,idTenista,idTenistaDesafiado,nome,email);
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            //fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();


                           // FragmentManager fm = getSupportFragmentManager();
                            //CustomDialogDesafioMarcado dialogcustom = new CustomDialogDesafioMarcado();
                            customDialogDesafio.show(fragmentManager, "customdialog");
                        }

                    }
                });

        builder1.setNegativeButton(
                "Não",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onPause()
    {
        super.onPause();

    }



    //caso o usuário tenha ligado para um jogador para marcar um desafio, essa verificação é feita
    //toda vez que o aplicativo volta a ser executado. A possibilidade de ligação é feita através
    // do arquivo CustomArrayRankingAdapter que armazena o id do TenistaDesafiado

    @Override
    public void onResume()
    {
        super.onResume();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean ligou = pref.getBoolean("ligou",false);
        String jogador = pref.getString("nome_jogador_desafiado",null);

        String msg = new String();


        if (jogador == null)
            msg ="Você ligou para um jogador para marcar um desafio. O jogo foi agendado?";
        else
            msg = "Você ligou para o jogador \"" + jogador + "\" para marcar um desafio. O jogo foi agendado?";

        if (ligou)
        {
            showDialog(msg);

            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("ligou",false);
            editor.putString("nome_jogador_desafiado",null);
            editor.putString("idTenistaDesafiado",null);
            editor.commit();


        }





        //FragmentTransaction f = getSupportFragmentManager().beginTransaction();
        //f.detach()

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

        //se não for coordenador não poderá alterar as regras
        if (idCoordenador==0)
            menu.getItem(0).setEnabled(false);

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


            Intent intent = new Intent(this,PreferencesActivity.class);

            intent.putExtra("idCoordenador",idCoordenador);
            this.startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_jogos) {
            FragmentJogos fragment = FragmentJogos.newInstance(idUsuario,idTenista,nome,email);
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();
            this.setTitle("Jogos");


        } else if (id == R.id.nav_ranking) {
            FragmentRanking fragment = FragmentRanking.newInstance(idUsuario,idTenista,nome,email);
            FragmentManager fragmentManager = this.getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.content_frame,fragment,"RankingFragment").commit();
            this.setTitle("Ranking");

        } else if (id == R.id.nav_categorias) {

            FragmentCategorias fragment = FragmentCategorias.newInstance("","");
            fragment.setIdCoordenador(idCoordenador);
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,fragment,"Categorias").commit();
            this.setTitle("Categorias");

        } else if (id == R.id.nav_usuarios) {

            FragmentTenistas fragment = FragmentTenistas.newInstance("","");
            fragment.setIdCoordenador(idCoordenador);
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,fragment,"Tenistas").commit();

            this.setTitle("Tenistas");

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //Do Fragmento FragmentJogos
    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    //Do Fragmento CustomDialogDesafioMarcado
    @Override
    public void onCustomDialogFragmentInteraction(String Data, String Hora, String Descricao, int idTenistaDesafiador, int idTenistaDesafiado) {

        Desafio t = new Desafio();

        t.setIdQuadra(1);
        t.setIdTenistaDesafiado(idTenistaDesafiado);
        t.setIdTenistaDesafiador(idTenistaDesafiador);
        t.setGanhador(0);
        t.setAceitoDesafiado(1);
        t.setAceitoDesafiador(1);
        t.setConfirmadoCoordenador(0);
        t.setConfirmadoDesafiado(0);
        t.setConfirmadoDesafiador(0);
        t.setData(Data);
        t.setHora(Hora);
        t.setJogado(0);
        t.setDescricao(Descricao);

        InsertDesafioAsyncTask insDesAsyncTask = new InsertDesafioAsyncTask();
        insDesAsyncTask.execute(t);
    }

    @Override
    public void onCustomDialogMarcarResultadoOKClicked(Desafio d) {

        InsertDesafioAsyncTask insertDesafioAsyncTask = new InsertDesafioAsyncTask();
        Log.i("IDDESAFIOS",String.valueOf(d.getIdDesafio()));
        insertDesafioAsyncTask.setInsere(false);
        insertDesafioAsyncTask.execute(d);
    }

    //insere os desfios e atualiza os dados do fragmento do Ranking com o novo jogo
private class InsertDesafioAsyncTask extends AsyncTask <Desafio, Integer, Boolean>
{
    //se insere = false então o banco atualizará os dados ao invés de inserir
    private boolean insere = true;
    private ProgressDialog progressDialog;

    public boolean isInsere() {
        return insere;
    }

    public void setInsere(boolean insere) {
        this.insere = insere;
    }



    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
         progressDialog = new ProgressDialog(MainActivity.this);
         progressDialog.setMessage("Enviando dados, aguarde...");
         progressDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean b)
    {
        super.onPostExecute(b);

        //atualiza o fragmento do Ranking (com os novos dados gerados)
        if (MainActivity.this.getTitle().toString().contains("Ranking")) {
            Fragment fragment = FragmentRanking.newInstance(idUsuario,idTenista,nome,email);
            FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.content_frame,fragment,"RankingFragment").commit();


        }
        progressDialog.cancel();

    }


    @Override
    protected Boolean doInBackground(Desafio... arrayLists) {

        DatabaseJson json = new DatabaseJson();
        Integer result = 0;


        json.setIP(IP);
        if (insere) {
            if (!json.insereDesafio(1, arrayLists[0]))
                Log.i("DESAFIO", "ERRO");
            else
                Log.i("DESAFIO", "SEMERRO");
        }
        else
        {
            if (!json.insereDesafio(2, arrayLists[0]))
                Log.i("DESAFIO", "ERRO");
            else
                Log.i("DESAFIO", "SEMERRO");
        }




        return true;
    }
}

private class ConsultaRegrasAsyncTask extends AsyncTask<Void, Void, Boolean>
{

    private ProgressDialog progressDialog;
    ArrayList<Regra> regras = null;

    Regra regra = null;

    @Override
    protected Boolean doInBackground(Void... voids) {

        DatabaseJson json = new DatabaseJson();
        json.setIP(IP);

        regras = json.getRegras();


        if (regras==null || json.getError()>0) {
            Log.i("Regras","false");
            return false;
        }

        Log.i("Regras","true");

        if (regras!=null)
            regra = regras.get(0);

        return true;
    }



    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Carregando...");
        progressDialog.setCancelable(false);
        progressDialog.show();



    }

    @Override
    protected void onPostExecute(Boolean b)
    {
        super.onPostExecute(b);

        if (regras!=null) {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor = pref.edit();
            Log.i("idRegra",regra.getIdRegra().toString());
            editor.putString("idRegra", regra.getIdRegra().toString());
            editor.putString("DataAlteracao",regra.getDataAlteracao());
            editor.putString("QtdDiasPorMesPodeDesafiar",regra.getQtdDiasPorMesPodeDesafiar().toString());
            editor.putString("QtdDiasPMesReceberDesafio",regra.getQtdDiasPorMesRebecerDesafio().toString());
            editor.putString("PosicaoMaximaQPodeDesafiar",regra.getPosicaoMaximaQPodeDesafiar().toString());
            editor.putString("DesafiadorQtdPosCasoVitoria",regra.getDesafiadorQtdPosCasoVitoria().toString());
            editor.putString("DesafiadoQtdPosCasoVitoria",regra.getDesafiadoQtdPosCasoVitoria().toString());
            editor.putString("DesafiadorQtdPosCasoDerrota",regra.getDesafiadorQtdPosCasoDerrota().toString());
            editor.putString("DesafiadoQtdPosCasoDerrota",regra.getDesafiadoQtdPosCasoDerrota().toString());
            editor.putString("QtdPosCaiCasoNaoDesafieMes",regra.getQtdPosCaiCasoNaoDesafieMes().toString());
            editor.putString("TempoWO",regra.getTempoWO().toString());
            editor.putString("QtdPosicoesPerdeCasoWO",regra.getQtdPosicoesPerdeCasoWO().toString());


            editor.commit();




        }

        progressDialog.cancel();

        Fragment fragment = FragmentRanking.newInstance(idUsuario,idTenista,nome,email);
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();







        //MenuItem item =;
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {



        }



}


}







