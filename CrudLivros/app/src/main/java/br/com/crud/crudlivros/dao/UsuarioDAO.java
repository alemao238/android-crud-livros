package br.com.crud.crudlivros.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.crud.crudlivros.model.Livro;
import br.com.crud.crudlivros.model.Usuario;

/**
 * Created by Mescla on 13/04/2017.
 */

public class UsuarioDAO {


    private SQLiteDatabase db;
    private DBOpenHelper dbo;

    public UsuarioDAO(Context context) {
        dbo = new DBOpenHelper(context);
    }

    private static final String TABELA_USUARIO = "usuario";
    private static final String COLUNA_ID = "id";
    private static final String COLUNA_LOGIN = "login";
    private static final String COLUNA_SENHA = "senha";

    public String add(Usuario usuario) {

        long resultado;
        SQLiteDatabase db = dbo.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_LOGIN, usuario.getLogin());
        values.put(COLUNA_SENHA, usuario.getSenha());
        resultado = db.insert(TABELA_USUARIO,
                null,
                values);
        db.close();
        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }

    public Usuario findUsuario(String login, String senha) {
        SQLiteDatabase db = dbo.getWritableDatabase();
        String query = "SELECT id, login, senha FROM " + UsuarioDAO.TABELA_USUARIO + " WHERE login = ? AND senha = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(login), String.valueOf(senha)});
        cursor.moveToFirst();
        Usuario usuario = new Usuario();
        usuario.setId(cursor.getLong(0));
        usuario.setLogin(cursor.getString(1));
        usuario.setSenha(cursor.getString(2));
        db.close();
        return usuario;

    }



}
