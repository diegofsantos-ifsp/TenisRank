package com.santos.diego.tenisrank;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Created by Diego on 14/08/2016.
 */

public class CustomDialogDesafioMarcado extends DialogFragment {

    private Button button_cancel;
    private Button button_ok;
    private ImageButton buttonData;
    private ImageButton buttonTime;

    private int idTenistaDesafiado;
    private int idTenistaDesafiador;

    private EditText dataEdit;
    private EditText horaEdit;
    private EditText descricao;

    private OnCustomDialogDesafioInteractionListener mListener;


    public static CustomDialogDesafioMarcado newInstance(Integer param1, Integer param2, Integer param3, String param4, String param5) {
        CustomDialogDesafioMarcado fragment = new CustomDialogDesafioMarcado();
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
        if (context instanceof OnCustomDialogDesafioInteractionListener) {
            mListener = (OnCustomDialogDesafioInteractionListener) context;
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

    public interface OnCustomDialogDesafioInteractionListener {


        void onCustomDialogFragmentInteraction(String Data, String Hora, String Descricao, int idTenistaDesafiador, int idTenistaDesafiado);
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.customdialog_desafio, container, false);
        getDialog().setTitle("Marcar Desafio");

        button_cancel = (Button) rootView.findViewById(R.id.button_cancel);
        button_ok = (Button) rootView.findViewById(R.id.button_ok);
        dataEdit = (EditText) rootView.findViewById(R.id.editText_datadesafiomarcado);
        horaEdit = (EditText) rootView.findViewById(R.id.editText_hora_desafio_marcado);
        buttonData = (ImageButton) rootView.findViewById(R.id.imageButton_calendar);
        buttonTime = (ImageButton) rootView.findViewById(R.id.imageButton_time);
        descricao = (EditText) rootView.findViewById(R.id.editText_descricao);

        final String date;
        final String time;

        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


        buttonData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // arg1 = year
                        // arg2 = month
                        // arg3 = day
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH,arg3);
                        calendar.set(Calendar.MONTH,arg2);
                        calendar.set(Calendar.YEAR,arg1);
                        String temp = dateFormat.format(calendar.getTime());


                        dataEdit.setText(temp);

                    }
                };
                Calendar caltemp = Calendar.getInstance();

                DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), myDateListener, caltemp.get(Calendar.YEAR), caltemp.get(Calendar.MONTH), caltemp.get(Calendar.DAY_OF_MONTH));
                pickerDialog.show();
            }
        });



        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener(){

                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY,i);
                        calendar.set(Calendar.MINUTE,i1);
                        String temp = timeFormat.format(calendar.getTime());

                        horaEdit.setText(temp);
                    }
                };

                Calendar caltemp = Calendar.getInstance();

                TimePickerDialog timerDialog = new TimePickerDialog(getActivity(),myTimeListener,caltemp.get(Calendar.HOUR),caltemp.get(Calendar.MINUTE),true);
                timerDialog.show();

            }
        });


        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {

                        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date date = inputFormat.parse(dataEdit.getText().toString());
                            String result = outputFormat.format(date);
                            mListener.onCustomDialogFragmentInteraction(result,horaEdit.getText().toString(),descricao.getText().toString(),idTenistaDesafiador,idTenistaDesafiado);
                        }catch (java.text.ParseException e)
                        {

                        }

                        getDialog().dismiss();
                      }
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return rootView;
    }
    /*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);




        return null;
    }*/
}
