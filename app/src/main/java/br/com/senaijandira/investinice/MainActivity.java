package br.com.senaijandira.investinice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    TextView txt_receita, txt_despesa, txt_saldo;
    DespesaDAO dao = DespesaDAO.getInstance();
    LancamentoAdapter adapter;
    ListView list_view_lancamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list_view_lancamento = findViewById(R.id.list_view_lancamento);
        txt_receita = findViewById(R.id.txt_receita);
        txt_despesa = findViewById(R.id.txt_despesa);
        txt_saldo = findViewById(R.id.txt_saldo);

        adapter = new LancamentoAdapter(this, new ArrayList<Despesa>());
        list_view_lancamento.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        grafico();
        lancamentos();
        telaResumo();
    }

    private void grafico() {
        Double entradaGrafico = dao.saldo(this);
        Double despesaGrafico = dao.despesa(this);
        List<PieEntry> dadosGrafico = new ArrayList<>();
        dadosGrafico.add(new PieEntry(Float.valueOf(Float.parseFloat(entradaGrafico.toString())), "Entrada"));
        dadosGrafico.add(new PieEntry(Float.valueOf(Float.parseFloat(despesaGrafico.toString())),"Despesa"));

        PieDataSet inserirDados = new PieDataSet(dadosGrafico, "Resumo");

        PieData data = new PieData(inserirDados);

        PieChart pieChart = findViewById(R.id.piechart);
        pieChart.setData(data);
        pieChart.invalidate();
    }

    public void telaResumo(){
        NumberFormat f = NumberFormat.getCurrencyInstance(new Locale("pt", "br"));
        txt_receita.setText(f.format(dao.saldo(this)));
        txt_receita.setTextColor(getResources().getColor(R.color.colorGreen));
        txt_despesa.setText(f.format(dao.despesa(this)));
        txt_despesa.setTextColor(getResources().getColor(R.color.colorAccent));
        if(dao.saldo(this) - dao.despesa(this) < 0){
            txt_saldo.setTextColor(getResources().getColor(R.color.colorAccent));
        }else{
            txt_saldo.setTextColor(getResources().getColor(R.color.colorGreen));
        }
        txt_saldo.setText(f.format(dao.saldo(this) - dao.despesa(this)));

    }
    public void lancamentos(){

        /* LISTA */
        ArrayList<Despesa> lancamentos;
        lancamentos = dao.selecionarTodos(this);

        adapter.clear();
        adapter.addAll(lancamentos);
    }

    public void mostrarLancamento(View v){
        Intent intent = new Intent(getApplicationContext(), LancamentosActivity.class);

        startActivity(intent);
    }

    public void mostrarGraficos(View v){
        Intent intent = new Intent(getApplicationContext(), GraficoActivity.class);

        startActivity(intent);
    }

    public void adicionarReceita(View v){
        Intent intent = new Intent(getApplicationContext(), Receita.class);

        startActivity(intent);
    }

}
