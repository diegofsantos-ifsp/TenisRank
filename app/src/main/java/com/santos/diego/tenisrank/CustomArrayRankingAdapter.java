package com.santos.diego.tenisrank;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
 * Created by Diego on 11/03/2016.
 */
public class CustomArrayRankingAdapter extends ArrayAdapter<Tenista>
{
    private Context context=null;
    private ArrayList<Tenista> values=null;
    private Integer idUsuario;
    private Integer posicao=null;
    private String nome;
    private String email;

    //private final String[] values=null;
    private LayoutInflater inflater = null;


    public CustomArrayRankingAdapter (Context context, ArrayList<Tenista> temp)
    {
        super(context, 0, temp);
        this.context = context;
        values = temp;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    void setPosicaoUsuario (Integer p)
    {
        posicao = p;
    }

    void setItem (ArrayList<Tenista> temp)
    {
        values = temp;
    }

    void setNome (String n)
    {
        nome = n;
    }

    void setEmail (String e)
    {
        email = e;
    }

    void setidUsuario(Integer id)
    {
        idUsuario = id;
    }

    @Override
    public Tenista getItem(int position) {
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
        final Tenista item = values.get(position);
        ViewHolderCustom viewHolder;

        if (convertView==null)
        {
            convertView = inflater.inflate(R.layout.listview_ranking_item,parent,false);
            viewHolder = new ViewHolderCustom();
            viewHolder.Posicao = (TextView) convertView.findViewById(R.id.textView_ranking_item_posicao);
            viewHolder.Nome = (TextView) convertView.findViewById(R.id.textView_ranking_item_nome);
            viewHolder.imgView = (ImageButton) convertView.findViewById(R.id.imageButton);
            viewHolder.imgButton2 = (ImageButton) convertView.findViewById(R.id.imageButton2);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolderCustom) convertView.getTag();

        if (item!=null) {
            viewHolder.Posicao.setText(item.getPosicaoAtualRanking().toString());
            viewHolder.Nome.setText(item.getUsuario().getNome());

            if (email.compareToIgnoreCase(item.getUsuario().getEmail())==0) {
                viewHolder.Nome.setTextColor(Color.WHITE);
                viewHolder.Posicao.setTextColor(Color.WHITE);
                convertView.setBackgroundColor(Color.parseColor("#d97d10"));

                //convertView.setEnabled(false);

            }

            viewHolder.imgView.setVisibility(View.GONE);
            viewHolder.imgButton2.setVisibility(View.GONE);

            if (posicao!=null) {
                int diferenca = (posicao-1) - position;
                if ( (diferenca >= 1 && diferenca<=3) && (!item.getTemJogoMarcado())) //3 posicoes acima que pode desafiar (mostrar botão do telefone e desafio)
                {
                  //  convertView.setBackgroundColor(Color.parseColor("#FFFFE7B3"));
                    //viewHolder.imgView.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.tennis_icon_32));
                    viewHolder.imgView.setVisibility(View.VISIBLE);
                    viewHolder.imgView.setFocusable(false);
                    viewHolder.imgView.setFocusableInTouchMode(false);

                    viewHolder.imgView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                            builder1.setMessage("Você já conversou com o jogador e possui uma data definida para o jogo?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Sim",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
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
                    });


                    viewHolder.imgButton2.setVisibility(View.VISIBLE);
                    viewHolder.imgButton2.setFocusable(false);
                    viewHolder.imgButton2.setFocusableInTouchMode(false);
                    viewHolder.imgButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"+item.getUsuario().getTelefone()));
                            try {
                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putBoolean("ligou",true);
                                editor.putString("nome_jogador_desafiado",item.getUsuario().getNome());
                                editor.commit();

                                context.startActivity(intent);


                            }catch (SecurityException e){
                                Log.i("Excecao","ENTROU");
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

        }

      //  TextView tv_posicao = (TextView) rowView.findViewById(R.id.textView_ranking_item_posicao);
       // TextView tv_nome = (TextView) rowView.findViewById(R.id.textView_ranking_item_nome);

      //  String s = values[position];

        return convertView;
    }


    private static class ViewHolderCustom
    {
        TextView Posicao;
        TextView Nome;
        ImageButton imgView;
        ImageButton imgButton2;
    }

}

