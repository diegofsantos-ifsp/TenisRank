package com.santos.diego.tenisrank;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRanking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRanking extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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


    public FragmentRanking() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRanking.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRanking newInstance(Integer param1, Integer param2, String param3, String param4) {
        FragmentRanking fragment = new FragmentRanking();
        Bundle args = new Bundle();
        args.putInt("idUsuario", param1);
        args.putInt("idTenista", param2);
        args.putString("Nome", param3);
        args.putString("Email", param4);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_ranking, container, false);

        spinner = (Spinner) view.findViewById(R.id.spinner_nav);
        textView_Data = (TextView) view.findViewById(R.id.textview_data);
        textView_Hora = (TextView) view.findViewById(R.id.textview_hora);


        Bundle args = getArguments();
        idUsuario = args.getInt("idUsuario", 0);
        idTenista = args.getInt("idTenista",0);
        nome = args.getString("Nome",null);
        email = args.getString("Email",null);


        nomesCategorias = new ArrayList<String>();
        adapter_spinner = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,nomesCategorias);
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


        lv = (ListView) view.findViewById(R.id.listView_Ranking);

        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);

        tenistas = new ArrayList<Tenista>();

        adapter = new CustomArrayRankingAdapter(getActivity(), tenistas);
        lv.setAdapter(adapter);
        adapter.setNome(nome);
        adapter.setEmail(email);
        adapter.setidUsuario(idUsuario);



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



        return view;
    }




    //faz a consulta na tabela de ranking e retorna o ranking atual (último registro)
    private class RankingAsyncTask extends AsyncTask<Void, Integer,ArrayList<RankingHistorico> > {

        private ArrayList<RankingHistorico> temp = null;
        // private DatabaseJson json = null;
        private Integer tipo = null;
        private Boolean updateTenistas=true;
        private Integer idcat = null;
        private TextView textViewDesafiador;
        private TextView textViewDesafiado;
        private TextView data;
        private TextView hora;
        private LinearLayout linearLayout;

        RankingAsyncTask (Integer t, Integer idCat)
        {
            tipo=t;
            idcat = idCat;
            textViewDesafiador = (TextView) getActivity().findViewById(R.id.textView_Desafiador);
            textViewDesafiado = (TextView) getActivity().findViewById(R.id.textView_Desafiado);
            data = (TextView) getActivity().findViewById(R.id.textView_Proxima_Data);
            hora = (TextView) getActivity().findViewById(R.id.textView_Proxima_Hora);
            linearLayout = (LinearLayout) getActivity().findViewById(R.id.layoutStatusBar);

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

            //json.setIP(IP);
            // publishProgress(30);
            //Thread.sleep(1000);

            temp = json.getRanking(idcat);



            //atualiza os dados do próximo jogo do usuário atual

            if (idTenista!=0) {
                final ArrayList<Desafio> des;


                des = json.getJogosByTenista(0, idTenista, 1);

                if (des!=null) {
                    //  Log.i("TENISTA_ID",idTenista.toString());
                    //Log.i("TENISTA",des.get(0).getData());
                    final Tenista desafiado = des.get(0).getTenistaDesafiado();
                    final Tenista desafiador = des.get(0).getTenistaDesafiador();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String strDesafiado;
                            String strDesafiador;

                            strDesafiado = desafiado.getUsuario().getNome();
                            strDesafiador = desafiador.getUsuario().getNome();

                            strDesafiado=strDesafiado.substring(0,strDesafiado.indexOf(" "));
                            strDesafiador=strDesafiador.substring(0,strDesafiador.indexOf(" "));

                            textViewDesafiador.setText(strDesafiador);
                            textViewDesafiado.setText(strDesafiado);
                            data.setText(des.get(0).getData());
                            hora.setText(des.get(0).getHora());
                            linearLayout.setVisibility(View.VISIBLE);


                        }
                    });

                    //  Log.i("TENISTADESAFIADO", desafiado.getUsuario().getNome());
                    //Log.i("TENISTADESAFIADOR",desafiador.getUsuario().getNome());
                    // Log.i("TENISTADESAFIADOR", desafiador.getUsuario().getNome());
                }
            }

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
        //private ArrayList<Desafio> tempDesafio = null;
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
            //json.setIP(IP);

            // publishProgress(30);
            //Thread.sleep(1000);
            //              Log.i("RANKING","DEPOIS");
            //ArrayList<Usuario> users = json.getUsersByEmail(mEmail);
            temp = json.getTenistasByRankingID(rank_temp.getIdRanking());

            //atualiza a posição atual no ranking do usuário
            int pos=0;

            if (temp!=null) {
                for (int x = 0; x < temp.size(); x++)
                    if (temp.get(x).getUsuario().getEmail().compareToIgnoreCase(email) == 0) {
                        posicaoUsuario = temp.get(x).getPosicaoAtualRanking();

                        pos = x;
                        //                        Log.i("POSICAO", posicaoUsuario.toString());
                        break;

                    }
            }

            //verifica (de acordo com as regras) os jogadores acima do jogador atual que possui jogos marcados
            //e, portanto, não poderão aceitar desafios


            ArrayList<Desafio> tempDesafio = null;
            for (int x=pos; x>=pos-3; x--) {
                tempDesafio = json.getJogosByTenista(0,temp.get(x).getIdTenista(),0);

                if (tempDesafio!=null)
                {
                    temp.get(x).setTemJogoMarcado(true);
                }
                else
                    temp.get(x).setTemJogoMarcado(false);
            }

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
        protected void onPostExecute(ArrayList<Tenista> tenistasTemp) {
            super.onPostExecute(tenistasTemp);
            if (tenistasTemp!=null) {
                tenistas = tenistasTemp;
                adapter.setPosicaoUsuario(posicaoUsuario);
                adapter.setItem(tenistasTemp);
                adapter.notifyDataSetChanged();

            }
            else
            {
                tenistas.clear();
                tenistas = null;
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

            // json.setIP(IP);
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
