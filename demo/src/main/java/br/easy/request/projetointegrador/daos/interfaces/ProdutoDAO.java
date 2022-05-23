package br.easy.request.projetointegrador.daos.interfaces;

import java.util.ArrayList;

import br.easy.request.projetointegrador.models.Produto;

public interface ProdutoDAO {
    boolean cadastrarProduto(Produto p) throws Exception;
    boolean editarProduto(int idProduto, Produto p) throws Exception;
    boolean removerProduto(int idProduto) throws Exception;
    ArrayList<Produto> listarProduto() throws Exception;
    Produto buscar(int id) throws Exception;
    Produto buscarProdutoItem(int idItem) throws Exception;
    
}
