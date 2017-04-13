package br.com.crud.crudlivros.view.holder;

import android.support.v7.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.com.crud.crudlivros.LivroActivity;
import br.com.crud.crudlivros.R;
import br.com.crud.crudlivros.dao.LivroDAO;
import br.com.crud.crudlivros.model.Livro;
import br.com.crud.crudlivros.view.adapter.LivroAdapter;

/**
 * Created by Mescla on 12/04/2017.
 */

public class LivroViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    public final TextView nome;
    public final TextView autor;
    private Long livroId;
    public final LivroAdapter adapter;


    public LivroViewHolder(final View view, final LivroAdapter adapter) {
        super(view);
        this.adapter = adapter;

        view.setOnLongClickListener(this);

        nome = (TextView) view.findViewById(R.id.tvNome);
        autor = (TextView) view.findViewById(R.id.tvAutor);
    }

    public void preencher(Livro livro) {
        livroId = livro.getId();
        nome.setText(livro.getNome());
        autor.setText(livro.getAutor());
    }

    @Override
    public boolean onLongClick(final View v) {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.getMenuInflater().inflate(R.menu.livro_options, popup.getMenu());

        final Activity context = (Activity)v.getContext();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.menuEditar:

                        final Intent intent = new Intent(context, LivroActivity.class);
                        intent.putExtra("livroId", livroId);
                        context.startActivityForResult(intent, LivroActivity.CODE_LIVRO);

                        break;

                    case R.id.menuDeletar:
                        LivroDAO livroDAO = new LivroDAO(context);
                        livroDAO.deletaLivro(livroId);
                        deletarLivro();
                        break;

                }

                return true;
            }
        });

        popup.show();
        return false;
    }


    public void deletarLivro(){
        adapter.remove(getAdapterPosition());
    }

}
