package br.com.crud.crudlivros.model;

/**
 * Created by Mescla on 12/04/2017.
 */

public class Livro {

    private Long id;
    private String nome;
    private String autor;

    public Livro() {

    }

    public Livro(Long id, String nome, String autor) {
        this.id = id;
        this.nome = nome;
        this.autor = autor;
    }

    public Livro(String nome, String autor) {
        this.nome = nome;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
