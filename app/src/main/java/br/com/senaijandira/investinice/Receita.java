package br.com.senaijandira.investinice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Receita extends AppCompatActivity {

    ArrayList<String> items_spinner = new ArrayList<>();
    EditText inserir_receita;
    DespesaDAO dao = DespesaDAO.getInstance();
    Spinner spin;
    LancamentoDAO lancaDAO = LancamentoDAO.getInstance();
    Double receita;
    TextView txt_receita_atual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spin = findViewById(R.id.spinner_receita);
        inserir_receita = findViewById(R.id.inserir_receita);
        txt_receita_atual = findViewById(R.id.txt_receita_atual);

        items_spinner = lancaDAO.tipoLancamento(this);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, items_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        atualizarReceita();
    }

    public void inserirReceita(View view){
        Despesa d = new Despesa();

        if(inserir_receita.getText().toString().isEmpty()){
           inserir_receita.setError("Preencha o campo");
           return;
        }else{
            receita = Double.valueOf(inserir_receita.getText().toString());
            d.setValor(receita);
        }

        d.setIdCategoria(1);
        if(spin.getSelectedItem().toString().equals("Despesa")){
            d.setIdLancamento(2);
        }else{
            d.setIdLancamento(1);
        }

        dao.inserir(this, d);
        finish();
    }

    public void atualizarReceita(){
        NumberFormat f = NumberFormat.getCurrencyInstance(new Locale("pt", "br"));
        txt_receita_atual.setText(f.format(dao.saldo(this)));
    }

}
