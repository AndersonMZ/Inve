package br.com.senaijandira.investinice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by 17170074 on 21/03/2018.
 */

public class LancamentoDAO {

    private static LancamentoDAO instance;

    public static LancamentoDAO getInstance() {
        if (instance == null){
            instance = new LancamentoDAO();
        }
        return instance;
    }

    public ArrayList<String> tipoLancamento(Context context){
        ArrayList<String> lstLancamento = new ArrayList<>();
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        String sql = "SELECT * from tbl_lancamento";

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            String lancamento = cursor.getString(1);
            lstLancamento.add(lancamento);
        }

        return lstLancamento;
    }
}
