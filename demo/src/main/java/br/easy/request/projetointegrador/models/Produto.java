package br.easy.request.projetointegrador.models;

public class Produto {
    private int idProduto;
    private String nome;
    private Double valorUnitario;
    
    public Produto(int idProduto, String nome, Double valorUnitario) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.valorUnitario = valorUnitario;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setId(int id) {
        this.idProduto = id;
    }

    public Produto(String nome, Double valorUnitario) {
        this(-1, nome, valorUnitario);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String toString(){
        return "ID:" + idProduto+"\nNome: "+nome+"\nValor unitário: "+valorUnitario;
    }
    
}
