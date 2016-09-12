package com.santos.diego.tenisrank;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Diego on 11/09/2016.
 */

public class Regra {

    private Integer idRegra=-1;
    private String DataAlteracao="";
    private Integer QtdDiasPorMesPodeDesafiar=0;
    private Integer QtdDiasPorMesRebecerDesafio=0;
    private Integer PosicaoMaximaQPodeDesafiar = -1;
    private Integer DesafiadorQtdPosCasoVitoria=-1;
    private Integer DesafiadoQtdPosCasoVitoria = -1;
    private Integer DesafiadorQtdPosCasoDerrota = -1;
    private Integer DesafiadoQtdPosCasoDerrota = -1;
    private Integer QtdPosCaiCasoNaoDesafieMes = -1;
    private Integer tempoWO = 15;
    private Integer QtdPosicoesPerdeCasoWO = 0;

    public Integer getIdRegra() {
        return idRegra;
    }

    public void setIdRegra(Integer idRegra) {
        this.idRegra = idRegra;
    }

    public String getDataAlteracao() {
        return DataAlteracao;
    }

    public void setDataAlteracao(String dataAlteracao) {
        DataAlteracao = dataAlteracao;
    }

    public Integer getQtdDiasPorMesPodeDesafiar() {
        return QtdDiasPorMesPodeDesafiar;
    }

    public void setQtdDiasPorMesPodeDesafiar(Integer qtdDiasPorMesPodeDesafiar) {
        QtdDiasPorMesPodeDesafiar = qtdDiasPorMesPodeDesafiar;
    }

    public Integer getQtdDiasPorMesRebecerDesafio() {
        return QtdDiasPorMesRebecerDesafio;
    }

    public void setQtdDiasPorMesRebecerDesafio(Integer qtdDiasPorMesRebecerDesafio) {
        QtdDiasPorMesRebecerDesafio = qtdDiasPorMesRebecerDesafio;
    }

    public Integer getPosicaoMaximaQPodeDesafiar() {
        return PosicaoMaximaQPodeDesafiar;
    }

    public void setPosicaoMaximaQPodeDesafiar(Integer posicaoMaximaQPodeDesafiar) {
        PosicaoMaximaQPodeDesafiar = posicaoMaximaQPodeDesafiar;
    }

    public Integer getDesafiadorQtdPosCasoVitoria() {
        return DesafiadorQtdPosCasoVitoria;
    }

    public void setDesafiadorQtdPosCasoVitoria(Integer desafiadorQtdPosCasoVitoria) {
        DesafiadorQtdPosCasoVitoria = desafiadorQtdPosCasoVitoria;
    }

    public Integer getDesafiadoQtdPosCasoVitoria() {
        return DesafiadoQtdPosCasoVitoria;
    }

    public void setDesafiadoQtdPosCasoVitoria(Integer desafiadoQtdPosCasoVitoria) {
        DesafiadoQtdPosCasoVitoria = desafiadoQtdPosCasoVitoria;
    }

    public Integer getDesafiadorQtdPosCasoDerrota() {
        return DesafiadorQtdPosCasoDerrota;
    }

    public void setDesafiadorQtdPosCasoDerrota(Integer desafiadorQtdPosCasoDerrota) {
        DesafiadorQtdPosCasoDerrota = desafiadorQtdPosCasoDerrota;
    }

    public Integer getDesafiadoQtdPosCasoDerrota() {
        return DesafiadoQtdPosCasoDerrota;
    }

    public void setDesafiadoQtdPosCasoDerrota(Integer desafiadoQtdPosCasoDerrota) {
        DesafiadoQtdPosCasoDerrota = desafiadoQtdPosCasoDerrota;
    }

    public Integer getQtdPosCaiCasoNaoDesafieMes() {
        return QtdPosCaiCasoNaoDesafieMes;
    }

    public void setQtdPosCaiCasoNaoDesafieMes(Integer qtdPosCaiCasoNaoDesafieMes) {
        QtdPosCaiCasoNaoDesafieMes = qtdPosCaiCasoNaoDesafieMes;
    }

    public Integer getTempoWO() {
        return tempoWO;
    }

    public void setTempoWO(Integer tempoWO) {
        this.tempoWO = tempoWO;
    }

    public Integer getQtdPosicoesPerdeCasoWO() {
        return QtdPosicoesPerdeCasoWO;
    }

    public void setQtdPosicoesPerdeCasoWO(Integer qtdPosicoesPerdeCasoWO) {
        QtdPosicoesPerdeCasoWO = qtdPosicoesPerdeCasoWO;
    }

    public String toJson()
    {
        JSONObject json = new JSONObject();

        try{
            json.put("idRegra",this.getIdRegra());
            json.put("DataAlteracao",this.getDataAlteracao());
            json.put("QtdDiasPorMesPodeDesafiar",this.getQtdDiasPorMesPodeDesafiar());
            json.put("QtdDiasPMesReceberDesafio",this.getQtdDiasPorMesRebecerDesafio());
            json.put("PosicaoMaximaQPodeDesafiar",this.getPosicaoMaximaQPodeDesafiar());
            json.put("DesafiadorQtdPosCasoVitoria",this.getDesafiadorQtdPosCasoVitoria());
            json.put("DesafiadoQtdPosCasoVitoria",this.getDesafiadoQtdPosCasoVitoria());
            json.put("DesafiadorQtdPosCasoDerrota",this.getDesafiadorQtdPosCasoDerrota());
            json.put("DesafiadoQtdPosCasoDerrota",this.getDesafiadoQtdPosCasoDerrota());
            json.put("QtdPosCaiCasoNaoDesafieMes",this.getQtdPosCaiCasoNaoDesafieMes());
            json.put("TempoWO",this.getTempoWO());
            json.put("QtdPosicoesPerdeCasoWO",this.getQtdPosicoesPerdeCasoWO());

            Log.i("JSONSTRING_REGRA",json.toString());
            return json.toString();



        }catch (JSONException e)
        {
            return null;
        }
    }
}
