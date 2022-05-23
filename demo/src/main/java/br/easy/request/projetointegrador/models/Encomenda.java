package br.easy.request.projetointegrador.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Encomenda {

    private int idEncomenda;
    private LocalDateTime dataVenda;
    private LocalDate dataEntrega;
    private String hora;
    private String descricao;
    private double valorTotal;
    private ArrayList<ItensEncomenda> itens;
    private Cliente cliente;
    private Atendente atendente;

    public Encomenda(int idEncomenda, LocalDateTime dataVenda, LocalDate dataEntrega,String hora, String descricao, double valorTotal, ArrayList<ItensEncomenda> itens, Cliente cliente, Atendente atendente) {
        this.idEncomenda = idEncomenda;
        this.dataVenda = dataVenda;
        this.dataEntrega = dataEntrega;
        this.hora = hora;
        this.descricao = descricao;
        this.valorTotal = valorTotal;
        this.itens = itens;
        this.cliente = cliente;
        this.atendente = atendente;
    }

    public Encomenda(LocalDateTime dataVenda,Cliente cliente, Atendente atendente) {
        this(-1,dataVenda,null,null,null,0.0,new ArrayList<>(),cliente,atendente);
    }

    public int getIdEncomenda() {
        return idEncomenda;
    }

    public void setIdEncomenda(int idEncomenda) {
        this.idEncomenda = idEncomenda;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public ArrayList<ItensEncomenda> getItens() {
        return itens;
    }

    public void setItens(ArrayList<ItensEncomenda> itens) {
        this.itens = itens;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Atendente getAtendente() {
        return atendente;
    }

    public void setAtendente(Atendente atendente) {
        this.atendente = atendente;
    }

    public void adicionar(ItensEncomenda itemAdd){
        for(ItensEncomenda item:itens){
            if(item.getProduto().getIdProduto() == itemAdd.getProduto().getIdProduto()){
                item.setQuantidade(item.getQuantidade()+itemAdd.getQuantidade());
                return;
            }
        }
        itens.add(itemAdd);
    }

    public double calcular(){
        double total = 0.0;
        for(ItensEncomenda item:itens){
            total += item.getValor()*item.getQuantidade();
        }
        this.valorTotal = total;
        return this.valorTotal;
    }

    @Override
    public String toString() {
        return "Encomenda{" +
                "idEncomenda=" + idEncomenda +
                ", dataVenda=" + dataVenda +
                ", dataEntrega=" + dataEntrega +
                ", hora=" + hora +
                ", descricao='" + descricao + '\'' +
                ", valorTotal=" + valorTotal +
                ", itens=" + itens +
                ", cliente=" + cliente +
                ", atendente=" + atendente +
                '}';
    }
}
