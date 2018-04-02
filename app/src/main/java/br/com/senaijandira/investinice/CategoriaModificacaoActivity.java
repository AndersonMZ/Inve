package br.com.senaijandira.investinice;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CategoriaModificacaoActivity extends AppCompatActivity {
    TextView txt_categoria_atual;
    CategoriaDAO dao = CategoriaDAO.getInstance();
    Categoria c;
    Integer id;
    Boolean exclusao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_modificacao);

        txt_categoria_atual = findViewById(R.id.txt_categoria_atual);
//        TODO: id não retornando
        id = getIntent().getIntExtra("_idCategoria", 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        categoriaAtual();
    }

    public void categoriaAtual(){
        c = dao.selecionarUm(this, id);
        txt_categoria_atual.setText(c.getNomeCategoria());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_visualizar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_excluir){
            exclusao = dao.exclusaoCategoria(this, id);
            if(exclusao != false){
                dao.excluirCategoria(this, id);
                finish();
            }else{
                Toast.makeText(this,"Exclua os lançamentos dessa categoria.", Toast.LENGTH_SHORT).show();
            }
        }

        if(item.getItemId() == R.id.menu_editar){
            Intent intent = new Intent(getApplicationContext(), AdicionarCategoriaActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
