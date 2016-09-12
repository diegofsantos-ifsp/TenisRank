package com.santos.diego.tenisrank;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;

public class PreferencesActivity extends Activity {

    private Regra regra = null;
    private String IP = null;
    SharedPreferences pref = null;


    @Override
    protected void onStart()
    {
        super.onStart();

        ConsultaRegrasAsyncTask consulta = new ConsultaRegrasAsyncTask();
        consulta.execute((Void) null);
    }


    @Override
    protected void onPause()
    {
        super.onPause();

        regra = new Regra();
        regra.setIdRegra(Integer.valueOf(pref.getString("idRegra","-1")));
        regra.setDataAlteracao(pref.getString("DataAlteracao",""));
        regra.setQtdDiasPorMesPodeDesafiar(Integer.valueOf(pref.getString("QtdDiasPorMesPodeDesafiar","-1")));
        regra.setQtdDiasPorMesRebecerDesafio(Integer.valueOf(pref.getString("QtdDiasPMesReceberDesafio","-1")));
        regra.setPosicaoMaximaQPodeDesafiar(Integer.valueOf(pref.getString("PosicaoMaximaQPodeDesafiar","-1")));
        regra.setDesafiadorQtdPosCasoVitoria(Integer.valueOf(pref.getString("DesafiadorQtdPosCasoVitoria","-1")));
        regra.setDesafiadoQtdPosCasoVitoria(Integer.valueOf(pref.getString("DesafiadoQtdPosCasoVitoria","-1")));
        regra.setDesafiadorQtdPosCasoDerrota(Integer.valueOf(pref.getString("DesafiadorQtdPosCasoDerrota","-1")));
        regra.setDesafiadoQtdPosCasoDerrota(Integer.valueOf(pref.getString("DesafiadoQtdPosCasoDerrota","-1")));
        regra.setQtdPosCaiCasoNaoDesafieMes(Integer.valueOf(pref.getString("QtdPosCaiCasoNaoDesafieMes","-1")));
        regra.setTempoWO(Integer.valueOf(pref.getString("TempoWO","-1")));
        regra.setQtdPosicoesPerdeCasoWO(Integer.valueOf(pref.getString("QtdPosicoesPerdeCasoWO","-1")));

        InsereRegraAsyncTask regraAsync = new InsereRegraAsyncTask();
        regraAsync.setRegra(regra);
        regraAsync.execute((Void) null);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        IP = pref.getString("ip","0");

        /*
        */

       // getFragmentManager().beginTransaction().replace(android.R.id.content,new PreferencesFragment()).commit();

    }


    private class InsereRegraAsyncTask extends AsyncTask <Void, Void, Boolean>
    {
        private ProgressDialog progressDialog = null;
        private Regra temp;

        public Regra getRegra() {
            return temp;
        }

        public void setRegra(Regra temp) {
            this.temp = temp;
        }



        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PreferencesActivity.this);
            progressDialog.setMessage("Aguarde...");
            progressDialog.setCancelable(false);
            progressDialog.show();



        }


        @Override
        protected Boolean doInBackground(Void... voids) {

            DatabaseJson json = new DatabaseJson();
            json.setIP(IP);


            json.insereRegra(2,temp);

            if (json.getError()>0)
                return false;

            return true;
        }


        @Override
        protected void onPostExecute(Boolean d)
        {
            super.onPostExecute(d);

            progressDialog.cancel();
        }

    }

    private class ConsultaRegrasAsyncTask extends AsyncTask<Void, Void, Boolean>
    {

        private ProgressDialog progressDialog;
        ArrayList<Regra> regras = null;

        Regra regra = null;

        @Override
        protected Boolean doInBackground(Void... voids) {

            DatabaseJson json = new DatabaseJson();
            json.setIP(IP);

            regras = json.getRegras();


            if (regras==null || json.getError()>0) {
                Log.i("Regras","false");
                return false;
            }

            Log.i("Regras","true");

            if (regras!=null)
                regra = regras.get(0);

            return true;
        }



        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PreferencesActivity.this);
            progressDialog.setMessage("Carregando...");
            progressDialog.setCancelable(false);
            progressDialog.show();



        }

        @Override
        protected void onPostExecute(Boolean b)
        {
            super.onPostExecute(b);

            if (regras!=null) {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(PreferencesActivity.this);
                SharedPreferences.Editor editor = pref.edit();
             //   Log.i("idRegra",regra.getIdRegra().toString());
                editor.putString("idRegra", regra.getIdRegra().toString());
                editor.putString("DataAlteracao",regra.getDataAlteracao());
                editor.putString("QtdDiasPorMesPodeDesafiar",regra.getQtdDiasPorMesPodeDesafiar().toString());
                editor.putString("QtdDiasPMesReceberDesafio",regra.getQtdDiasPorMesRebecerDesafio().toString());
                editor.putString("PosicaoMaximaQPodeDesafiar",regra.getPosicaoMaximaQPodeDesafiar().toString());
                editor.putString("DesafiadorQtdPosCasoVitoria",regra.getDesafiadorQtdPosCasoVitoria().toString());
                editor.putString("DesafiadoQtdPosCasoVitoria",regra.getDesafiadoQtdPosCasoVitoria().toString());
                editor.putString("DesafiadorQtdPosCasoDerrota",regra.getDesafiadorQtdPosCasoDerrota().toString());
                editor.putString("DesafiadoQtdPosCasoDerrota",regra.getDesafiadoQtdPosCasoDerrota().toString());
                editor.putString("QtdPosCaiCasoNaoDesafieMes",regra.getQtdPosCaiCasoNaoDesafieMes().toString());
                editor.putString("TempoWO",regra.getTempoWO().toString());
                editor.putString("QtdPosicoesPerdeCasoWO",regra.getQtdPosicoesPerdeCasoWO().toString());


                editor.commit();




            }

            progressDialog.cancel();

            getFragmentManager().beginTransaction().replace(android.R.id.content,new PreferencesFragment()).commit();
            //MenuItem item =;
            //int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            //if (id == R.id.action_settings) {



        }



    }

}
