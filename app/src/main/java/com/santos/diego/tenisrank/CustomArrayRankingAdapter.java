package com.santos.diego.tenisrank;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
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
import android.widget.Toast;

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
    Regra regra=null;


    private Integer idTenista=null;

    private Boolean isWhatsappInstalled=false;

    private Integer idCategoria = 0;



    public Regra getRegra() {
        return regra;
    }

    public void setRegra(Regra regra) {
        this.regra = regra;
    }

    public Integer getIdTenista() {
        return idTenista;
    }

    public void setIdTenista(Integer idTenista) {
        this.idTenista = idTenista;
    }



    private boolean usuario_pode_marcar_jogo=true;

    //private final String[] values=null;
    private LayoutInflater inflater = null;

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public CustomArrayRankingAdapter (Context context, ArrayList<Tenista> temp)
    {
        super(context, 0, temp);
        this.context = context;
        values = temp;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        PackageManager pm = ((MainActivity)context).getPackageManager();

        try{
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);

            isWhatsappInstalled=true;
        }
        catch (PackageManager.NameNotFoundException ex)
        {
            isWhatsappInstalled=false;
        }

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


    public boolean getUsuario_pode_marcar_jogo() {
        return usuario_pode_marcar_jogo;
    }

    public void setUsuario_pode_marcar_jogo(boolean usuario_pode_marcar_jogo) {
        this.usuario_pode_marcar_jogo = usuario_pode_marcar_jogo;
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
        final Integer posicaoDesafiado = position+1;
        ViewHolderCustom viewHolder;

        if (convertView==null)
        {
            convertView = inflater.inflate(R.layout.listview_ranking_item,parent,false);
            viewHolder = new ViewHolderCustom();
            viewHolder.Posicao = (TextView) convertView.findViewById(R.id.textView_ranking_item_posicao);
            viewHolder.Nome = (TextView) convertView.findViewById(R.id.textView_ranking_item_nome);
            viewHolder.imgView = (ImageButton) convertView.findViewById(R.id.imageButton);
            viewHolder.imgButton2 = (ImageButton) convertView.findViewById(R.id.imageButton2);
            viewHolder.imgEmail = (ImageButton) convertView.findViewById(R.id.imageButton_email);
            viewHolder.imgWhatsapp = (ImageButton) convertView.findViewById(R.id.imageButton_whatsapp);


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
            viewHolder.imgEmail.setVisibility(View.GONE);
            viewHolder.imgWhatsapp.setVisibility(View.GONE);

            if (posicao!=null) {



            if (usuario_pode_marcar_jogo) {

                int diferenca = (posicao - 1) - position;
                if ((diferenca >= 1 && diferenca <= regra.getPosicaoMaximaQPodeDesafiar()) && (!item.getTemJogoMarcado())) //N posicoes acima que pode desafiar (mostrar botão do telefone e desafio)
                {
                    //  convertView.setBackgroundColor(Color.parseColor("#FFFFE7B3"));
                    //viewHolder.imgView.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.tennis_icon_32));
                    viewHolder.imgView.setVisibility(View.VISIBLE);
                    viewHolder.imgView.setFocusable(false);
                    viewHolder.imgView.setFocusableInTouchMode(false);

                    viewHolder.imgView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                         //   Log.i("IDCATEGORIA",idCategoria.toString());
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                            builder1.setMessage("Você já conversou com o jogador e possui uma data definida para o jogo?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Sim",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();

                                            ///////////////////////////////////////////////////////////////////////////////////
                                           CustomDialogDesafioMarcado customDialogDesafio = CustomDialogDesafioMarcado.newInstance(item.getUsuario().getId(),idTenista,item.getIdTenista(),posicaoDesafiado,posicao);
                                            customDialogDesafio.setIdCategoria(idCategoria);
                                            FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                                            //fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();


                                            //FragmentManager fm = ((MainActivity)context).getSupportFragmentManager();
                                            //CustomDialogDesafioMarcado dialogcustom = new CustomDialogDesafioMarcado();
                                            customDialogDesafio.show(fragmentManager, "customdialog");






                                            ///////////////////////////////////////////////////////////////////////////////////
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
                            intent.setData(Uri.parse("tel:" + item.getUsuario().getTelefone()));
                            try {
                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putBoolean("ligou", true);
                                editor.putString("nome_jogador_desafiado", item.getUsuario().getNome());
                               // Log.i("TENISTA", item.getIdTenista().toString());
                                //editor.putInt("idTenistaDesafiado",item.getIdTenista());

                                editor.putInt("idTenistaDesafiado", item.getIdTenista());

                                //enviar as posicoes atuais do ranking de cada tenista
                                //para que seja possível fazer o cálculo de quantas posições cada
                                // tenista ganhará/perderá após o jogo
                                editor.putInt("posicaoTenistaDesafiado",posicaoDesafiado);
                                editor.putInt("posicaoTenistaDesafiador",posicao);
                                editor.putInt("idCategoria",idCategoria);

                            //    Log.i("PosicaoDesafiado",posicaoDesafiado.toString());
                             //   Log.i("PosicaoDesafiador",posicao.toString());

                                editor.commit();

                                context.startActivity(intent);


                            } catch (SecurityException e) {
                                Log.i("Excecao", "ENTROU");
                                e.printStackTrace();
                            }
                        }
                    });


                    viewHolder.imgEmail.setVisibility(View.VISIBLE);
                    viewHolder.imgEmail.setFocusable(false);
                    viewHolder.imgEmail.setFocusableInTouchMode(false);

                    viewHolder.imgEmail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent (Intent.ACTION_SENDTO);
                            intent.setType("message/rfc822");
                            intent.setData(Uri.parse("mailto:"+item.getUsuario().getEmail()));
                            //intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{item.getUsuario().getEmail()});
                            intent.putExtra(Intent.EXTRA_SUBJECT, "TenisRank - Desafio");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            String str;
                            str = "Gostaria de convidá-lo para um desafio de tênis.\n" +
                                    "Posso jogar nos seguintes dias e horários: \n"+
                                    nome+"\n";
                            intent.putExtra(Intent.EXTRA_TEXT   , str);
                            try {
                                ((MainActivity)context).startActivity(Intent.createChooser(intent, "Enviar email..."));
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(((MainActivity)context), "Não existem clientes de email instalados !", Toast.LENGTH_LONG).show();
                            }
                        }

                    });

                    if (isWhatsappInstalled) {


                        viewHolder.imgWhatsapp.setVisibility(View.VISIBLE);
                        viewHolder.imgWhatsapp.setFocusable(false);
                        viewHolder.imgWhatsapp.setFocusableInTouchMode(false);

                        viewHolder.imgWhatsapp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Uri uri = Uri.parse("smsto:" + item.getUsuario().getTelefone());
                                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                                i.setPackage("com.whatsapp");
                                i.setType("text/plain");
                                i.putExtra("sms_body", "Gostaria de convidá-lo para um desafio de tênis. Os dias e horário que eu posso são:\n");
                                try {
                                    ((MainActivity) context).startActivity(Intent.createChooser(i, ""));
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(((MainActivity) context), "O aplicativo Whatsapp não está instalado !", Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }
                }
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
        ImageButton imgEmail;
        ImageButton imgWhatsapp;
    }

}

