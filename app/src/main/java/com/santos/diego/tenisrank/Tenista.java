package com.santos.diego.tenisrank;

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



    private Integer idTenista;
    private Integer idUsuario;
    private Integer Categoria;
    private Integer posicaoAtualRanking;
    private Usuario usuario;


    private Boolean temJogoMarcado=false;

}
