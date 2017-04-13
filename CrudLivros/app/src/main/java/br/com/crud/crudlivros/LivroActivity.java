package br.com.crud.crudlivros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TextInputLayout;

import android.view.View;
import android.widget.EditText;

import br.com.crud.crudlivros.dao.LivroDAO;
import br.com.crud.crudlivros.model.Livro;

public class LivroActivity extends AppCompatActivity {

    public final static int CODE_LIVRO = 1002;
    private TextInputLayout tilNome;
    private TextInputLayout tilAutor;
    private EditText etNome;
    private EditText etAutor;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro);

        tilNome = (TextInputLayout) findViewById(R.id.tilNome);
        tilAutor = (TextInputLayout) findViewById(R.id.tilAutor);

        etNome = (EditText) findViewById(R.id.etNome);
        etAutor = (EditText) findViewById(R.id.etAutor);

        carregarJogo();

    }

    public void cadastrar(View v) {
        final Bundle extras = getIntent().getExtras();
        Long livroId = (extras != null) ? extras.getLong("livroId") : null;
        if (livroId == null) {
            LivroDAO livroDAO = new LivroDAO(this);
            Livro livro = new Livro();
            livro.setNome(tilNome.getEditText().getText().toString());
            livro.setAutor(tilAutor.getEditText().getText().toString());
            livroDAO.add(livro);
        } else {
            Livro livro = new Livro();
            LivroDAO livroDAO = new LivroDAO(this);
            livro.setNome(tilNome.getEditText().getText().toString());
            livro.setAutor(tilAutor.getEditText().getText().toString());
            livroDAO.update(livro, livroId);
        }
        retornaParaTelaAnterior();
    }

    //retorna para tela de lista de torcedores
    public void retornaParaTelaAnterior() {
        Intent intentMessage = new Intent();
        setResult(CODE_LIVRO, intentMessage);
        finish();
    }

    public void carregarJogo() {
        final Bundle extras = getIntent().getExtras();
        Long livroId = (extras != null) ? extras.getLong("livroId") : null;

        if (livroId == null) {
            Livro livro = new Livro();
        } else {
            Livro livro = new Livro();
            LivroDAO livroDAO = new LivroDAO(this);
            livro = livroDAO.getLivroById(livroId);
            etNome.setText(livro.getNome().toString());
            etAutor.setText(livro.getAutor().toString());

        }
    }
}