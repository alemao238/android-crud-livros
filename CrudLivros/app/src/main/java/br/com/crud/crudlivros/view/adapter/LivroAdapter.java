package br.com.crud.crudlivros.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.crud.crudlivros.R;
import br.com.crud.crudlivros.model.Livro;
import br.com.crud.crudlivros.view.holder.LivroViewHolder;

/**
 * Created by Mescla on 12/04/2017.
 */

public class LivroAdapter extends RecyclerView.Adapter {

    private List<Livro> livros;
    private Context context;

    public LivroAdapter(List<Livro> livros, Context context) {
        this.livros = livros;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.livro_item_lista, parent, false);
        LivroViewHolder holder = new LivroViewHolder(view, this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        LivroViewHolder viewHolder = (LivroViewHolder) holder;

        Livro livro  = livros.get(position);

        ((LivroViewHolder) holder).preencher(livro);

    }

    @Override
    public int getItemCount() {
        return livros.size();
    }

    public void remove(int position) {
        livros.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

}
