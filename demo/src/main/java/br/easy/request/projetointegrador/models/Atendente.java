package br.easy.request.projetointegrador.models;

public class Atendente {

    private int idAtendente;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    public Atendente(int idAtendente, String nome, String email, String cpf, String telefone) {
        this.idAtendente = idAtendente;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public int getIdAtendente() {
        return idAtendente;
    }

    public void setId(int id) {
        this.idAtendente = id;
    }

    public Atendente(String nome, String email, String cpf, String telefone){
        this(-1, nome, email, cpf, telefone);

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String toString() {
        return "Nome: "+nome+"\nE-mail: "+email+"\nCPF: "+cpf+"\nTelefone: "+telefone;
    }
    
}
