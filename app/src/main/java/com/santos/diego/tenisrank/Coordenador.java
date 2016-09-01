package com.santos.diego.tenisrank;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Diego on 10/03/2016.
 */
public class Coordenador {
    public int getIdCoordenadores() {
        return idCoordenadores;
    }

    public void setIdCoordenadores(int idCoordenadores) {
        this.idCoordenadores = idCoordenadores;
    }

    public int getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(int idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    private int idCoordenadores=-1;
    private int idUsuarios=-1;


    public String toJson() {


        JSONObject json = new JSONObject();

        try {

            json.put("idCoordenador",this.getIdCoordenadores());
            json.put("idUsuario",this.getIdUsuarios());


            Log.i("JSONSTRING_COORDENADOR", json.toString());
            return json.toString();


        } catch (JSONException e) {
            return null;
        }
    }
}
