package com.santos.diego.tenisrank;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Diego on 07/08/2016.
 */

public class CustomArrayJogosAdapter extends ArrayAdapter<Desafio> {

    private Context context = null;
    private ArrayList<Desafio> values;
    private LayoutInflater inflater = null;

    public CustomArrayJogosAdapter (Context context, ArrayList<Desafio> temp)
    {
        super(context, 0, temp);
        this.context = context;
        values = temp;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    void setItem (ArrayList<Desafio> temp)
    {
        values = temp;
    }

    @Override
    public Desafio getItem(int position) {
        //return super.getItem(position);
        if (values!=null)
            return values.get(position);
        return null;

    }

    @Override
    public int getCount() {
        if (values!=null)
            return values.size();

        return 0;
    }


    @Override
    public View getView (int position, View convertView, ViewGroup parent)
    {
        final Desafio item = values.get(position);
        CustomArrayJogosAdapter.ViewHolderCustom viewHolder;

        if (convertView==null)
        {
            convertView = inflater.inflate(R.layout.listview_jogos_item,parent,false);
            viewHolder = new CustomArrayJogosAdapter.ViewHolderCustom();
            viewHolder.Desafiado = (TextView) convertView.findViewById(R.id.textView_JogosDesafiado);
            viewHolder.Desafiador = (TextView) convertView.findViewById(R.id.textView_JogosDesafiador);
            viewHolder.Data = (TextView) convertView.findViewById(R.id.textView_JogosData);
            viewHolder.Hora = (TextView) convertView.findViewById(R.id.textView_JogosHora);
            viewHolder.imgChampionDesafiado = (ImageView) convertView.findViewById(R.id.imageViewCampeao_Desafiado);
            viewHolder.imgChampionDesafiador = (ImageView) convertView.findViewById(R.id.imageViewCampeao_Desafiador);

            viewHolder.set1 = (TextView) convertView.findViewById(R.id.ntextview_set1);
            viewHolder.set2 = (TextView) convertView.findViewById(R.id.ntextview_set2);
            viewHolder.set3 = (TextView) convertView.findViewById(R.id.ntextview_set3);

            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (CustomArrayJogosAdapter.ViewHolderCustom) convertView.getTag();


        if (item!=null) {
            String strDesafiado = item.getTenistaDesafiado().getUsuario().getNome();
            String strDesafiador = item.getTenistaDesafiador().getUsuario().getNome();

            strDesafiado = strDesafiado.split(" ")[0];
            strDesafiador = strDesafiador.split(" ")[0];


            if (item.getGanhador()==1) {
                viewHolder.imgChampionDesafiador.setVisibility(View.VISIBLE);
                viewHolder.imgChampionDesafiado.setVisibility(View.GONE);
               // viewHolder.Desafiador.setTextColor(Color.parseColor("#DF7401"));
                //strDesafiador = strDesafiador + " (v)";
            }
            else if (item.getGanhador()==2) {
                viewHolder.imgChampionDesafiado.setVisibility(View.VISIBLE);
                viewHolder.imgChampionDesafiador.setVisibility(View.GONE);
                //viewHolder.Desafiado.setTextColor(Color.parseColor("#DF7401"));
                //strDesafiado = strDesafiado + " (v)";
            }
            else
            {
                viewHolder.imgChampionDesafiado.setVisibility(View.GONE);
                viewHolder.imgChampionDesafiador.setVisibility(View.GONE);
                //viewHolder.Desafiador.setTextColor(Color.BLACK);
                //viewHolder.Desafiado.setTextColor(Color.BLACK);
            }

            viewHolder.Desafiado.setText(strDesafiado);
            viewHolder.Desafiador.setText(strDesafiador);
            viewHolder.Data.setText(item.getData());
            viewHolder.Hora.setText(item.getHora());


            //atualiza os resultados
            String set1=null;
            String set2=null;
            String set3=null;

            if (item.isJogado()!=0)
            {
                StringBuilder temp = new StringBuilder();

                //set 1
                temp.append(item.getResultTenistaDesafiador1());
                temp.append(" x ");
                temp.append(item.getResultTenistaDesafiado1());

                if (item.getTieBreakDesafiador1()!=-1)
                {
                    temp.append(" (");
                    temp.append(item.getTieBreakDesafiador1());
                    temp.append(" | ");
                    temp.append(item.getTieBreakDesafiado1());
                    temp.append(")");
                }

                set1 = temp.toString();

                temp.delete(0,temp.length());

                //set 2
                if (item.getResultTenistaDesafiador2()!=-1)
                {
                    temp.append(item.getResultTenistaDesafiador2());
                    temp.append(" x ");
                    temp.append(item.getResultTenistaDesafiado2());

                    if (item.getTieBreakDesafiador2()!=-1)
                    {
                        temp.append(" (");
                        temp.append(item.getTieBreakDesafiador2());
                        temp.append(" | ");
                        temp.append(item.getTieBreakDesafiado2());
                        temp.append(")");
                    }

                    set2 = temp.toString();

                    temp.delete(0,temp.length());

                }

                //set 3
                if (item.getResultTenistaDesafiador3()!=-1)
                {
                    temp.append(item.getResultTenistaDesafiador3());
                    temp.append(" x ");
                    temp.append(item.getResultTenistaDesafiado3());

                    if (item.getTieBreakDesafiador3()!=-1)
                    {
                        temp.append(" (");
                        temp.append(item.getTieBreakDesafiador3());
                        temp.append(" | ");
                        temp.append(item.getTieBreakDesafiado3());
                        temp.append(")");
                    }

                    set3 = temp.toString();

                    temp.delete(0,temp.length());

                }

            }


            if (set1==null)
                viewHolder.set1.setVisibility(View.GONE);
            else
            {
                viewHolder.set1.setText(set1);
                viewHolder.set1.setVisibility(View.VISIBLE);
            }

            if (set2==null)
                viewHolder.set2.setVisibility(View.GONE);
            else
            {
                viewHolder.set2.setText(set2);
                viewHolder.set2.setVisibility(View.VISIBLE);
            }

            if (set3==null)
                viewHolder.set3.setVisibility(View.GONE);
            else
            {
                viewHolder.set3.setText(set3);
                viewHolder.set3.setVisibility(View.VISIBLE);
            }

        }

        //  TextView tv_posicao = (TextView) rowView.findViewById(R.id.textView_ranking_item_posicao);
        // TextView tv_nome = (TextView) rowView.findViewById(R.id.textView_ranking_item_nome);

        //  String s = values[position];

        return convertView;
    }

    private static class ViewHolderCustom
    {
        TextView Desafiado;
        TextView Desafiador;
        TextView Data;
        TextView Hora;
        ImageView imgChampionDesafiado;
        ImageView imgChampionDesafiador;
        TextView set1;
        TextView set2;
        TextView set3;
    }

}

