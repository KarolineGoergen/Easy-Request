package br.easy.request.projetointegrador.daos.interfaces;

import java.util.ArrayList;

import br.easy.request.projetointegrador.models.Cliente;

public interface ClienteDAO {

    boolean cadastrarCliente(Cliente c) throws Exception;
    boolean editarCliente(int idCliente, Cliente c) throws Exception;
    boolean removerCliente(int idCliente) throws Exception;
    ArrayList<Cliente> listarCliente() throws Exception;
    Cliente buscar(int id) throws Exception;
    Cliente buscarClienteEncomenda(int idEncomenda) throws Exception;
    
}
