package br.easy.request.projetointegrador.models;

public class ItensEncomenda {
    private int id;
    private Produto produto;
    private int quantidade;
    private double valor;

    public ItensEncomenda(int id, Produto produto, int quantidade, double valor) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public ItensEncomenda(Produto produto, int quantidade, double valor) {
        this(-1, produto,quantidade,valor);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getSubTotal(){
        return quantidade*valor;
    }

    @Override
    public String toString() {
        return "ItensEncomenda{" +
                "id=" + id +
                ", produto=" + produto +
                ", quantidade=" + quantidade +
                ", valor=" + valor +
                '}';
    }
}
