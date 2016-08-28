package com.santos.diego.tenisrank;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentJogos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentJogos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentJogos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter_spinner = null;
    private ListView lv = null;
    private TextView emptyView = null;
    private CheckBox chkbox = null;

    private ArrayList<Desafio> desafios;

    private CustomArrayJogosAdapter adapter = null;

    private OnFragmentInteractionListener mListener;

    private String nome = null;
    private String email = null;
    private Integer idUsuario = null;
    private Integer idTenista = null;
    private Integer idCoordenador = null;

    private String IP = null;

    public FragmentJogos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentJogos.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentJogos newInstance(Integer param1, Integer param2, String param3, String param4) {
        FragmentJogos fragment = new FragmentJogos();
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
            idUsuario = getArguments().getInt("idUsuario");
            idTenista = getArguments().getInt("idTenista");
            nome = getArguments().getString("Nome");
            email = getArguments().getString("Email");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_jogos, container, false);


        chkbox = (CheckBox) view.findViewById(R.id.checkBoxMeusJogos);
        spinner = (Spinner) view.findViewById(R.id.spinnerJogos);

        adapter_spinner = ArrayAdapter.createFromResource(getActivity(),R.array.jogos_spinnervalues,R.layout.spinner_item);
        adapter_spinner.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter_spinner);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        IP = pref.getString("ip","0");


        lv = (ListView) view.findViewById(R.id.listViewJogos);

        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_jogos);


        adapter = new CustomArrayJogosAdapter(getActivity(),desafios);

        lv.setAdapter(adapter);


        chkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Integer pos = spinner.getSelectedItemPosition();


                DesafiosAsyncTask task = new DesafiosAsyncTask();

                task.setJogos(pos);
                task.setMeusjogos(chkbox.isChecked());
                //RankingAsyncTask r = new RankingAsyncTask(0, categorias.get(pos).getIdCategoria());
                task.execute((Void) null);

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Integer pos = spinner.getSelectedItemPosition();


                DesafiosAsyncTask task = new DesafiosAsyncTask();

                task.setJogos(pos);
                task.setMeusjogos(chkbox.isChecked());
                //RankingAsyncTask r = new RankingAsyncTask(0, categorias.get(pos).getIdCategoria());
                task.execute((Void) null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        //atualiza a listview caso o usuário deslize na tela (dentro da listview)
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               // FragmentRanking.RankingAsyncTask rasync = new FragmentRanking.RankingAsyncTask(0,categorias.get(spinner.getSelectedItemPosition()).getIdCategoria());
                //rasync.execute((Void) null);
                Integer pos = spinner.getSelectedItemPosition();


                DesafiosAsyncTask task = new DesafiosAsyncTask();

                task.setJogos(pos);
                task.setMeusjogos(chkbox.isChecked());
                //RankingAsyncTask r = new RankingAsyncTask(0, categorias.get(pos).getIdCategoria());
                task.execute((Void) null);

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


        //ao clicar um item apresentar o dialog do resultado
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // FragmentManager fm = getFragmentManager();
                //dialog_showresults dialog = dialog_showresults.newInstance("t","t");
                //dialog.show(fm,"dialogresults");
                //mostra os resultados dos jogados já jogados
                if (desafios.get(i).isJogado()!=0) {
                    LinearLayout ln = (LinearLayout) view.findViewById(R.id.linearlayout_resultados);
                    if (ln.getVisibility() == View.VISIBLE)
                        ln.setVisibility(View.GONE);
                    else
                        ln.setVisibility(View.VISIBLE);
                }

            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private class DesafiosAsyncTask extends AsyncTask <Void, Integer, ArrayList<Desafio>>
    {

        private Desafio desafioTemp;
        private ArrayList<Desafio> desafiosTemp;
        private boolean meusjogos = true;


        //Se jogos = 0 -> proximos jogos
        //Se jogos = 1 -> jogos anteriores
        //Se jogos = 2 -> todos os jogos
        private int jogos = 0;



        public boolean isMeusjogos() {
            return meusjogos;
        }

        public void setMeusjogos(boolean meusjogos) {
            this.meusjogos = meusjogos;
        }

        public int getJogos() {
            return jogos;
        }

        public void setJogos(int jogos) {
            this.jogos = jogos;
        }


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(ArrayList<Desafio> temp)
        {
            super.onPostExecute(temp);

            if (temp!=null)
            {
                desafios = temp;
                adapter.setItem(temp);

            }
            else {
                if (desafios!=null)
                    desafios.clear();
                desafios = null;

            }
            adapter.notifyDataSetChanged();
        }

        @Override
        protected ArrayList<Desafio> doInBackground(Void... values) {

            DatabaseJson json = new DatabaseJson();
            json.setIP(IP);

            if (meusjogos)
                desafiosTemp = json.getJogosByTenista(jogos,idTenista,1);
            else {

                desafiosTemp = json.getJogos(jogos, 1);
            }




            return desafiosTemp;
        }
    }


}


