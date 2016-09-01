package com.santos.diego.tenisrank;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Diego on 21/03/2016.
 */
public class Categoria {
    private Integer idCategoria=-1;
    private String nome=" ";

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String toJson() {


        JSONObject json = new JSONObject();

        try {

            json.put("idCategoria",this.getIdCategoria());
            json.put("Nome",this.getNome());


            Log.i("JSONSTRING_CATEGORIA", json.toString());
            return json.toString();


        } catch (JSONException e) {
            return null;
        }
    }

}
