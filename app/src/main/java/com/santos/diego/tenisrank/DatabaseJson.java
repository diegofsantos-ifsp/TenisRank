package com.santos.diego.tenisrank;

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

    JSONParser jparser = new JSONParser();

    //ArrayList<HashMap<String,String>> usuariosArray;
    ArrayList<Usuario> usuarioArray;
    ArrayList<Tenista> tenistaArray;

    JSONArray usuariosJson = null;
    JSONArray tenistasJson = null;

    String error;


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

            JSONObject json = jparser.makeHttpRequest("http://192.168.0.14/tenis/consultaUsuario.php", "POST", params);
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

            JSONObject json = jparser.makeHttpRequest("http://192.168.0.14/tenis/consultaUsuario.php", "POST", params);

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
            JSONObject json = jparser.makeHttpRequest("http://192.168.0.14/tenis/consultaUsuario.php", "POST", params);

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
            JSONObject json = jparser.makeHttpRequest("http://192.168.0.14/tenis/consultaUsuario.php", "POST", params);

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
    public ArrayList<Desafio> getJogosByTenista(int type, int idTen)
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
            JSONObject json = jparser.makeHttpRequest("http://192.168.0.14/tenis/consultaJogoByTenista.php", "GET", params);

            if (json == null)
                return null;

            if (json.getInt("result")==1) {
                desafiosJson = json.getJSONArray("desafios");
                desafiosArray = new ArrayList<Desafio>();

                for (int i=0; i< desafiosJson.length(); i++)
                {
                    JSONObject u = desafiosJson.getJSONObject(i);

                    Tenista temp = new Tenista();
                    Usuario user = new Usuario();
                    Desafio des = new Desafio();

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
            params.put("idTenista", Integer.toString(id));
        else if (type==3)
            params.put("idCategoria",Integer.toString(id));


        try {
            JSONObject json = jparser.makeHttpRequest("http://192.168.0.14/tenis/consultaTenista.php", "GET", params);

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
            JSONObject json = jparser.makeHttpRequest("http://192.168.0.14/tenis/consultaTenista.php", "GET", params);

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
            JSONObject json = jparser.makeHttpRequest("http://192.168.0.14/tenis/consultaTenistasByRanking.php", "GET", params);
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
            JSONObject json = jparser.makeHttpRequest("http://192.168.0.14/tenis/consultaRankingHistorico.php", "GET", params);

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

        JSONObject json = jparser.makeHttpRequest("http://192.168.0.14/tenis/consultaCategoria.php", "POST", params);
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



}


