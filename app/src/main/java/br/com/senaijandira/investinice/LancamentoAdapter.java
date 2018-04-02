package br.com.senaijandira.investinice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by PC on 30/03/2018.
 */

public class LancamentoAdapter extends ArrayAdapter<Despesa>{
    public LancamentoAdapter(Context ctx, ArrayList<Despesa> lst){
        super(ctx, 0, lst);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View converView, @NonNull ViewGroup parent){
        View v = converView;

        NumberFormat f = NumberFormat.getCurrencyInstance(new Locale("pt", "br"));
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item, null);
        }

        Despesa item = getItem(position);

        TextView txt_categoria_lancamento = v.findViewById(R.id.lbl_categoria_lancamento);
        TextView txt_valor_lancamento = v.findViewById(R.id.lbl_valor_lancamento);
        TextView txt_data_lancamento = v.findViewById(R.id.lbl_data_lancamento);

        txt_categoria_lancamento.setText(item.getCategoria());
//        SEM INSERT RELACIONAL
//        switch (item.getIdCategoria()){
//            case 1:
//                txt_categoria_lancamento.setText("Entrada ou despesa comum");
//                break;
//            case 2:
//                txt_categoria_lancamento.setText("Lazer");
//                break;
//            case 3:
//                txt_categoria_lancamento.setText("Transporte");
//                break;
//            case 4:
//                txt_categoria_lancamento.setText("Saúde");
//                break;
//            case 5:
//                txt_categoria_lancamento.setText("Moradia");
//                break;
//            case 6:
//                txt_categoria_lancamento.setText("Salário");
//                break;
//            case 7:
//                txt_categoria_lancamento.setText("Outros");
//                break;
//            default:
//                break;
//        }

        txt_valor_lancamento.setText(f.format(item.getValor()));
        if(item.getData() != null){
            txt_data_lancamento.setText(df.format(item.getData()));
        }else{
            txt_data_lancamento.setText("Sem data definida.");
        }
        return v;
    }

}
