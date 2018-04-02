package br.com.senaijandira.investinice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class AdicionarCategoriaActivity extends AppCompatActivity {
    EditText categoria_nova;
    ArrayList<String> todas_categorias = new ArrayList<>();
    CategoriaDAO cateDAO = CategoriaDAO.getInstance();
    Boolean modoEdicao = false;
    Categoria c;
    Categoria categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_categoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoria_nova = findViewById(R.id.txt_categoria_nova);

        categoria_nova.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int comeco, int fim, Spanned dmc, int dcomeco, int dfim) {
                if(charSequence.equals("")){
                    return charSequence;
                }
                if(charSequence.toString().matches("[a-zA-Z ]+")){
                    return charSequence;
                }
                return "";
            }
        }});
    }

    @Override
    protected void onResume() {
        super.onResume();
        Integer id = getIntent().getIntExtra("id", 0);
        if(id != 0){
            modoEdicao = true;
            categoria = cateDAO.selecionarUm(this, id);
            categoria_nova.setText(c.getNomeCategoria());
        }else {
            modoEdicao = false;
        }
    }

    public void adicionarCategoria(View view){
        if(modoEdicao){
            c = categoria;
        }else{
            c = new Categoria();
        }
        c.setNomeCategoria(categoria_nova.getText().toString());

        todas_categorias = cateDAO.tipoCategoria(this);

        if(categoria_nova.getText().toString().isEmpty()){
            categoria_nova.setError("Insira uma categoria");
            return;
        }
        if(todas_categorias.toString().toLowerCase().contains(categoria_nova.getText().toString().toLowerCase().replace(" ", ""))){
            categoria_nova.setError("Categoria já existente");
            return;
        }else{
            c.setNomeCategoria(categoria_nova.getText().toString());
            cateDAO.inserir(this, c);
        }
        finish();
    }

}
