package com.santos.diego.tenisrank;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Diego on 14/08/2016.
 */

public class CustomDialogMarcarResultado extends DialogFragment {

    private Button button_cancel;
    private Button button_ok;

    private TabHost tabHost = null;

    private NumberPicker pickerDesafiadorSet1 = null;
    private NumberPicker pickerDesafiadoSet1 = null;
    private NumberPicker pickerDesafiadorSet2 = null;
    private NumberPicker pickerDesafiadoSet2 = null;
    private NumberPicker pickerDesafiadorSet3 = null;
    private NumberPicker pickerDesafiadoSet3 = null;


    private ToggleButton tButtonDesafiador = null;
    private ToggleButton tButtonDesafiado = null;


    private int idTenistaDesafiado = -1;
    private int idTenistaDesafiador = -1;


    private int idDesafio = -1;

    private EditText dataEdit;
    private EditText horaEdit;
    private EditText descricao;

    private TextView nomeDesafiador;
    private TextView nomeDesafiado;


    private Desafio desafio=null;

    private OnCustomDialogMarcarResultadoListener mListener;


    public static CustomDialogMarcarResultado newInstance(Integer param1, Integer param2, Integer param3, String param4, String param5) {
        CustomDialogMarcarResultado fragment = new CustomDialogMarcarResultado();
        Bundle args = new Bundle();
        args.putInt("idUsuario", param1);
        args.putInt("idTenistaDesafiador", param2);
        args.putInt("idTenistaDesafiado",param3);
        args.putString("Nome", param4);
        args.putString("Email", param5);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCustomDialogMarcarResultadoListener) {
            mListener = (OnCustomDialogMarcarResultadoListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCustomDialogDesafioInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCustomDialogMarcarResultadoListener {


        void onCustomDialogMarcarResultadoOKClicked(Desafio d);
    }

    //public void onButtonPressed(String Data, String Hora) {
      //  if (mListener != null) {
      //      mListener.onCustomDialogFragmentInteraction(Data,Hora);
      //  }
   // }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idTenistaDesafiador = getArguments().getInt("idTenistaDesafiador");
            idTenistaDesafiado = getArguments().getInt("idTenistaDesafiado");
            //  idTenista = getArguments().getInt("idTenista");
            // nome = getArguments().getString("Nome");
            // email = getArguments().getString("Email");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.custom_dialog_marcar_resultado, container, false);
        getDialog().setTitle("Resultados");

        button_cancel = (Button) rootView.findViewById(R.id.button_marcar_cancel);
        button_ok = (Button) rootView.findViewById(R.id.button_marcar_ok);

        tButtonDesafiador = (ToggleButton) rootView.findViewById(R.id.toggleButton_wo_desafiador);
        tButtonDesafiado = (ToggleButton) rootView.findViewById(R.id.toggleButton_wo_desafiado);

        nomeDesafiador = (TextView) rootView.findViewById(R.id.textView_marcar_desafiador_set1);
        nomeDesafiado = (TextView) rootView.findViewById(R.id.textView_marcar_desafiado_set1);


        pickerDesafiadorSet1 = (NumberPicker) rootView.findViewById(R.id.numberPicker_desafiador_set1);
        pickerDesafiadoSet1 = (NumberPicker) rootView.findViewById(R.id.numberPicker_desafiado_set1);
        pickerDesafiadorSet2 = (NumberPicker) rootView.findViewById(R.id.numberPicker_desafiador_set2);
        pickerDesafiadoSet2 = (NumberPicker) rootView.findViewById(R.id.numberPicker_desafiado_set2);
        pickerDesafiadorSet3 = (NumberPicker) rootView.findViewById(R.id.numberPicker_desafiador_set3);
        pickerDesafiadoSet3 = (NumberPicker) rootView.findViewById(R.id.numberPicker_desafiado_set3);


        String[] valores = new String[]{"0","1","2","3","4","5","6","7"};
        String[] valoresSuperTieBreak = new String[]{"0","1","2","3","4","5","6","7","8","9","10"};

        pickerDesafiadorSet1.setDisplayedValues(valores);
        pickerDesafiadorSet1.setMinValue(0);
        pickerDesafiadorSet1.setMaxValue(valores.length-1);

        pickerDesafiadoSet1.setDisplayedValues(valores);
        pickerDesafiadoSet1.setMinValue(0);
        pickerDesafiadoSet1.setMaxValue(valores.length-1);

        pickerDesafiadorSet2.setDisplayedValues(valores);
        pickerDesafiadorSet2.setMinValue(0);
        pickerDesafiadorSet2.setMaxValue(valores.length-1);

        pickerDesafiadoSet2.setDisplayedValues(valores);
        pickerDesafiadoSet2.setMinValue(0);
        pickerDesafiadoSet2.setMaxValue(valores.length-1);

        pickerDesafiadorSet3.setDisplayedValues(valores);
        pickerDesafiadorSet3.setMinValue(0);
        pickerDesafiadorSet3.setMaxValue(valores.length-1);

        pickerDesafiadoSet3.setDisplayedValues(valores);
        pickerDesafiadoSet3.setMinValue(0);
        pickerDesafiadoSet3.setMaxValue(valores.length-1);


        tabHost = (TabHost) rootView.findViewById(R.id.tabhost);

        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("um");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Set 1");



        TabHost.TabSpec tab2 = tabHost.newTabSpec("dois");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Set 2");


        TabHost.TabSpec tab3 = tabHost.newTabSpec("tres");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("Set 3");


        tabHost.setCurrentTab(0);
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);



        //atualiza os nomes dos jogadores (Desafiador e Desafiado) no Dialog
        if (desafio!=null)
        {

            String desafiadorTemp = desafio.getTenistaDesafiador().getUsuario().getNome();



            int posicaoTemp = desafiadorTemp.indexOf(" ");

            if (posicaoTemp>0)
                desafiadorTemp = desafiadorTemp.substring(0,posicaoTemp);

            nomeDesafiador.setText(desafiadorTemp);

            String desafiadoTemp = desafio.getTenistaDesafiado().getUsuario().getNome();

            posicaoTemp = 0;
            posicaoTemp = desafiadoTemp.indexOf(" ");

            if (posicaoTemp>0)
                desafiadoTemp = desafiadoTemp.substring(0,posicaoTemp);

                nomeDesafiado.setText(desafiadoTemp);


        }

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    int vdesafiador = 0;
                    int vdesafiado = 0;
                    boolean erro = false;
                    int wo=0;

                    if (desafio!=null)
                    {

                        if (tButtonDesafiador.isChecked())
                            wo = 2;
                        else if (tButtonDesafiado.isChecked())
                            wo = 1;


                        //se não teve WO
                        if (wo==0) {

                            //  Log.i("IDDESAFIOSDIALOG",String.valueOf(desafio.getIdDesafio()));
                            if (pickerDesafiadorSet1.getValue() != 0 && pickerDesafiadoSet1.getValue() != 0) {
                                if (pickerDesafiadorSet1.getValue() > pickerDesafiadoSet1.getValue())
                                    vdesafiador++;
                                else
                                    vdesafiado++;

                                desafio.setResultTenistaDesafiador1((short) pickerDesafiadorSet1.getValue());
                                desafio.setResultTenistaDesafiado1((short) pickerDesafiadoSet1.getValue());

                            }

                            if (pickerDesafiadorSet2.getValue() != 0 && pickerDesafiadoSet2.getValue() != 0) {
                                if (pickerDesafiadorSet2.getValue() > pickerDesafiadoSet2.getValue())
                                    vdesafiador++;
                                else
                                    vdesafiado++;

                                desafio.setResultTenistaDesafiador2((short) pickerDesafiadorSet2.getValue());
                                desafio.setResultTenistaDesafiado2((short) pickerDesafiadoSet2.getValue());


                            }

                            if (pickerDesafiadorSet3.getValue() != 0 && pickerDesafiadoSet3.getValue() != 0) {
                                if (pickerDesafiadorSet3.getValue() > pickerDesafiadoSet3.getValue())
                                    vdesafiador++;
                                else
                                    vdesafiado++;

                                desafio.setResultTenistaDesafiador3((short) pickerDesafiadorSet3.getValue());
                                desafio.setResultTenistaDesafiado3((short) pickerDesafiadoSet3.getValue());


                            }


                            //verifica quem ganhou:
                            if (vdesafiador > vdesafiado)
                                desafio.setGanhador(1);
                            else
                                desafio.setGanhador(2);
                        }//se wo não for =0 então alguém ganhou por WO
                        else
                            desafio.setGanhador(wo);

                        desafio.setWO((short)wo);

                        desafio.setJogado(1);

                        mListener.onCustomDialogMarcarResultadoOKClicked(desafio);
                        getDialog().dismiss();

                    }
                }
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });



        tButtonDesafiador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tButtonDesafiador.isChecked()) {
                    if (tButtonDesafiado.isChecked())
                        tButtonDesafiado.setChecked(false);
                    tabHost.setVisibility(View.GONE);
                }
                else
                    tabHost.setVisibility(View.VISIBLE);

            }
        });

        tButtonDesafiado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tButtonDesafiado.isChecked()) {
                    if (tButtonDesafiador.isChecked())
                        tButtonDesafiador.setChecked(false);
                    tabHost.setVisibility(View.GONE);
                }
                else
                    tabHost.setVisibility(View.VISIBLE);
            }
        });

        return rootView;
    }


    public int getIdTenistaDesafiado() {
        return idTenistaDesafiado;
    }

    public void setIdTenistaDesafiado(int idTenistaDesafiado) {
        this.idTenistaDesafiado = idTenistaDesafiado;
    }

    public int getIdTenistaDesafiador() {
        return idTenistaDesafiador;
    }

    public void setIdTenistaDesafiador(int idTenistaDesafiador) {
        this.idTenistaDesafiador = idTenistaDesafiador;
    }

    public int getIdDesafio() {
        return idDesafio;
    }

    public void setIdDesafio(int idDesafio) {
        this.idDesafio = idDesafio;
    }


    public Desafio getDesafio() {
        return desafio;
    }

    public void setDesafio(Desafio desafio) {
        this.desafio = desafio;


    }


    /*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);




        return null;
    }*/
}
