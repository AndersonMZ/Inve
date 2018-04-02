package br.com.senaijandira.investinice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdicionarLancamentoActivity extends AppCompatActivity{

    ArrayList<String> items_spinner = new ArrayList<>();
    ArrayList<String> items_spinner_tipo = new ArrayList<>();
    Spinner spin_escolha, spin;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    Date data;
    DespesaDAO dao = DespesaDAO.getInstance();
    CategoriaDAO cateDAO = CategoriaDAO.getInstance();
    LancamentoDAO lancaDAO = LancamentoDAO.getInstance();
    Despesa despesa;
    Despesa d;
    Boolean modoEdicao = false;
    EditText txt_despesa_lancamento, txt_data, txt_descricao;
    Integer posicaoCategoria, posicaoLancamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_lancamento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spin = findViewById(R.id.spinner);
        spin_escolha = findViewById(R.id.spinner_escolha);
        txt_despesa_lancamento = findViewById(R.id.txt_despesa_lancamento);
        txt_descricao = findViewById(R.id.txt_descricao);
        txt_data = findViewById(R.id.txt_data);

    }

    @Override
    protected void onResume() {
        super.onResume();

        items_spinner = cateDAO.tipoCategoria(this);
        items_spinner_tipo = lancaDAO.tipoLancamento(this);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, items_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        ArrayAdapter adapterEscolha = new ArrayAdapter(this,android.R.layout.simple_spinner_item, items_spinner_tipo);
        adapterEscolha.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_escolha.setAdapter(adapterEscolha);

        Integer id = getIntent().getIntExtra("_id", 0);
        if(id != 0){
            modoEdicao = true;
            despesa = DespesaDAO.getInstance().selecionarUm(this, id);
            if(despesa.getData() != null){
                txt_data.setText(df.format(despesa.getData()));
            }
            if(despesa.getDescricao() != null){
                txt_descricao.setText(despesa.getDescricao());
            }
            txt_despesa_lancamento.setText(despesa.getValor().toString());
            posicaoCategoria = adapter.getPosition(despesa.getCategoria());
            posicaoLancamento = adapterEscolha.getPosition(despesa.getLancamento());
            spin.setSelection(posicaoCategoria);
            spin_escolha.setSelection(posicaoLancamento);

//            spin.setSelection(despesa.getIdCategoria(), true);
//            spin_escolha.setSelection(despesa.getIdLancamento(), true);
        }else{
            modoEdicao = false;
        }
    }

    public void inserirLancamento(View view){
        if(modoEdicao){
            d = despesa;
        }else{
            d = new Despesa();
        }

        try {
            String dt_nasc = txt_data.getText().toString();
            data = df.parse(dt_nasc);
            d.setData(data);
        } catch (ParseException e) {
            e.printStackTrace();
            txt_data.setError("Insira uma data certa.");
            return;
        }

        if(txt_descricao.getText().toString().isEmpty()){
            txt_descricao.setError("Preencha o campo em branco");
            return;
        }else{
            d.setDescricao(txt_descricao.getText().toString());
        }

        if(txt_despesa_lancamento.getText().toString().isEmpty()){
            txt_despesa_lancamento.setError("Preencha o campo em branco");
            return;
        }else{
            d.setValor(Double.valueOf(txt_despesa_lancamento.getText().toString()));
        }

        if(txt_data.getText().toString().isEmpty()){
            txt_data.setError("Preencha o campo em branco");
            return;
        }

        String categoria = spin.getSelectedItem().toString();
        Integer idCategoria = cateDAO.identificarCategoria(this, categoria);
        d.setIdCategoria(idCategoria);
//        if(spin.getSelectedItem().equals("Lazer")){
//            d.setIdCategoria(2);
//        }else if(spin.getSelectedItem().equals("Transporte")){
//            d.setIdCategoria(3);
//        }else if(spin.getSelectedItem().equals("Saúde")){
//            d.setIdCategoria(4);
//        }else if(spin.getSelectedItem().equals("Moradia")){
//            d.setIdCategoria(5);
//        }else if(spin.getSelectedItem().equals("Salário")){
//            d.setIdCategoria(6);
//        }else{
//            d.setIdCategoria(7);
//        }

        if(spin_escolha.getSelectedItem().toString().equals("Despesa")){
            d.setIdLancamento(2);
        }else{
            d.setIdLancamento(1);
        }

        if(modoEdicao){
            dao.atualizarLancamento(this, d);
            Toast.makeText(this, "Lançamento atualizado.", Toast.LENGTH_SHORT).show();
        }else{
            dao.inserir(this, d);
            Toast.makeText(this, "Lançamento inserido com sucesso.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void activityCategoria(View view){
        Intent intent = new Intent(getApplicationContext(), CategoriaActivity.class);
        startActivity(intent);
    }


}
