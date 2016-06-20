package com.example.todeolho.myapplication.classes;

/**
 * Created by alan_ on 20/06/2016.
 */
public class Pessoa {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private int id;
    private String nome;
    private String email;

}
