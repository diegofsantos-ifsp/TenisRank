package com.santos.diego.tenisrank;

import java.util.Date;

/**
 * Created by Diego on 07/03/2016.
 */
public class Usuario {
    private int id;

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

    public Date getDataultimoacesso() {
        return dataultimoacesso;
    }

    public void setDataultimoacesso(Date dataultimoacesso) {
        this.dataultimoacesso = dataultimoacesso;
    }

    public String getHoraultimoacesso() {
        return horaultimoacesso;
    }

    public void setHoraultimoacesso(String horaultimoacesso) {
        this.horaultimoacesso = horaultimoacesso;
    }

    public boolean isCadastrovalido() {
        return cadastrovalido;
    }

    public void setCadastrovalido(boolean cadastrovalido) {
        this.cadastrovalido = cadastrovalido;
    }

    private String nome;
    private String endereco;
    private String telefone;
    private String email;
    private String nomeusuario;
    private String senha;
    private Date dataultimoacesso;
    private String horaultimoacesso;
    private boolean cadastrovalido;


}
