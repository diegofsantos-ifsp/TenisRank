package com.santos.diego.tenisrank;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCategorias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCategorias extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView lv = null;
    private ArrayList<Categoria> categorias = null;
    private CustomArrayCategoriasAdapter adapter = null;
    private String IP=null;
    private SharedPreferences pref=null;
    private FloatingActionButton fab = null;

    private Integer idCoordenador = 0;


    public FragmentCategorias() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCategorias.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCategorias newInstance(String param1, String param2) {
        FragmentCategorias fragment = new FragmentCategorias();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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


    public Integer getIdCoordenador() {
        return idCoordenador;
    }

    public void setIdCoordenador(Integer idCoordenador) {
        this.idCoordenador = idCoordenador;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categorias, container, false);

        lv = (ListView) view.findViewById(R.id.listview_categorias);

        if (idCoordenador>0)
            registerForContextMenu(lv);

        adapter = new CustomArrayCategoriasAdapter(getActivity(), categorias);
        lv.setAdapter(adapter);

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        IP = pref.getString("ip","0");

        CategoriaAsyncTask cat = new CategoriaAsyncTask();
        cat.execute((Void) null);

        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_categorias);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                CategoriaAsyncTask cat = new CategoriaAsyncTask();
                cat.execute((Void) null);

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


        fab = (FloatingActionButton) view.findViewById(R.id.fab_categorias);

        if (idCoordenador==0)
            fab.setVisibility(View.GONE);
        else
            fab.setVisibility(View.VISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setTitle("Cadastro de categorias");
                builder1.setMessage("Insira o nome da nova categoria: \n\n");
                builder1.setCancelable(true);
                final EditText input = new EditText(getActivity());
                builder1.setView(input);

                builder1.setPositiveButton(
                        "Cadastrar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                GerenciaCategoriaAsyncTask cadastrar = new GerenciaCategoriaAsyncTask();
                                cadastrar.setTipo(1);
                                Categoria temp = new Categoria();
                                temp.setNome(input.getText().toString());
                                cadastrar.setCategoria(temp);
                                cadastrar.execute((Void) null);
                                CategoriaAsyncTask catAsync = new CategoriaAsyncTask();
                                catAsync.execute((Void) null);


                            }
                        });

                builder1.setNegativeButton(
                        "Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });


        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.listview_categorias) {
            //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            //menu.setHeaderTitle(Countries[info.position]);
            //String[] menuItems = getResources().getStringArray(R.arr);
            //for (int i = 0; i<menuItems.length; i++) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_listview_categorias, menu);
            }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        //String[] menuItems = getResources().getStringArray(R.menu.menu_listview_categorias);
        //String menuItemName = menuItems[menuItemIndex];
        //String listItemName = Countries[info.position];

       // text.setText(String.format("Selected %s for item %s", menuItemName, listItemName));
        switch(item.getItemId()) {
            case R.id.menu_categorias_editar:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setTitle("Alteração do nome da Categoria");
                builder1.setMessage("Insira o novo nome da categoria: \n\n");
                builder1.setCancelable(true);
                final EditText input = new EditText(getActivity());
                input.setText(categorias.get(info.position).getNome());
                input.setSelection(input.getText().length());
                builder1.setView(input);

                builder1.setPositiveButton(
                        "Alterar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                GerenciaCategoriaAsyncTask cadastrar = new GerenciaCategoriaAsyncTask();
                                cadastrar.setTipo(2);
                                categorias.get(info.position).setNome(input.getText().toString());
                                cadastrar.setCategoria(categorias.get(info.position));
                                cadastrar.execute((Void) null);
                                //CategoriaAsyncTask catAsync = new CategoriaAsyncTask();
                                //catAsync.execute((Void) null);


                            }
                        });

                builder1.setNegativeButton(
                        "Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

                return true;
            case R.id.menu_categorias_excluir:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                builder2.setTitle("Exclusão de Categoria");
                builder2.setMessage("A categoria só poderá ser excluída caso não existam tenistas e desafios cadastrados com ela.\nTem certeza que deseja excluir a categoria?\n");
                builder2.setCancelable(true);
               // final EditText input2 = new EditText(getActivity());
                //input2.setText(categorias.get(info.position).getNome());
                //builder2.setView(input2);

                builder2.setPositiveButton(
                        "Sim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                GerenciaCategoriaAsyncTask cat = new GerenciaCategoriaAsyncTask();
                                cat.setCategoria(categorias.get(info.position));
                                cat.setTipo(3); //exclusão
                                cat.execute((Void) null);

                            }
                        });

                builder2.setNegativeButton(
                        "Não",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert12 = builder2.create();
                alert12.show();

                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }


    //popula a variável Categorias com todas as categorias existentes e atualiza o listview
    private class CategoriaAsyncTask extends AsyncTask<Void, Integer,ArrayList<Categoria> > {

        private ArrayList<Categoria> temp = null;
        // private DatabaseJson json = null;

        private ProgressDialog progressDialog = null;

        CategoriaAsyncTask ()
        {

        }

        @Override
        protected ArrayList<Categoria> doInBackground(Void... values) {


            //  if (ranking!=null || ranking==null) {
            publishProgress(10);
            //Thread.sleep(1000);
            DatabaseJson json = new DatabaseJson();

            json.setIP(IP);
            // publishProgress(30);
            //Thread.sleep(1000);
            //    Log.i("RANKING","DEPOIS");
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
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Aguarde...");
            progressDialog.setCancelable(false);
            progressDialog.show();


        }

        @Override
        protected void onPostExecute(ArrayList<Categoria> cat) {
            super.onPostExecute(cat);
            if (cat!=null) {
                //;
                categorias = cat;
                adapter.setItem(categorias);
                //if(nomesCat!=null)
                  //  nomesCategorias.clear();
                //for (int x=0; x<categorias.size(); x++) {
                 //   nomesCategorias.add(categorias.get(x).getNome());
                    //              Log.i("CAT",categorias.get(x).getNome());
                }

                adapter.notifyDataSetChanged();

                progressDialog.cancel();
            }
        }

    private class GerenciaCategoriaAsyncTask extends AsyncTask<Void, Void, Boolean>
    {

        private ProgressDialog progressDialog = null;
        private int tipo = 1; //1 insere e 2 altera
        private Categoria categoria = null;

        public int getTipo() {
            return tipo;
        }

        public void setTipo(int tipo) {
            this.tipo = tipo;
        }

        public Categoria getCategoria() {
            return categoria;
        }

        public void setCategoria(Categoria categoria) {
            this.categoria = categoria;
        }


        @Override
        protected Boolean doInBackground(Void... voids) {

            DatabaseJson json = new DatabaseJson();
            json.setIP(IP);

            json.insereCategoria(tipo,categoria);

            if (json.getError()>0)
                return false;

            return true;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Inserindo dados, aguarde...");
            progressDialog.setCancelable(false);
            progressDialog.show();


        }

        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);


           // adapter.notifyDataSetChanged();

            progressDialog.cancel();
            CategoriaAsyncTask catAsync = new CategoriaAsyncTask();
            catAsync.execute((Void) null);


        }
    }

}


