package com.santos.diego.tenisrank;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Diego on 15/09/2016.
 */

public class CustomArrayCategoriasAdapter extends ArrayAdapter<Categoria>{

    private Context context=null;
    private LayoutInflater inflater = null;
    private ArrayList<Categoria> values;

    public CustomArrayCategoriasAdapter(Context context, ArrayList<Categoria> cat) {
        super(context, 0, cat);

        this.context = context;
        values = cat;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    void setItem (ArrayList<Categoria> temp)
    {
        values = temp;
    }

    @Override
    public Categoria getItem(int position) {
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
        final Categoria item = values.get(position);
        ViewHolderCustom viewHolder;

        if (convertView==null)
        {
            convertView = inflater.inflate(R.layout.listview_categorias_item,parent,false);
            viewHolder = new ViewHolderCustom();
            viewHolder.Nome = (TextView) convertView.findViewById(R.id.textView_nomeCategoria_item);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolderCustom) convertView.getTag();

        if (item!=null) {
            viewHolder.Nome.setText(item.getNome());



        }

        //  TextView tv_posicao = (TextView) rowView.findViewById(R.id.textView_ranking_item_posicao);
        // TextView tv_nome = (TextView) rowView.findViewById(R.id.textView_ranking_item_nome);

        //  String s = values[position];

        return convertView;
    }


    private static class ViewHolderCustom
    {
        TextView Nome;

    }

}
