package com.santos.diego.tenisrank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Diego on 15/09/2016.
 */

public class CustomArrayTenistasAdapter extends ArrayAdapter<Tenista>{

    private Context context=null;
    private LayoutInflater inflater = null;
    private ArrayList<Tenista> values;

    public CustomArrayTenistasAdapter(Context context, ArrayList<Tenista> temp) {
        super(context, 0, temp);

        this.context = context;
        values = temp;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    void setItem (ArrayList<Tenista> temp)
    {
        values = temp;
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
            convertView = inflater.inflate(R.layout.listview_tenistas_item,parent,false);
            viewHolder = new ViewHolderCustom();

            viewHolder.Nome = (TextView) convertView.findViewById(R.id.textView_tenistas_nome);
            viewHolder.Email = (TextView) convertView.findViewById(R.id.textView_tenistas_email);
            viewHolder.Telefone = (TextView) convertView.findViewById(R.id.textView_tenistas_telefone);
            viewHolder.Endereco = (TextView) convertView.findViewById(R.id.textView_tenistas_endereco);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolderCustom) convertView.getTag();

        if (item!=null) {
            viewHolder.Nome.setText(item.getUsuario().getNome());
            viewHolder.Email.setText(item.getUsuario().getEmail());
            viewHolder.Telefone.setText(item.getUsuario().getTelefone());
            viewHolder.Endereco.setText(item.getUsuario().getEndereco());

/*
            if (viewHolder.Email.toString().isEmpty())
                viewHolder.Email.setVisibility(View.GONE);
            else
                viewHolder.Email.setVisibility(View.VISIBLE);

            if (viewHolder.Telefone.toString().isEmpty())
                viewHolder.Telefone.setVisibility(View.GONE);
            else
                viewHolder.Telefone.setVisibility(View.VISIBLE);


            if (viewHolder.Endereco.toString().isEmpty())
                viewHolder.Endereco.setVisibility(View.GONE);
            else
                viewHolder.Endereco.setVisibility(View.VISIBLE);


            if (viewHolder.CadastroValido.toString().isEmpty())
                viewHolder.CadastroValido.setVisibility(View.GONE);
            else
                viewHolder.CadastroValido.setVisibility(View.VISIBLE);
*/

        }

        //  TextView tv_posicao = (TextView) rowView.findViewById(R.id.textView_ranking_item_posicao);
        // TextView tv_nome = (TextView) rowView.findViewById(R.id.textView_ranking_item_nome);

        //  String s = values[position];

        return convertView;
    }


    private static class ViewHolderCustom
    {

        TextView Nome;
        TextView Email;
        TextView Telefone;
        TextView Endereco;
        TextView CadastroValido;

    }

}
