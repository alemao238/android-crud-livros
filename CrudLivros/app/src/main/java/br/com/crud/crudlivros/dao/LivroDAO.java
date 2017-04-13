package br.com.crud.crudlivros.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import br.com.crud.crudlivros.model.Livro;

/**
 * Created by Mescla on 12/04/2017.
 */

public class LivroDAO {

    private SQLiteDatabase db;
    private DBOpenHelper dbo;

    public LivroDAO(Context context) {
        dbo = new DBOpenHelper(context);
    }

    private static final String TABELA_LIVRO = "livro";
    private static final String COLUNA_ID = "id";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_AUTOR = "autor";

    public String add(Livro livro) {

        long resultado;
        SQLiteDatabase db = dbo.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, livro.getNome());
        values.put(COLUNA_AUTOR, livro.getAutor());
        resultado = db.insert(TABELA_LIVRO,
                null,
                values);
        db.close();
        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }

    public String update(Livro livro, Long id) {

        long resultado;
        SQLiteDatabase db = dbo.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, livro.getNome());
        values.put(COLUNA_AUTOR, livro.getAutor());
        resultado = db.update(LivroDAO.TABELA_LIVRO, values, LivroDAO.COLUNA_ID + "=" + id, null);

        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }


    public List<Livro> getAll() {
        List<Livro> livros = new LinkedList<>();
        String rawQuery = "SELECT id, nome, autor FROM " +
                LivroDAO.TABELA_LIVRO;
        SQLiteDatabase db = dbo.getReadableDatabase();
        Cursor cursor = db.rawQuery(rawQuery, null);
        Livro livro = null;
        if (cursor.moveToFirst()) {
            do {
                livro = new Livro();
                livro.setId(cursor.getLong(0));
                livro.setNome(cursor.getString(1));
                livro.setAutor(cursor.getString(2));
                livros.add(livro);
            } while (cursor.moveToNext());
        }
        return livros;
    }

    public Livro getLivroById(Long id) {
        SQLiteDatabase db = dbo.getWritableDatabase();
        String query = "SELECT id, nome, autor FROM " + LivroDAO.TABELA_LIVRO + " WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        Livro livro = new Livro();
        livro.setId(cursor.getLong(0));
        livro.setNome(cursor.getString(1));
        livro.setAutor(cursor.getString(2));
        db.close();
        return livro;
    }

    public void deletaLivro(Long id) {
        String where = COLUNA_ID + "=" + id;
        SQLiteDatabase db = dbo.getWritableDatabase();
        db.delete(LivroDAO.TABELA_LIVRO, where, null);

    }

}
