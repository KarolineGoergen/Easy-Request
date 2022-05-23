package br.easy.request.projetointegrador.repositorios;

import java.sql.SQLException;
import java.util.ArrayList;

import br.easy.request.projetointegrador.daos.interfaces.ProdutoDAO;
import br.easy.request.projetointegrador.models.Produto;

public class RepositorioProdutos {

    private ArrayList<Produto> produtos;

    private ProdutoDAO produtoDAO;

    public RepositorioProdutos(ProdutoDAO produtoDAO) {

        this.produtoDAO = produtoDAO;
        produtos = new ArrayList<>();
    }

    public boolean cadastrarProduto(String nome, Double valorUnitario) throws SQLException {

        Produto p = new Produto(nome, valorUnitario);

        try {
            produtoDAO.cadastrarProduto(p);
            this.produtos.add(p);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        
    }

    public boolean editarProduto(int id, String nome, Double valorUnitario) throws SQLException {

        Produto produto = new Produto(nome, valorUnitario);

        try {
            return produtoDAO.editarProduto(id, produto);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean removerProduto(int id) throws SQLException {

        try {
            return produtoDAO.removerProduto(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Produto> listarProduto() throws Exception {

        return produtoDAO.listarProduto();
    }
    
    
}
