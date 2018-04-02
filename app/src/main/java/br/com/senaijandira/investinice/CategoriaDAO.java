package br.com.senaijandira.investinice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by 17170074 on 21/03/2018.
 */

public class CategoriaDAO {

    private static CategoriaDAO instance;

    public static CategoriaDAO getInstance() {
        if (instance == null){
            instance = new CategoriaDAO();
        }
        return instance;
    }

    public Categoria selecionarUm(Context context, int id){
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        String sql = "SELECT * FROM tbl_categoria where _idCategoria =" + id + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0){
            cursor.moveToNext();
            Categoria c = new Categoria();
            c.setNomeCategoria(cursor.getString(1));

            return c;
        }

        return null;
    }

    public Integer identificarCategoria (Context context, String categoria){
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        String sql = "SELECT * from tbl_categoria where nomeCategoria = '" + categoria + "';";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.getCount() > 0){
            cursor.moveToNext();
            Integer idCategoria;

            idCategoria = cursor.getInt(0);
            return idCategoria;
        }
        return null;
    }

    public Boolean excluirCategoria(Context context, Integer id){
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();
        db.delete("tbl_categoria", "_idCategoria = ?", new String[]{id.toString()});

        return true;
    }

    public Boolean exclusaoCategoria(Context context, Integer idCategoria){
        Boolean exclusao;
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        String sql = "SELECT idCategoria from tbl_despesa where idCategoria =" + idCategoria + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.getCount() > 0) {
            cursor.moveToNext();
            Integer id = cursor.getInt(0);
            exclusao = true;
        }else{
            exclusao = false;
        }
        return exclusao;
    }

    public ArrayList<Categoria> selecionarTodos(Context context) {
        ArrayList<Categoria> retorno = new ArrayList<>();
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        String sql = "select * from tbl_categoria;";
        Cursor cursor = db.rawQuery(sql, null);


        while (cursor.moveToNext()){
            Categoria c = new Categoria();
            c.setNomeCategoria(cursor.getString(1));
            retorno.add(c);
        }
        return retorno;
    }

    public ArrayList<String> tipoCategoria(Context context){
        ArrayList<String> lstCategoria = new ArrayList<>();
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        String sql = "SELECT * FROM tbl_categoria";

        Cursor cursor = db.rawQuery(sql,null);

        while (cursor.moveToNext()){
            String categoria = cursor.getString( 1);
            lstCategoria.add(categoria);
        }

        return lstCategoria;
    }

    public Boolean inserir(Context context, Categoria c){
        //ACESSAR O BANO EM MODO ESCRITA
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nomeCategoria", c.getNomeCategoria());
        Long id = db.insert("tbl_categoria", null, valores);
        if(id != -1){
            return true;
        }else{
            return false;
        }
    }

}
