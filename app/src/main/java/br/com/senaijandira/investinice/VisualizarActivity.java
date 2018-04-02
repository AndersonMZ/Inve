package br.com.senaijandira.investinice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class VisualizarActivity extends AppCompatActivity {
    DespesaDAO dao = DespesaDAO.getInstance();
    TextView txt_categoria_visualizar,
            txt_valor_visualizar,
            txt_data_visualizar,
            txt_lancamento_visualizar,
            txt_descricao_visualizar;
    Integer id;
    Despesa d;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_categoria_visualizar = findViewById(R.id.txt_categoria_visualizar);
        txt_valor_visualizar = findViewById(R.id.txt_valor_visualizar);
        txt_data_visualizar = findViewById(R.id.txt_data_visualizar);
        txt_lancamento_visualizar = findViewById(R.id.txt_lancamento_visualizar);
        txt_descricao_visualizar = findViewById(R.id.txt_descricao_visualizar);

        Intent intent = getIntent();
        id = intent.getIntExtra("_id", 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lancamentoInformacao();
    }

    public void lancamentoInformacao(){
        d = dao.selecionarUm(this, id);

        NumberFormat f = NumberFormat.getCurrencyInstance(new Locale("pt", "br"));
        txt_categoria_visualizar.setText(d.getCategoria());
        txt_valor_visualizar.setText(f.format(d.getValor()));
        if(d.getData() != null){
            txt_data_visualizar.setText(df.format(d.getData()));
        }else{
            txt_data_visualizar.setText("Sem data definida.");
        }
        txt_lancamento_visualizar.setText(d.getLancamento());
        txt_descricao_visualizar.setText(d.getDescricao());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_visualizar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_excluir){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Excluir");
            builder.setMessage("Tem certeza que deseja excluir ?");

            final Context context = this;
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dao.removerLancamento(context, id);
                    finish();
                }
            });

            builder.setNegativeButton("Não", null);

            builder.create().show();
        }

        if(item.getItemId() == R.id.menu_editar){
            Intent intent = new Intent(this, AdicionarLancamentoActivity.class);
            intent.putExtra("_id", id);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
