package br.com.senaijandira.investinice;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class CategoriaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView list_view_categoria_geral;
    CategoriaAdapter adapter;
    CategoriaDAO dao = CategoriaDAO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view_categoria_geral = findViewById(R.id.list_view_categoria_geral);
        adapter = new CategoriaAdapter(this, new ArrayList<Categoria>());
        list_view_categoria_geral.setAdapter(adapter);
        list_view_categoria_geral.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        categorias();
    }

    public void novaCategoria(View view){
        Intent intent = new Intent(getApplicationContext(), AdicionarCategoriaActivity.class);
        startActivity(intent);
    }

    public void categorias(){
        ArrayList<Categoria> categorias;
        categorias = dao.selecionarTodos(this);
        adapter.clear();
        adapter.addAll(categorias);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Categoria item = adapter.getItem(i);

        Intent intent = new Intent(getApplicationContext(), CategoriaModificacaoActivity.class);
        intent.putExtra("_idCategoria", item.getId());

        startActivity(intent);
    }
}
