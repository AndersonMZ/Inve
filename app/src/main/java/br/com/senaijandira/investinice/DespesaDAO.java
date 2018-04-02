package br.com.senaijandira.investinice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.ArrayRes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 17170074 on 21/03/2018.
 */

public class DespesaDAO {

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    Date data;

    private static DespesaDAO instance;


    public static DespesaDAO getInstance() {
        if (instance == null){
            instance = new DespesaDAO();
        }
        return instance;
    }
// TODO:Tentativa de grafico especifico
//    public ArrayList<String> graficoCategoria(Context context){
//        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
//        String sql = "select c.nomeCategoria as categoria from tbl_despesa as d " +
//                "inner join tbl_categoria as c on c._idCategoria = d.idCategoria;";
//        Cursor cursor = db.rawQuery(sql, null);
//        ArrayList<String> lstCategoria = new ArrayList<>();
//        while (cursor.moveToNext()){
//            String categoria = cursor.getString(0);
//            lstCategoria.add(categoria);
//        }
//
//        return lstCategoria;
//    }
//
//    public ArrayList<Double> graficoValor(Context context){
//        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
//        String sql = "select valor from tbl_despesa;";
//        Cursor cursor = db.rawQuery(sql, null);
//        ArrayList<Double> lstValor = new ArrayList<>();
//        while (cursor.moveToNext()){
//            Double valor = cursor.getDouble(0);
//            lstValor.add(valor);
//        }
//        return lstValor;
//    }
//
//    public ArrayList<String> graficoLancamento(Context context){
//        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
//        String sql = "select l.tipoLancamento as lancamento from tbl_despesa as d " +
//                "inner join tbl_lancamento as l on l._idLancamento = d.idLancamento;";
//        Cursor cursor = db.rawQuery(sql, null);
//        ArrayList<String> lstLancamento = new ArrayList<>();
//        while (cursor.moveToNext()){
//            String lancamento = cursor.getString(0);
//            lstLancamento.add(lancamento);
//        }
//
//        return lstLancamento;
//    }

    public ArrayList<Despesa> selecionarTodos(Context context) {
        ArrayList<Despesa> retorno = new ArrayList<>();
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        String sql = "select _id, idLancamento, idCategoria, valor, data, descricao, c.nomeCategoria as categoria, l.tipoLancamento as lancamento " +
                "from tbl_despesa as d " +
                "inner join tbl_categoria as c on c._idCategoria = d.idCategoria " +
                "inner join tbl_lancamento as l on l._idLancamento = d.idLancamento;";
        Cursor cursor = db.rawQuery(sql, null);


        while (cursor.moveToNext()){
            Despesa d = new Despesa();
            d.setId(cursor.getInt(0));
            d.setIdLancamento(cursor.getInt(1));
            d.setIdCategoria(cursor.getInt(2));
            d.setValor(cursor.getDouble(3));

            if(cursor.getString(4) != null){
                String dataString = cursor.getString(4);
                try {
                    data = df.parse(dataString);
                    d.setData(data);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            d.setDescricao(cursor.getString(5));
            d.setCategoria(cursor.getString(6));
            d.setLancamento(cursor.getString(7));

            retorno.add(d);
        }
        return retorno;
    }

    public Despesa selecionarUm(Context context, int id){
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        String sql ="select _id as id, idLancamento, idCategoria, valor, data, descricao, c.nomeCategoria as categoria, l.tipoLancamento as lancamento " +
                "from tbl_despesa as d " +
                "inner join tbl_categoria as c on c._idCategoria = d.idCategoria " +
                "inner join tbl_lancamento as l on l._idLancamento = d.idLancamento " +
                "WHERE _id =" + id + ";";
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.getCount() > 0){
            cursor.moveToNext();
            Despesa d = new Despesa();

            if(cursor.getString(4) != null){
                String dataString = cursor.getString(4);
                try {
                    data = df.parse(dataString);
                    d.setData(data);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            d.setId(cursor.getInt(0));
            d.setIdLancamento(cursor.getInt(1));
            d.setIdCategoria(cursor.getInt(2));
            d.setValor(cursor.getDouble(3));
            d.setDescricao(cursor.getString(5));
            d.setCategoria(cursor.getString(6));
            d.setLancamento(cursor.getString(7));

            return d;
        }
        return null;
    }

    public Boolean inserir(Context context,Despesa d){
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues valores = new ContentValues();

        if(d.getData() != null){
            valores.put("data", df.format(d.getData()));
        }
        valores.put("idCategoria", d.getIdCategoria());
        valores.put("idLancamento", d.getIdLancamento());
        valores.put("valor", d.getValor());
        valores.put("descricao", d.getDescricao());
        Long id = db.insert("tbl_despesa", null, valores);
        if(id != -1){
            return true;
        }else{
            return false;
        }
    }

    public Double saldo(Context context){
        Double receita = 0.0;
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        String sql = "select * from tbl_despesa;";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            if(cursor.getInt(1) == 1){
                receita += Double.valueOf(cursor.getDouble(3));
            }
        }
        return receita;
    }

    public Double despesa(Context context){
        Double despesa = 0.0;
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        String sql = "select * from tbl_despesa;";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            if(cursor.getInt(1) == 2){
                despesa += Double.valueOf(cursor.getDouble(3));
            }
        }
        return despesa;
    }

    public Boolean removerLancamento(Context context, Integer id){
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        db.delete("tbl_despesa", "_id = ?", new String[]{id.toString()});

        return true;
    }

    public Boolean atualizarLancamento(Context context, Despesa d){
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("valor", d.getValor());
        if(d.getData() != null){
            valores.put("data", df.format(d.getData()));
        }
        valores.put("idCategoria", d.getIdCategoria());
        valores.put("idLancamento", d.getIdLancamento());
        valores.put("descricao", d.getDescricao());

        db.update("tbl_despesa", valores, "_id = ?", new String[]{d.getId().toString()});
        return true;
    }
}
