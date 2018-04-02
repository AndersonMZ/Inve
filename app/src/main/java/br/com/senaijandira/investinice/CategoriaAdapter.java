package br.com.senaijandira.investinice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoriaAdapter extends ArrayAdapter<Categoria>{
    public CategoriaAdapter(Context ctx, ArrayList<Categoria> lst) {
        super(ctx, 0, lst);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View converView, @NonNull ViewGroup parent) {
        View v = converView;

        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_categoria, null);
        }

        Categoria item = getItem(position);

        TextView txt_categoria = v.findViewById(R.id.lbl_categoria);

        txt_categoria.setText(item.getNomeCategoria());

        return v;
    }
}
