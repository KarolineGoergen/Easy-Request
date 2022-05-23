package br.easy.request.projetointegrador.repositorios;

import br.easy.request.projetointegrador.daos.interfaces.AtendenteDAO;
import br.easy.request.projetointegrador.daos.interfaces.ClienteDAO;
import br.easy.request.projetointegrador.daos.interfaces.EncomendaDAO;
import br.easy.request.projetointegrador.daos.interfaces.ProdutoDAO;
import br.easy.request.projetointegrador.models.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RepositorioEncomendas {

    private AtendenteDAO atendenteDAO;
    private ClienteDAO clienteDAO;
    private EncomendaDAO encomendaDAO;
    private ProdutoDAO produtoDAO;
    private Encomenda encomenda;


    public RepositorioEncomendas(AtendenteDAO atendenteDAO, ClienteDAO clienteDAO, EncomendaDAO encomendaDAO, ProdutoDAO produtoDAO) {
        this.atendenteDAO = atendenteDAO;
        this.clienteDAO = clienteDAO;
        this.encomendaDAO = encomendaDAO;
        this.produtoDAO = produtoDAO;
    }

    public void iniciaEncomenda(Cliente cliente, Atendente atendente){
        LocalDateTime dataVenda = LocalDateTime.now();
        encomenda = new Encomenda(dataVenda, cliente, atendente);
    }

    public void adicionaProduto(Produto produto, int quantidade){

        ItensEncomenda itensEncomenda = new ItensEncomenda(produto, quantidade, produto.getValorUnitario());
        encomenda.adicionar(itensEncomenda);
    }

    public void adicionaisProduto(LocalDate dataEntrega,String hora,String descricao) {
        encomenda.setDataEntrega(dataEntrega);
        encomenda.setDescricao(descricao);
        encomenda.setHora(hora);
    }

    public boolean finalizarEncomenda() throws Exception{

        encomenda.calcular();
        encomendaDAO.cadastrarEncomenda(encomenda);

        return true;
    }

    public boolean removerEncomenda(int id) throws SQLException {

        try {
            return encomendaDAO.removerEncomenda(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public Encomenda getEncomenda(){
        return encomenda;
    }

    public ArrayList<Encomenda> listar() throws Exception{
        ArrayList<Encomenda> lista = encomendaDAO.listarEncomenda();

        for(Encomenda encomenda:lista){
            encomenda.setCliente(clienteDAO.buscarClienteEncomenda(encomenda.getIdEncomenda()));
            encomenda.setAtendente(atendenteDAO.buscarAtendenteEncomenda(encomenda.getIdEncomenda()));

            for(ItensEncomenda item:encomenda.getItens()){
                item.setProduto(produtoDAO.buscarProdutoItem(item.getId()));
            }
        }

        return lista;
    }
}
