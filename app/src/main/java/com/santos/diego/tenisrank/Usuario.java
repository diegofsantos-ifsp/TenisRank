package com.santos.diego.tenisrank;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Diego on 07/03/2016.
 */
public class Usuario {
    private int id=-1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeusuario() {
        return nomeusuario;
    }

    public void setNomeusuario(String nomeusuario) {
        this.nomeusuario = nomeusuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDataultimoacesso() {
        return dataultimoacesso;
    }

    public void setDataultimoacesso(String dataultimoacesso) {
        this.dataultimoacesso = dataultimoacesso;
    }

    public String getHoraultimoacesso() {
        return horaultimoacesso;
    }

    public void setHoraultimoacesso(String horaultimoacesso) {
        this.horaultimoacesso = horaultimoacesso;
    }

    public int isCadastrovalido() {
        return cadastrovalido;
    }

    public void setCadastrovalido(int cadastrovalido) {
        this.cadastrovalido = cadastrovalido;
    }

    public String toJson()
    {


        JSONObject json = new JSONObject();

        try{
            json.put("idUsuario",this.getId());
            json.put("Nome",this.getNome());
            json.put("Endereco",this.getEndereco());
            json.put("Telefone",this.getTelefone());
            json.put("Email",this.getEmail());
            json.put("NomeUsuario",this.getNomeusuario());
            json.put("Senha",this.getSenha());
            json.put("DataUltimoAcesso",this.getDataultimoacesso());
            json.put("HoraUltimoAcesso",this.getHoraultimoacesso());
            json.put("CadastroValido",this.isCadastrovalido());

            Log.i("JSONSTRING_USUARIO",json.toString());
            return json.toString();



        }catch (JSONException e)
        {
            return null;
        }


    }

    private String nome=" ";
    private String endereco=" ";
    private String telefone=" ";
    private String email=" ";
    private String nomeusuario=" ";
    private String senha=" ";
    private String dataultimoacesso=" ";
    private String horaultimoacesso=" ";
    private int cadastrovalido=0;


}
