package br.senai.sc.sistemaeventos.modelo;

import java.io.Serializable;

public class Evento implements Serializable {
    private int id;
    private String nome;
    private String local;
    private String data;

    public Evento(int id, String nome, String local, String data) {
        this.id = id;
        this.nome = nome;
        this.local = local;
        this.data = data;
    }

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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toString() {
        return id + " - Nome do evento: " + nome + "\n  Local do Evento: " + local + "\n  Data do Evento: " + data;
    }
}
