package com.santos.diego.tenisrank;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Diego on 19/04/2016.
 */
public class Desafio {
    private int idDesafio;
    private Integer idTenistaDesafiado=0;
    private Integer idTenistaDesafiador=0;
    private int idQuadra=0;


    private int DesafiadorPontosSeGanhar=0;
    private int DesafiadorPontosSePerder=0;
    private int DesafiadoPontosSeGanhar=0;
    private int DesafiadoPontosSePerder=0;


    private int idCategoria=0;
    private int EstaNoRanking=0;

    private int ganhador=0;
    private String Data;
    private String Hora;

    int jogado=0;
    int AceitoDesafiado=0;
    int AceitoDesafiador=0;
    private String Descricao="";
    private short ResultTenistaDesafiado1=-1;
    private short ResultTenistaDesafiador1=-1;
    private short tieBreakDesafiado1=-1;
    private short tieBreakDesafiador1=-1;
    private Tenista tenistaDesafiado;
    private Tenista tenistaDesafiador;

    private short ResultTenistaDesafiado2=-1;
    private short ResultTenistaDesafiador2=-1;
    private short tieBreakDesafiado2=-1;
    private short tieBreakDesafiador2=-1;
    private short ResultTenistaDesafiado3=-1;
    private short ResultTenistaDesafiador3=-1;
    private short tieBreakDesafiado3=-1;
    private short tieBreakDesafiador3=-1;
    private short WO=0;
    int confirmadoCoordenador=0;
    int confirmadoDesafiado=0;
    int confirmadoDesafiador=0;

    public int getDesafiadorPontosSeGanhar() {
        return DesafiadorPontosSeGanhar;
    }

    public void setDesafiadorPontosSeGanhar(int desafiadorPontosSeGanhar) {
        DesafiadorPontosSeGanhar = desafiadorPontosSeGanhar;
    }

    public int getDesafiadorPontosSePerder() {
        return DesafiadorPontosSePerder;
    }

    public void setDesafiadorPontosSePerder(int desafiadorPontosSePerder) {
        DesafiadorPontosSePerder = desafiadorPontosSePerder;
    }

    public int getDesafiadoPontosSeGanhar() {
        return DesafiadoPontosSeGanhar;
    }

    public void setDesafiadoPontosSeGanhar(int desafiadoPontosSeGanhar) {
        DesafiadoPontosSeGanhar = desafiadoPontosSeGanhar;
    }

    public int getDesafiadoPontosSePerder() {
        return DesafiadoPontosSePerder;
    }

    public void setDesafiadoPontosSePerder(int desafiadoPontosSePerder) {
        DesafiadoPontosSePerder = desafiadoPontosSePerder;
    }


    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }


    public int getEstaNoRanking() {
        return EstaNoRanking;
    }

    public void setEstaNoRanking(int estaNoRanking) {
        EstaNoRanking = estaNoRanking;
    }

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

    public String toJson()
    {


        JSONObject json = new JSONObject();

        try{
            json.put("idDesafio",this.getIdDesafio());
            json.put("idTenistaDesafiado",this.getIdTenistaDesafiado());
            json.put("idTenistaDesafiador",this.getIdTenistaDesafiador());
            json.put("idQuadra",this.getIdQuadra());
            json.put("idCategoria",this.getIdCategoria());
            json.put("Ganhador",this.getGanhador());
            json.put("Data",this.getData());
            json.put("Hora",this.getHora());
            json.put("Jogado",this.isJogado());
            json.put("AceitoDesafiado",this.isAceitoDesafiado());
            json.put("AceitoDesafiador",this.isAceitoDesafiador());
            json.put("Descricao",this.getDescricao());
            json.put("ResultTenistaDesafiado1",this.getResultTenistaDesafiado1());
            json.put("ResultTenistaDesafiador1",this.getResultTenistaDesafiador1());
            json.put("ResultTenistaDesafiado2",this.getResultTenistaDesafiado2());
            json.put("ResultTenistaDesafiador2",this.getResultTenistaDesafiador2());
            json.put("ResultTenistaDesafiado3",this.getResultTenistaDesafiado3());
            json.put("ResultTenistaDesafiador3",this.getResultTenistaDesafiador3());
            json.put("tieBreakDesafiado1",this.getTieBreakDesafiado1());
            json.put("tieBreakDesafiador1",this.getTieBreakDesafiador1());
            json.put("tieBreakDesafiado2",this.getTieBreakDesafiado2());
            json.put("tieBreakDesafiador2",this.getTieBreakDesafiador2());
            json.put("tieBreakDesafiado3",this.getTieBreakDesafiado3());
            json.put("tieBreakDesafiador3",this.getTieBreakDesafiador3());
            json.put("WO",this.getWO());
            json.put("ConfirmadoCoordenador",this.isConfirmadoCoordenador());
            json.put("ConfirmadoDesafiado",this.isConfirmadoDesafiado());
            json.put("ConfirmadoDesafiador",this.isConfirmadoDesafiador());
            json.put("DesafiadorPontosSeGanhar",this.getDesafiadorPontosSeGanhar());
            json.put("DesafiadorPontosSePerder",this.getDesafiadorPontosSePerder());
            json.put("DesafiadoPontosSeGanhar",this.getDesafiadoPontosSeGanhar());
            json.put("DesafiadoPontosSePerder",this.getDesafiadoPontosSePerder());
            json.put("EstaNoRanking",this.getEstaNoRanking());
            Log.i("JSONSTRING",json.toString());
            return json.toString();



        }catch (JSONException e)
        {
            return null;
        }


    }
}
