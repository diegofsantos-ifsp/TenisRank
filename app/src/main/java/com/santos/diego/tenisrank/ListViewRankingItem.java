package com.santos.diego.tenisrank;

/**
 * Created by Diego on 10/03/2016.
 */
public class ListViewRankingItem {

    public ListViewRankingItem()
    {
        posicao="";
        nome="";

    }

    public ListViewRankingItem(String p, String n)
    {
        nome = n;
        posicao = p;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    private String posicao;
    private String nome;


}
