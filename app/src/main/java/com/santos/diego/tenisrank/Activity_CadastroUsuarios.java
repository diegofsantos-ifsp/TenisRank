package com.santos.diego.tenisrank;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Activity_CadastroUsuarios extends Activity {

    private Button button_enviarCadastro;
    private Spinner spinner;
    private ArrayList<String> nomesCategorias = null;
    private ArrayAdapter<String> adapter_spinner = null;
    private ArrayList<Categoria> categorias = null;
    private String IP = null;


    private Boolean existeCoordenador=false;

    private EditText nome = null;
    private EditText email = null;
    private EditText telefone = null;
    private EditText senha = null;
    private EditText endereco = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__cadastro_usuarios);



        button_enviarCadastro = (Button) findViewById(R.id.button_enviarcadastro);
        spinner = (Spinner) findViewById(R.id.spinner_categoria);

        nome = (EditText) findViewById(R.id.edittext_nome_completo);
        endereco = (EditText) findViewById(R.id.edittext_endereco);
        telefone = (EditText) findViewById(R.id.edittext_telefone);
        senha = (EditText) findViewById(R.id.edittext_senha);
        email = (EditText) findViewById(R.id.edittext_email);


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        IP = pref.getString("ip","0");


        nomesCategorias = new ArrayList<String>();
        adapter_spinner = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,nomesCategorias);
        adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter_spinner);

        //preenche o spinner
        CategoriaAsyncTask catAsync = new CategoriaAsyncTask(0);

        catAsync.execute((Void) null);


        ConsultaBancoTask consultaTask = new ConsultaBancoTask();
        consultaTask.execute((Void) null);


        //ao clicar no botão, executar código para armazenar os dados do usuário no banco
        button_enviarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = "";
                Boolean erro = false;


                if (!nome.getText().toString().isEmpty() && !endereco.getText().toString().isEmpty() &&
                     !telefone.getText().toString().isEmpty() && !senha.getText().toString().isEmpty() && !email.getText().toString().isEmpty())
                {
                    final Usuario usuario = new Usuario();
                    usuario.setNome(nome.getText().toString());
                    usuario.setEndereco(endereco.getText().toString());
                    usuario.setTelefone(telefone.getText().toString());
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());

                    Calendar cal = Calendar.getInstance();
                    String data = String.valueOf(cal.get(Calendar.YEAR))+"-"+String.valueOf(cal.get(Calendar.MONTH)+1)+"-"+String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
                    String hora = String.valueOf(cal.get(Calendar.HOUR))+":"+String.valueOf(cal.get(Calendar.MINUTE));

                    usuario.setDataultimoacesso(data);
                    usuario.setHoraultimoacesso(hora);

                    //ao adicionar o usuário, fazer com que ele já fique ativo no sistema
                    usuario.setCadastrovalido(1);

                    //se existem coordenadores cadastrados, cadastrar o usuário atual somente como tenista
                    if (!existeCoordenador) {
                        //cadastrar nova categoria
                        if (nomesCategorias.isEmpty()) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(Activity_CadastroUsuarios.this);
                            builder1.setTitle("Cadastro de categorias");
                            builder1.setMessage("Não existem categorias cadastradas!!\nPara que você possa se cadastrar como coordenador, você deverá antes digitar no campo abaixo" +
                                    " o nome de uma categoria para incluir no sistema.\n\n");
                            builder1.setCancelable(true);
                            final EditText input = new EditText(Activity_CadastroUsuarios.this);
                            builder1.setView(input);

                            builder1.setPositiveButton(
                                    "Cadastrar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {


                                            CadastrarUsuarioTask cadastrar = new CadastrarUsuarioTask();
                                            cadastrar.setCadastrarCategoria(true);
                                            cadastrar.setCadastrarCoordenador(true);
                                            cadastrar.setUsuario(usuario);
                                            cadastrar.setNomeCategoria(input.getText().toString());
                                            cadastrar.execute((Void) null);
                                            //cadastrar categoria, usuário, tenista e coordenador
                                            finish();

                                        }
                                    });

                            builder1.setNegativeButton(
                                    "Cancelar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                        //existe categoria já selecionada pelo usuário
                        else
                        {
                            CadastrarUsuarioTask cadastrar = new CadastrarUsuarioTask();
                            cadastrar.setCadastrarCoordenador(true);
                            cadastrar.setIdCategoria(categorias.get(spinner.getSelectedItemPosition()).getIdCategoria());
                            cadastrar.setUsuario(usuario);
                            cadastrar.execute((Void) null);
                            finish();

                        }

                    }
                    //se existe coordenador então existe categoria, cadastrar usuario e tenista
                    else {
                        CadastrarUsuarioTask cadastrar = new CadastrarUsuarioTask();
                        cadastrar.setIdCategoria(categorias.get(spinner.getSelectedItemPosition()).getIdCategoria());
                        cadastrar.setUsuario(usuario);
                        cadastrar.execute((Void) null);
                        finish();
                    }


                }
                else
                    Toast.makeText(Activity_CadastroUsuarios.this, "Todos os campos são obrigatórios !!!", Toast.LENGTH_LONG).show();

            }
        });
    }


    private class CadastrarUsuarioTask extends AsyncTask<Void, Void, Boolean> {

        Boolean cadastrarCategoria=false;
        Boolean cadastrarCoordenador=false;

        private Integer novoidUsuario=-1;


        Usuario usuario=null;

        Integer error=0;
        Integer result=0;


        String nomeCategoria=null;
        Integer idCategoria = -1;


        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }


        public Integer getIdCategoria() {
            return idCategoria;
        }

        public void setIdCategoria(Integer idCategoria) {
            this.idCategoria = idCategoria;
        }




        public String getNomeCategoria() {
            return nomeCategoria;
        }

        public void setNomeCategoria(String nomeCategoria) {
            this.nomeCategoria = nomeCategoria;
        }


        public Boolean getCadastrarCategoria() {
            return cadastrarCategoria;
        }

        public void setCadastrarCategoria(Boolean cadastrarCategoria) {
            this.cadastrarCategoria = cadastrarCategoria;
        }

        public Boolean getCadastrarCoordenador() {
            return cadastrarCoordenador;
        }

        public void setCadastrarCoordenador(Boolean cadastrarCoordenador) {
            this.cadastrarCoordenador = cadastrarCoordenador;
        }



        @Override
        protected Boolean doInBackground(Void... voids) {


            DatabaseJson json = new DatabaseJson();
            json.setIP(IP);


            if (cadastrarCategoria)
            {

                Categoria tempCat = new Categoria();
                tempCat.setNome(nomeCategoria);

                json.insereCategoria(1,tempCat);

                error=json.getError();
                result = json.getResult();


                if (result==0)
                {

                    return false;
                }
                else
                    idCategoria=result;



            }



            //cadastrar Usuário
            if (usuario!=null)
            {
                json.insereUsuario(1,usuario);
                error=json.getError();
                result=json.getResult();

                if (result==0)
                    return false;
                else
                    novoidUsuario = result;
            }



            if (cadastrarCoordenador)
            {
                Coordenador temp = new Coordenador();
                temp.setIdUsuarios(novoidUsuario);

                json.insereCoordenador(1,temp);

                error=json.getError();
                result=json.getResult();

                if (result==0)
                    return false;

            }


            //se o usuário escolheu categoria então é possível adicionar um tenista
            if (idCategoria>0) {
                Tenista ten = new Tenista();
                ten.setCategoria(idCategoria);
                ten.setPosicaoAtualRanking(0);
                ten.setEstaNoRanking(0);
                ten.setUsuario(usuario);
                ten.getUsuario().setId(novoidUsuario);

                json.insereTenista(1,ten);

                error=json.getError();
                result=json.getResult();

                if (result==0)
                    return false;

            }


            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Boolean res) {
            super.onPostExecute(res);
            if (res != false) {
                Toast.makeText(Activity_CadastroUsuarios.this, "Usuário cadastro com sucesso !!!", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(Activity_CadastroUsuarios.this, "Ocorreu um problema ao cadastrar o usuário !!!", Toast.LENGTH_LONG).show();
        }
    }

    private class ConsultaBancoTask extends AsyncTask<Void, Void, Boolean>
    {
        ArrayList<Coordenador> coords = null;
        Integer error = 0;
        Integer result = 0;

        @Override
        protected Boolean doInBackground(Void... voids) {

            //verifica se existe coordenador cadastrado

            DatabaseJson json = new DatabaseJson();
            json.setIP(IP);

            coords = json.getCoordenador(-1,-1);
            error = json.getError();
            result = json.getResult();

            //se não existir erro de conexão
            if (error==0) {
                //conseguiu conectar mas não existem coordenadores cadastrados, cadastrar coordenador
                if (coords == null && result==0) {
                    Log.i("COORDENADORES", "NÃO");
                    return true;
                }
                //existem coordenadores cadastrados, verificar categorias e adicionar usuário como tenista
                else if (coords!=null && result==1){
                    Log.i("COORDENADORES", "SIM");
                    return true;
                }

            }


            return false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Boolean res) {
            super.onPostExecute(res);
            if (res!=false) {
                existeCoordenador=true;

            }
        }
    }


    //popula a variável Categorias com todas as categorias existentes e atualiza o spinner
    private class CategoriaAsyncTask extends AsyncTask<Void, Integer,ArrayList<Categoria> > {

        private ArrayList<Categoria> temp = null;
        // private DatabaseJson json = null;
        private Integer idCategoria=null;

        CategoriaAsyncTask (Integer i)
        {
            idCategoria=i;
        }

        @Override
        protected ArrayList<Categoria> doInBackground(Void... values) {


            //  if (ranking!=null || ranking==null) {
            publishProgress(10);
            //Thread.sleep(1000);
            DatabaseJson json = new DatabaseJson();

            json.setIP(IP);
            // publishProgress(30);
            //Thread.sleep(1000);
            //    Log.i("RANKING","DEPOIS");
            //ArrayList<Usuario> users = json.getUsersByEmail(mEmail);
            temp = json.getCategorias();

            //publishProgress(60);
            //Thread.sleep(1000);

            return temp;

            //}


            //return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(ArrayList<Categoria> cat) {
            super.onPostExecute(cat);
            if (cat!=null) {
                //;
                categorias = cat;
                if(nomesCategorias!=null)
                    nomesCategorias.clear();
                for (int x=0; x<categorias.size(); x++) {
                    nomesCategorias.add(categorias.get(x).getNome());
                    //              Log.i("CAT",categorias.get(x).getNome());
                }
                adapter_spinner.notifyDataSetChanged();


            }
        }

    }
}
