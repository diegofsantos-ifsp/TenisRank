package com.santos.diego.tenisrank;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Diego on 10/03/2016.
 */
public class Tenista {
    public Integer getIdTenista() {
        return idTenista;
    }

    public void setIdTenista(Integer idTenista) {
        this.idTenista = idTenista;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getCategoria() {
        return Categoria;
    }

    public void setCategoria(Integer categoria) {
        Categoria = categoria;
    }

    public Integer getPosicaoAtualRanking() {
        return posicaoAtualRanking;
    }

    public void setPosicaoAtualRanking(Integer posicaoAtualRanking) {
        this.posicaoAtualRanking = posicaoAtualRanking;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    //verifica, de acordo com as regras, se o tenista pode jogar
    //caso o tenista tenha um desafio marcado ele não poderá jogar ou aceitar novos desafios, por exemplo
    public Boolean getTemJogoMarcado() {
        return temJogoMarcado;
    }

    public void setTemJogoMarcado(Boolean temJogoMarcado) {
        this.temJogoMarcado = temJogoMarcado;
    }



    private Integer idTenista=-1;
    private Integer idUsuario=-1;
    private Integer Categoria=-1;
    private Integer posicaoAtualRanking=-1;
    private Usuario usuario;



    private Boolean temJogoMarcado=false;


    public String toJson()
    {


        JSONObject json = new JSONObject();

        try{

            json.put("idUsuarios",this.getUsuario().getId());
            json.put("Nome",this.getUsuario().getNome());
            json.put("Endereco",this.getUsuario().getEndereco());
            json.put("Telefone",this.getUsuario().getTelefone());
            json.put("Email",this.getUsuario().getEmail());
            json.put("NomeUsuario",this.getUsuario().getNomeusuario());
            json.put("Senha",this.getUsuario().getSenha());
            json.put("DataUltimoAcesso",this.getUsuario().getDataultimoacesso());
            json.put("HoraUltimoAcesso",this.getUsuario().getHoraultimoacesso());
            json.put("CadastroValido",this.getUsuario().isCadastrovalido());

            Log.i("JSONSTRING_TENISTA",json.toString());
            return json.toString();



        }catch (JSONException e)
        {
            return null;
        }


    }

}
