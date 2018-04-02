package br.com.senaijandira.investinice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 17170074 on 21/03/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    //NOME DO BANCO
    private static String DB_NAME = "investimento.db";
    //VERSÃO DO BANCO
    private static int DB_VERSION = 1;

    //CONSTRUTOR DA CLASSE PARA CRIAÇÃO DO BANCO DE DADOS
    public DbHelper(Context ctx){
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    /* CRIAÇÃO DO BANCO
    * TABELAS
    * ESTRUTURA INICIAL
    */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table tbl_categoria(_idCategoria INTEGER primary key autoincrement, nomeCategoria TEXT);");
        db.execSQL("create table tbl_lancamento(_idLancamento INTEGER primary key autoincrement, tipoLancamento TEXT);");

        db.execSQL("create table tbl_despesa(_id INTEGER primary key autoincrement, " +
                "idLancamento INTEGER NOT NULL, " +
                "idCategoria INTEGER NOT NULL, " +
                "valor DOUBLE, " +
                "data DATE," +
                "descricao TEXT, " +
                "foreign key (idLancamento) references tbl_tipo_lancamento(_idLancamento), " +
                "foreign key (idCategoria) references tbl_categoria(_idCategoria)" +
                ");"
        );

        db.execSQL("insert into tbl_lancamento(tipoLancamento) values('Entrada');");
        db.execSQL("insert into tbl_lancamento(tipoLancamento) values('Despesa');");

        db.execSQL("insert into tbl_categoria(nomeCategoria) values('Geral');");
        db.execSQL("insert into tbl_categoria(nomeCategoria) values('Lazer');");
        db.execSQL("insert into tbl_categoria(nomeCategoria) values('Transporte');");
        db.execSQL("insert into tbl_categoria(nomeCategoria) values('Saúde');");
        db.execSQL("insert into tbl_categoria(nomeCategoria) values('Moradia');");
        db.execSQL("insert into tbl_categoria(nomeCategoria) values('Salário');");
        db.execSQL("insert into tbl_categoria(nomeCategoria) values('Outros');");
    }

    //    ONDE SERÁ REALIZADOS OS UPGRADES DO BANCO
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table tbl_categoria;");
        onCreate(db);
        db.execSQL("drop table tbl_lancamento;");
        onCreate(db);
        db.execSQL("drop table tbl_despesa;");
        onCreate(db);
    }

}
