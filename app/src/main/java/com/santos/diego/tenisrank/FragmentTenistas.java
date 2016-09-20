package com.santos.diego.tenisrank;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTenistas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTenistas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FloatingActionButton fab = null;

    private ListView lv = null;
    private Spinner spinner = null;

    private ArrayList<String> nomesCategorias = null;
    private ArrayAdapter<String> adapter_spinner = null;
    private ArrayList<Categoria> categorias = null;

    private ArrayList<Tenista> tenistas = null;

    private CustomArrayTenistasAdapter adapter = null;

    private Integer spinnerPositionSelected = 0;

    private Integer idCoordenador = 0;

    private String IP = null;



    public FragmentTenistas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentUsuarios.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTenistas newInstance(String param1, String param2) {
        FragmentTenistas fragment = new FragmentTenistas();
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


    @Override
    public void onResume()
    {
        super.onResume();

        if (categorias!=null) {
            TenistasAsyncTask ten = new TenistasAsyncTask();

            ten.setIdCategoria(categorias.get(spinner.getSelectedItemPosition()).getIdCategoria());
            ten.execute((Void) null);
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

        View view = inflater.inflate(R.layout.fragment_tenistas, container, false);
        // Inflate the layout for this fragment

        lv = (ListView) view.findViewById(R.id.listview_usuarios);
        spinner = (Spinner) view.findViewById(R.id.spinner_cat_usuarios);


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        IP = pref.getString("ip","0");

        fab = (FloatingActionButton) view.findViewById(R.id.fab_tenistas);

        if (idCoordenador>0)
            fab.setVisibility(View.VISIBLE);
        else
            fab.setVisibility(View.GONE);

        nomesCategorias = new ArrayList<String>();
        adapter_spinner = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,nomesCategorias);
        adapter_spinner.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter_spinner);


        CategoriaAsyncTask catAsync = new CategoriaAsyncTask();
        catAsync.setUpdateTenistas(false);
        catAsync.execute((Void) null);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerPositionSelected = spinner.getSelectedItemPosition();

                //preenche o spinner
               TenistasAsyncTask ten = new TenistasAsyncTask();
                ten.setIdCategoria(categorias.get(spinnerPositionSelected).getIdCategoria());
                ten.execute((Void) null);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //listview adapter
        adapter = new CustomArrayTenistasAdapter(getActivity(),tenistas);
        lv.setAdapter(adapter);

        if (idCoordenador>0)
            registerForContextMenu(lv);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout ln = (LinearLayout) view.findViewById(R.id.linearlayout_tenistas_details);
                if (ln.getVisibility() == View.VISIBLE)
                    ln.setVisibility(View.GONE);
                else
                    ln.setVisibility(View.VISIBLE);

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Activity_CadastroUsuarios.class);
                intent.putExtra("idCoordenador",idCoordenador);

                //       intent.putExtra("idUsuario",user.getId());
                //     intent.putExtra("Nome",user.getNome());
                //   intent.putExtra("Email",user.getEmail());
                // intent.putExtra("idTenista",0);
                // intent.putExtra("idCoordenador",1);
                getActivity().startActivity(intent);

            }
        });

        return  view;
    }




    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.listview_usuarios) {
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
                Intent intent = new Intent(getActivity(),Activity_CadastroUsuarios.class);

                intent.putExtra("idCoordenador",idCoordenador);
                intent.putExtra("operacao",2);
                intent.putExtra("idTenista",tenistas.get(info.position).getIdTenista());

                intent.putExtra("idUsuario",tenistas.get(info.position).getUsuario().getId());
                intent.putExtra("Nome",tenistas.get(info.position).getUsuario().getNome());
                intent.putExtra("Email",tenistas.get(info.position).getUsuario().getEmail());
                intent.putExtra("Telefone",tenistas.get(info.position).getUsuario().getTelefone());
                intent.putExtra("Endereco",tenistas.get(info.position).getUsuario().getEndereco());
                intent.putExtra("Senha",tenistas.get(info.position).getUsuario().getSenha());
               Log.i("CadastroValido1",String.valueOf(tenistas.get(info.position).getUsuario().isCadastrovalido()));
                intent.putExtra("CadastroValido",tenistas.get(info.position).getUsuario().isCadastrovalido());
                intent.putExtra("idCategoria",tenistas.get(info.position).getCategoria());
                intent.putExtra("EstaNoRanking",tenistas.get(info.position).getEstaNoRanking());
                //       intent.putExtra("idUsuario",user.getId());
                //     intent.putExtra("Nome",user.getNome());
                //   intent.putExtra("Email",user.getEmail());
                // intent.putExtra("idTenista",0);
                // intent.putExtra("idCoordenador",1);
                getActivity().startActivity(intent);

                return true;
            case R.id.menu_categorias_excluir:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                builder2.setTitle("Exclusão de Tenista");
                builder2.setMessage("O Tenista só poderá ser excluído caso não existam desafios e jogos registrados com ele.\nVocê pode desativar o cadastro do usuário sem excluir o tenista.\n\n Tem certeza que deseja excluir o tenista?\n");
                builder2.setCancelable(true);
                // final EditText input2 = new EditText(getActivity());
                //input2.setText(categorias.get(info.position).getNome());
                //builder2.setView(input2);

                builder2.setPositiveButton(
                        "Sim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ExclusaoAsyncTask exc = new ExclusaoAsyncTask();
                                exc.setTenista(tenistas.get(info.position));

                                exc.execute((Void) null);
                               // GerenciaCategoriaAsyncTask cat = new GerenciaCategoriaAsyncTask();
                               // cat.setCategoria(categorias.get(info.position));
                               // cat.setTipo(3); //exclusão
                               // cat.execute((Void) null);

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





    //popula a variável Categorias com todas as categorias existentes e atualiza o spinner
    private class CategoriaAsyncTask extends AsyncTask<Void, Integer,ArrayList<Categoria> > {

        private ArrayList<Categoria> temp = null;
        // private DatabaseJson json = null;
        private Integer idCategoria=null;
        private ProgressDialog progressDialog = null;

        private Boolean updateTenistas = false;



        CategoriaAsyncTask ()
        {
        }



        public Boolean getUpdateTenistas() {
            return updateTenistas;
        }

        public void setUpdateTenistas(Boolean updateTenistas) {
            this.updateTenistas = updateTenistas;
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

            progressDialog.cancel();

            if (cat!=null) {
                //;
                categorias = cat;
                if(nomesCategorias!=null)
                    nomesCategorias.clear();
                for (int x=0; x<categorias.size(); x++) {
                    nomesCategorias.add(categorias.get(x).getNome());
                    //              Log.i("CAT",categorias.get(x).getNome());
                }
                adapter_spinner.notifyDataSetChanged();

                if (updateTenistas)
                {
                    TenistasAsyncTask ten = new TenistasAsyncTask();
                    ten.setIdCategoria(categorias.get(0).getIdCategoria());
                    ten.execute((Void) null);
                }

            }

        }

    }


   private class TenistasAsyncTask extends AsyncTask<Void, Void, Boolean>
   {
        private ProgressDialog progressDialog = null;
       private ArrayList<Tenista> tempValues = null;

       private Integer idCategoria = 0;


       public Integer getIdCategoria() {
           return idCategoria;
       }

       public void setIdCategoria(Integer idCategoria) {
           this.idCategoria = idCategoria;
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
       protected void onPostExecute(Boolean e) {
           super.onPostExecute(e);
           if (e) {
              tenistas = tempValues;



           }
           else
                tenistas = null;

           adapter.setItem(tenistas);
           adapter.notifyDataSetChanged();

          progressDialog.cancel();
       }


       @Override
       protected Boolean doInBackground(Void... voids) {

           DatabaseJson json = new DatabaseJson();
           json.setIP(IP);

        //   Log.i("idcat",idCategoria.toString());
           //if (spinnerPositionSelected>=0)

           if (idCategoria>0)
                tempValues = json.getTenistaBy(3,idCategoria);

           if (tempValues==null)
               return false;

           return true;
       }
   }



    private class ExclusaoAsyncTask extends AsyncTask<Void, Void, Boolean>
    {

        private ProgressDialog progressDialog = null;


        private Tenista ten=null;


        public Tenista getTenista() {
            return ten;
        }

        public void setTenista(Tenista ten) {
            this.ten = ten;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Excluindo, aguarde...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            DatabaseJson json = new DatabaseJson();
            json.setIP(IP);

            if (ten!=null) {
                json.insereTenista(3, ten);
                if (json.getError()>0)
                    return false;
                else
                {
                    json.insereUsuario(3,ten.getUsuario());
                    if (json.getError()>0)
                        return false;
                }
            }
            else
                return false;


            return true;
        }


        @Override
        protected void onPostExecute(Boolean res) {
            super.onPostExecute(res);


            progressDialog.cancel();

            if (res)
                Toast.makeText(getActivity(), "Tenista excluído com sucesso !!!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getActivity(), "Não foi possível excluir o tenista !!!", Toast.LENGTH_LONG).show();

            TenistasAsyncTask async = new TenistasAsyncTask();
            async.setIdCategoria(tenistas.get(spinnerPositionSelected).getCategoria());
            async.execute((Void) null);
        }
    }


}
