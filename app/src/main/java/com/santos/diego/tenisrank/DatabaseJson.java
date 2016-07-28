package com.santos.diego.tenisrank;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by Diego on 07/03/2016.
 */
public class DatabaseJson {


    private URL url;
    private String IP;



    JSONParser jparser = new JSONParser();

    //ArrayList<HashMap<String,String>> usuariosArray;
    ArrayList<Usuario> usuarioArray;
    ArrayList<Tenista> tenistaArray;

    JSONArray usuariosJson = null;
    JSONArray tenistasJson = null;


    String error;

    DatabaseJson()
    {
        IP = "192.168.0.15";
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }




    //retorna um array com todos os usuários da consulta no banco de dados
    public ArrayList<Usuario> getUsers()
    {
        HashMap<String,String> params = new HashMap<String,String>();

        Log.v("JSON","ANTES");
        try {

            //params.put("Email","diegofsantos@gmail.com");

            String http = "http://"+IP+"/tenis/consultaUsuario.php";
            Log.i("HTTP",http);
            JSONObject json = jparser.makeHttpRequest(http, "POST", params);
            if (json != null)
            if (json.getInt("result")==1) {
                usuariosJson = json.getJSONArray("usuarios");
                usuarioArray = new ArrayList<Usuario>();

                for (int i=0; i< usuariosJson.length(); i++)
                {
                    JSONObject u = usuariosJson.getJSONObject(i);

                    Usuario temp = new Usuario();
                    temp.setId(u.getInt("idUsuarios"));
                    temp.setNome(u.getString("Nome"));
                    temp.setEndereco(u.getString("Endereco"));
                    temp.setTelefone(u.getString("Telefone"));
                    temp.setEmail(u.getString("Email"));
                    temp.setNomeusuario(u.getString("NomeUsuario"));
                    temp.setSenha(u.getString("Senha"));
                    usuarioArray.add(temp);
                }
            }

        }catch (JSONException e) {e.printStackTrace(); }
        Log.v("JSON","DEPOIS");

       // Log.v("JSON", json.toString());
        return usuarioArray;
    }

    public ArrayList<Usuario> getUsersByName(String Nome)
    {
        HashMap<String,String> params = new HashMap<String,String>();

        params.put("Nome",Nome);

        Log.v("JSON","ANTES");
        try {
            params.put("Nome","Diego");

            String http = "http://"+IP+"/tenis/consultaUsuario.php";
            JSONObject json = jparser.makeHttpRequest(http, "POST", params);

            if (json.getInt("result")==1) {
                usuariosJson = json.getJSONArray("usuarios");
                usuarioArray = new ArrayList<Usuario>();

                for (int i=0; i< usuariosJson.length(); i++)
                {
                    JSONObject u = usuariosJson.getJSONObject(i);

                    Usuario temp = new Usuario();
                    temp.setId(u.getInt("idUsuarios"));
                    temp.setNome(u.getString("Nome"));
                    temp.setEndereco(u.getString("Endereco"));
                    temp.setTelefone(u.getString("Telefone"));
                    temp.setEmail(u.getString("Email"));
                    temp.setNomeusuario(u.getString("NomeUsuario"));
                    temp.setSenha(u.getString("Senha"));
                    usuarioArray.add(temp);
                }
            }

        }catch (JSONException e) {e.printStackTrace(); }
        Log.v("JSON","DEPOIS");

        // Log.v("JSON", json.toString());
        return usuarioArray;
    }

    public ArrayList<Usuario> getUsersByEmail(String Email)
    {
        HashMap<String,String> params = new HashMap<String,String>();

        params.put("Email",Email);

        Log.v("JSON","ANTES");
        try {
            String http = "http://"+IP+"/tenis/consultaUsuario.php";
            Log.i("HTTP",http);
            JSONObject json = jparser.makeHttpRequest(http, "POST", params);

            if (json == null)
                return null;

            if (json.getInt("result")==1) {
                usuariosJson = json.getJSONArray("usuarios");
                usuarioArray = new ArrayList<Usuario>();

                for (int i=0; i< usuariosJson.length(); i++)
                {
                    JSONObject u = usuariosJson.getJSONObject(i);

                    Usuario temp = new Usuario();
                    temp.setId(u.getInt("idUsuarios"));
                    temp.setNome(u.getString("Nome"));
                    temp.setEndereco(u.getString("Endereco"));
                    temp.setTelefone(u.getString("Telefone"));
                    temp.setEmail(u.getString("Email"));
                    temp.setNomeusuario(u.getString("NomeUsuario"));
                    temp.setSenha(u.getString("Senha"));
                    usuarioArray.add(temp);
                }
            }

        }catch (JSONException e) {e.printStackTrace(); }
        Log.v("JSON","DEPOIS");

        // Log.v("JSON", json.toString());
        return usuarioArray;
    }


    public ArrayList<Usuario> getUsersByID(Integer id)
    {
        HashMap<String,String> params = new HashMap<String,String>();

        params.put("idUsuarios",id.toString());

        Log.v("JSON","ANTES");
        try {
            String http = "http://"+IP+"/tenis/consultaUsuario.php";
            JSONObject json = jparser.makeHttpRequest(http, "POST", params);

            if (json == null)
                return null;

            if (json.getInt("result")==1) {
                usuariosJson = json.getJSONArray("usuarios");
                usuarioArray = new ArrayList<Usuario>();

                for (int i=0; i< usuariosJson.length(); i++)
                {
                    JSONObject u = usuariosJson.getJSONObject(i);

                    Usuario temp = new Usuario();
                    temp.setId(u.getInt("idUsuarios"));
                    temp.setNome(u.getString("Nome"));
                    temp.setEndereco(u.getString("Endereco"));
                    temp.setTelefone(u.getString("Telefone"));
                    temp.setEmail(u.getString("Email"));
                    temp.setNomeusuario(u.getString("NomeUsuario"));
                    temp.setSenha(u.getString("Senha"));
                    usuarioArray.add(temp);
                }
            }

        }catch (JSONException e) {e.printStackTrace(); }
        Log.v("JSON","DEPOIS");

        // Log.v("JSON", json.toString());
        return usuarioArray;
    }


    //retorna os jogos de um determinado tenista
    // Se type=0 retorna os próximos jogos
    // Se type=1 retorna os jogos passados
    // Se type-=2 retorna todos os jogos
    // Se tenistaDesc = 1 (retorna a descrição dos tenistas)
    public ArrayList<Desafio> getJogosByTenista(int type, int idTen, int tenistaDesc)
    {
        ArrayList<Desafio> desafiosArray = null;
        JSONArray desafiosJson = null;

        HashMap<String,String> params = new HashMap<String,String>();


        if (type==0) {
            params.put("PORJOGAR", "1");
            params.put("idTenistas",Integer.toString(idTen));
        }
        else if (type==1) {
            params.put("PORJOGAR", "0");
            params.put("idTenistas",Integer.toString(idTen));

        }
        else if (type==2)
            params.put("idTenistas",Integer.toString(idTen));

        try {

            String http = "http://"+IP+"/tenis/consultaJogoByTenista.php";
            JSONObject json = jparser.makeHttpRequest(http, "GET", params);

            if (json == null)
                return null;

            if (json.getInt("result")==1) {
                desafiosJson = json.getJSONArray("desafios");
                desafiosArray = new ArrayList<Desafio>();

                for (int i=0; i< desafiosJson.length(); i++)
                {
                    JSONObject u = desafiosJson.getJSONObject(i);
                    Desafio des = new Desafio();

                    des.setIdDesafio(u.getInt("idDesafios"));
                    des.setIdTenistaDesafiador(u.getInt("TenistaDesafiador_idTenistas"));
                    Log.i("DESAFIADOR",des.getIdTenistaDesafiador().toString());
                    des.setIdTenistaDesafiado(u.getInt("TenistaDesafiado"));
                    Log.i("DESAFIADO",des.getIdTenistaDesafiado().toString());
                    des.setIdQuadra(u.getInt("Quadra_idQuadra"));
                    des.setData(u.getString("Data"));
                    des.setHora(u.getString("Hora"));
                    des.setJogado(u.getInt("Jogado"));
                    des.setAceitoDesafiado(u.getInt("AceitoDesafiado"));
                    des.setAceitoDesafiador(u.getInt("AceitoDesafiador"));
                    des.setResultTenistaDesafiado1((short) u.getInt("ResultTenistaDesafiado1"));
                    des.setResultTenistaDesafiado2((short) u.getInt("ResultTenistaDesafiado2"));
                    des.setResultTenistaDesafiado3((short) u.getInt("ResultTenistaDesafiado3"));
                    des.setResultTenistaDesafiador1((short) u.getInt("ResultTenistaDesafiador1"));
                    des.setResultTenistaDesafiador2((short) u.getInt("ResultTenistaDesafiador2"));
                    des.setResultTenistaDesafiador3((short) u.getInt("ResultTenistaDesafiador3"));
                    des.setWO((short)u.getInt("WO"));
                    des.setConfirmadoCoordenador(u.getInt("ConfirmadoCoordenador"));
                    des.setConfirmadoDesafiado(u.getInt("ConfirmadoDesafiado"));
                    des.setConfirmadoDesafiador(u.getInt("ConfirmadoDesafiador"));
                    des.setTieBreakDesafiado1((short) u.getInt("tieBreakDesafiado1"));
                    des.setTieBreakDesafiado2((short) u.getInt("tieBreakDesafiado2"));
                    des.setTieBreakDesafiado3((short) u.getInt("tieBreakDesafiado3"));
                    des.setTieBreakDesafiador1((short) u.getInt("tieBreakDesafiador1"));
                    des.setTieBreakDesafiador2((short) u.getInt("tieBreakDesafiador2"));
                    des.setTieBreakDesafiador3((short) u.getInt("tieBreakDesafiador3"));


                    if (tenistaDesc==1) {

                        Log.i("TENISTADESC","ENTROU");
                        ArrayList<Tenista> tenistas;
                        Tenista tempDesafiado;

                        Log.i("DESAFIADO",des.getIdTenistaDesafiado().toString());
                        tenistas = getTenistaBy(2,des.getIdTenistaDesafiado());
                        tempDesafiado = tenistas.get(0);

                         des.setTenistaDesafiado(tempDesafiado);

                        ArrayList<Tenista> tenistas2;

                        Tenista tempDesafiador;


                        Log.i("DESAFIADOR",des.getIdTenistaDesafiador().toString());
                        tenistas2 = getTenistaBy(2,des.getIdTenistaDesafiador());
                        tempDesafiador = tenistas2.get(0);

                        des.setTenistaDesafiador(tempDesafiador);





                    }

                    desafiosArray.add(des);
                }
            }

        }catch (JSONException e) {e.printStackTrace(); }


        return desafiosArray;
    }



    /*
    Se type =   1 -> idUsuario
                2 -> idTenista
                3 -> idCategoria
                0 -> Todos os Tenistas
     */
    public ArrayList<Tenista> getTenistaBy(int type, int id)
    {
        HashMap<String,String> params = new HashMap<String,String>();

        if (type==1)
            params.put("idUsuarios", Integer.toString(id));
        else if (type==2)
            params.put("idTenistas", Integer.toString(id));
        else if (type==3)
            params.put("idCategoria",Integer.toString(id));


        try {
            String http = "http://"+IP+"/tenis/consultaTenista.php";
            JSONObject json = jparser.makeHttpRequest(http, "GET", params);

            if (json == null)
                return null;

            if (json.getInt("result")==1) {
                tenistasJson = json.getJSONArray("tenistas");
                tenistaArray = new ArrayList<Tenista>();

                for (int i=0; i< tenistasJson.length(); i++)
                {
                    JSONObject u = tenistasJson.getJSONObject(i);

                    Tenista temp = new Tenista();
                    Usuario user = new Usuario();

                    temp.setIdTenista(u.getInt("idTenistas"));
                    temp.setIdUsuario(u.getInt("idUsuarios"));
                    temp.setCategoria(u.getInt("Categoria"));
                    temp.setPosicaoAtualRanking(u.getInt("PosicaoAtualRanking"));
                    user.setNome(u.getString("Nome"));
                    user.setEndereco(u.getString("Endereco"));
                    user.setTelefone(u.getString("Telefone"));
                    user.setEmail(u.getString("Email"));
                    user.setNomeusuario(u.getString("NomeUsuario"));
                    temp.setUsuario(user);

                    tenistaArray.add(temp);
                }
            }

        }catch (JSONException e) {e.printStackTrace(); }
        Log.v("JSON","DEPOIS");

        // Log.v("JSON", json.toString());
        return tenistaArray;
    }

    public ArrayList<Tenista> getTenistaByUsuarioID(int id)
    {
        HashMap<String,String> params = new HashMap<String,String>();


        params.put("idUsuarios", Integer.toString(id));


        try {
            String http = "http://"+IP+"/tenis/consultaTenista.php";
            JSONObject json = jparser.makeHttpRequest(http, "GET", params);

            if (json == null)
                return null;

            if (json.getInt("result")==1) {
                tenistasJson = json.getJSONArray("tenistas");
                tenistaArray = new ArrayList<Tenista>();

                for (int i=0; i< tenistasJson.length(); i++)
                {
                    JSONObject u = tenistasJson.getJSONObject(i);

                    Tenista temp = new Tenista();
                    Usuario user = new Usuario();

                    temp.setIdTenista(u.getInt("idTenistas"));
                    temp.setIdUsuario(u.getInt("idUsuarios"));
                    temp.setCategoria(u.getInt("Categoria"));
                    temp.setPosicaoAtualRanking(u.getInt("PosicaoAtualRanking"));
                    user.setNome(u.getString("Nome"));
                    user.setEndereco(u.getString("Endereco"));
                    user.setTelefone(u.getString("Telefone"));
                    user.setEmail(u.getString("Email"));
                    user.setNomeusuario(u.getString("NomeUsuario"));
                    temp.setUsuario(user);

                    tenistaArray.add(temp);
                }
            }

        }catch (JSONException e) {e.printStackTrace(); }
        Log.v("JSON","DEPOIS");

        // Log.v("JSON", json.toString());
        return tenistaArray;
    }



    public ArrayList<Tenista> getTenistasByRankingID(Integer id)
    {
        HashMap<String,String> params = new HashMap<String,String>();


        params.put("idRanking", Integer.toString(id));
        Usuario user = null;


        try {
//            Log.i("RANKING","ANTES JPARSER");

            String http = "http://"+IP+"/tenis/consultaTenistasByRanking.php";
            JSONObject json = jparser.makeHttpRequest(http, "GET", params);
  //          Log.i("RANKING","DEPOIS JPARSER");
            if (json == null)
                return null;

            //Log.i("RANKING","DEPOIS JPARSER NULL");

            if (json.getInt("result")==1) {
                tenistasJson = json.getJSONArray("tenistas");
                tenistaArray = new ArrayList<Tenista>();

                for (int i=0; i< tenistasJson.length(); i++)
                {
                    JSONObject u = tenistasJson.getJSONObject(i);

                    Tenista temp = new Tenista();
                    user = new Usuario();
                    temp.setIdTenista(u.getInt("idTenistas"));
                    temp.setIdUsuario(u.getInt("idUsuarios"));
                    temp.setCategoria(u.getInt("idCategoria"));
                    temp.setPosicaoAtualRanking(u.getInt("PosicaoAtualRanking"));
                    user.setNome(u.getString("Nome"));
                    user.setEndereco(u.getString("Endereco"));
                    user.setTelefone(u.getString("Telefone"));
                    user.setEmail(u.getString("Email"));
                    user.setNomeusuario(u.getString("NomeUsuario"));
                    temp.setUsuario(user);

                    tenistaArray.add(temp);
                }
            }

        }catch (JSONException e) {e.printStackTrace(); }
        //Log.v("RANKING","DEPOIS");

        // Log.v("JSON", json.toString());
        return tenistaArray;
    }



    public ArrayList<RankingHistorico> getRanking(Integer idCat)
    {
        HashMap<String,String> params = new HashMap<String,String>();
        ArrayList<RankingHistorico> rankingArray = new ArrayList<RankingHistorico>();

        params.put("Ultimo", "1");
        params.put("idCategoria",idCat.toString());


        try {
            String http = "http://"+IP+"/tenis/consultaRankingHistorico.php";
            JSONObject json = jparser.makeHttpRequest(http, "GET", params);

            if (json == null)
                return null;

            if (json.getInt("result")==1) {
                tenistasJson = json.getJSONArray("ranking");
                tenistaArray = new ArrayList<Tenista>();

                for (int i=0; i< tenistasJson.length(); i++)
                {
                    JSONObject u = tenistasJson.getJSONObject(i);

                    RankingHistorico temp = new RankingHistorico();

                    temp.setIdRanking(u.getInt("idRankingHistorico"));
                    temp.setData(u.getString("Data"));
                    temp.setHora(u.getString("Hora"));
                    temp.setIdCategoria(u.getInt("Categoria_idCategoria"));
                    temp.setNomeCategoria(u.getString("Nome_Categoria"));

                    rankingArray.add(temp);
                }
            }

        }catch (JSONException e) {e.printStackTrace(); }
        Log.v("JSON","DEPOIS");

        // Log.v("JSON", json.toString());
        return rankingArray;
    }



//retorna todas as categorias
public ArrayList<Categoria> getCategorias()
{
    HashMap<String,String> params = new HashMap<String,String>();
    ArrayList<Categoria> categorias = null;
    JSONArray categoriasArray = null;


    try {

        //params.put("Email","diegofsantos@gmail.com");

        String http = "http://"+IP+"/tenis/consultaCategoria.php";
        JSONObject json = jparser.makeHttpRequest(http, "POST", params);
        if (json != null)
            if (json.getInt("result")==1) {
                categoriasArray = json.getJSONArray("categorias");
                categorias = new ArrayList<Categoria>();

                for (int i=0; i< categoriasArray.length(); i++)
                {
                    JSONObject u = categoriasArray.getJSONObject(i);

                    Categoria temp = new Categoria();
                    temp.setIdCategoria(u.getInt("idCategoria"));
                    temp.setNome(u.getString("Nome"));
                    categorias.add(temp);
                }
            }

    }catch (JSONException e) {e.printStackTrace(); }


    // Log.v("JSON", json.toString());
    return categorias;
}


    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }
}


