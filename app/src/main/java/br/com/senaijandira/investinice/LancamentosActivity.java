package br.com.senaijandira.investinice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class LancamentosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView list_view_lancamentos_geral;
    DespesaDAO dao = DespesaDAO.getInstance();
    LancamentoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancamentos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view_lancamentos_geral = findViewById(R.id.list_view_lancamentos_geral);
        adapter = new LancamentoAdapter(this,new ArrayList<Despesa>());
        list_view_lancamentos_geral.setAdapter(adapter);
        list_view_lancamentos_geral.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        lancamentos();
        super.onResume();
    }

    public void lancamentos(){
        ArrayList<Despesa> lancamentos;
        lancamentos = dao.selecionarTodos(this);
        adapter.clear();
        adapter.addAll(lancamentos);
    }
    public void adicionarLancamento(View v){
        Intent intent = new Intent(getApplicationContext(), AdicionarLancamentoActivity.class);

        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Despesa item = adapter.getItem(i);

        Intent intent = new Intent(getApplicationContext(), VisualizarActivity.class);
        intent.putExtra("_id", item.getId());

        startActivity(intent);
    }

}
