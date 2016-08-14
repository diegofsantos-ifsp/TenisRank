package com.santos.diego.tenisrank;

/**
 * Created by Diego on 19/04/2016.
 */
public class Desafio {
    private int idDesafio;
    private Integer idTenistaDesafiado;
    private Integer idTenistaDesafiador;
    private int idQuadra;

    private int ganhador;
    private String Data;
    private String Hora;
    int jogado;
    int AceitoDesafiado;
    int AceitoDesafiador;
    private String Descricao;
    private short ResultTenistaDesafiado1;
    private short ResultTenistaDesafiador1;
    private short tieBreakDesafiado1;
    private short tieBreakDesafiador1;
    private Tenista tenistaDesafiado;
    private Tenista tenistaDesafiador;

    private short ResultTenistaDesafiado2;
    private short ResultTenistaDesafiador2;
    private short tieBreakDesafiado2;
    private short tieBreakDesafiador2;
    private short ResultTenistaDesafiado3;
    private short ResultTenistaDesafiador3;
    private short tieBreakDesafiado3;
    private short tieBreakDesafiador3;
    private short WO;
    int confirmadoCoordenador;
    int confirmadoDesafiado;
    int confirmadoDesafiador;

    public int getGanhador() {
        return ganhador;
    }

    public void setGanhador(int ganhador) {
        this.ganhador = ganhador;
    }



    public int getIdDesafio() {
        return idDesafio;
    }

    public void setIdDesafio(int idDesafio) {
        this.idDesafio = idDesafio;
    }

    public Integer getIdTenistaDesafiado() {
        return idTenistaDesafiado;
    }

    public void setIdTenistaDesafiado(Integer idTenistaDesafiado) {
        this.idTenistaDesafiado = idTenistaDesafiado;
    }

    public Integer getIdTenistaDesafiador() {
        return idTenistaDesafiador;
    }

    public void setIdTenistaDesafiador(Integer idTenistaDesafiador) {
        this.idTenistaDesafiador = idTenistaDesafiador;
    }

    public int getIdQuadra() {
        return idQuadra;
    }

    public void setIdQuadra(int idQuadra) {
        this.idQuadra = idQuadra;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public int isJogado() {
        return jogado;
    }

    public void setJogado(int jogado) {
        this.jogado = jogado;
    }

    public int isAceitoDesafiado() {
        return AceitoDesafiado;
    }

    public void setAceitoDesafiado(int aceitoDesafiado) {
        AceitoDesafiado = aceitoDesafiado;
    }

    public int isAceitoDesafiador() {
        return AceitoDesafiador;
    }

    public void setAceitoDesafiador(int aceitoDesafiador) {
        AceitoDesafiador = aceitoDesafiador;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public short getResultTenistaDesafiado1() {
        return ResultTenistaDesafiado1;
    }

    public void setResultTenistaDesafiado1(short resultTenistaDesafiado1) {
        ResultTenistaDesafiado1 = resultTenistaDesafiado1;
    }

    public short getResultTenistaDesafiador1() {
        return ResultTenistaDesafiador1;
    }

    public void setResultTenistaDesafiador1(short resultTenistaDesafiador1) {
        ResultTenistaDesafiador1 = resultTenistaDesafiador1;
    }

    public short getTieBreakDesafiado1() {
        return tieBreakDesafiado1;
    }

    public void setTieBreakDesafiado1(short tieBreakDesafiado1) {
        this.tieBreakDesafiado1 = tieBreakDesafiado1;
    }

    public short getTieBreakDesafiador1() {
        return tieBreakDesafiador1;
    }

    public void setTieBreakDesafiador1(short tieBreakDesafiador1) {
        this.tieBreakDesafiador1 = tieBreakDesafiador1;
    }

    public short getResultTenistaDesafiado2() {
        return ResultTenistaDesafiado2;
    }

    public void setResultTenistaDesafiado2(short resultTenistaDesafiado2) {
        ResultTenistaDesafiado2 = resultTenistaDesafiado2;
    }

    public short getResultTenistaDesafiador2() {
        return ResultTenistaDesafiador2;
    }

    public void setResultTenistaDesafiador2(short resultTenistaDesafiador2) {
        ResultTenistaDesafiador2 = resultTenistaDesafiador2;
    }

    public short getTieBreakDesafiado2() {
        return tieBreakDesafiado2;
    }

    public void setTieBreakDesafiado2(short tieBreakDesafiado2) {
        this.tieBreakDesafiado2 = tieBreakDesafiado2;
    }

    public short getTieBreakDesafiador2() {
        return tieBreakDesafiador2;
    }

    public void setTieBreakDesafiador2(short tieBreakDesafiador2) {
        this.tieBreakDesafiador2 = tieBreakDesafiador2;
    }

    public short getResultTenistaDesafiado3() {
        return ResultTenistaDesafiado3;
    }

    public void setResultTenistaDesafiado3(short resultTenistaDesafiado3) {
        ResultTenistaDesafiado3 = resultTenistaDesafiado3;
    }

    public short getResultTenistaDesafiador3() {
        return ResultTenistaDesafiador3;
    }

    public void setResultTenistaDesafiador3(short resultTenistaDesafiador3) {
        ResultTenistaDesafiador3 = resultTenistaDesafiador3;
    }

    public short getTieBreakDesafiado3() {
        return tieBreakDesafiado3;
    }

    public void setTieBreakDesafiado3(short tieBreakDesafiado3) {
        this.tieBreakDesafiado3 = tieBreakDesafiado3;
    }

    public short getTieBreakDesafiador3() {
        return tieBreakDesafiador3;
    }

    public void setTieBreakDesafiador3(short tieBreakDesafiador3) {
        this.tieBreakDesafiador3 = tieBreakDesafiador3;
    }

    public short getWO() {
        return WO;
    }

    public void setWO(short WO) {
        this.WO = WO;
    }

    public int isConfirmadoCoordenador() {
        return confirmadoCoordenador;
    }

    public void setConfirmadoCoordenador(int confirmadoCoordenador) {
        this.confirmadoCoordenador = confirmadoCoordenador;
    }

    public int isConfirmadoDesafiado() {
        return confirmadoDesafiado;
    }

    public void setConfirmadoDesafiado(int confirmadoDesafiado) {
        this.confirmadoDesafiado = confirmadoDesafiado;
    }

    public int isConfirmadoDesafiador() {
        return confirmadoDesafiador;
    }

    public void setConfirmadoDesafiador(int confirmadoDesafiador) {
        this.confirmadoDesafiador = confirmadoDesafiador;
    }


    public Tenista getTenistaDesafiado() {
        return tenistaDesafiado;
    }

    public void setTenistaDesafiado(Tenista tenistaDesafiado) {
        this.tenistaDesafiado = tenistaDesafiado;
    }

    public Tenista getTenistaDesafiador() {
        return tenistaDesafiador;
    }

    public void setTenistaDesafiador(Tenista tenistaDesafiador) {
        this.tenistaDesafiador = tenistaDesafiador;
    }
}
