package br.easy.request.projetointegrador.repositorios;

import java.sql.SQLException;
import java.util.ArrayList;
import br.easy.request.projetointegrador.daos.interfaces.ClienteDAO;
import br.easy.request.projetointegrador.models.Cliente;

public class RepositorioClientes {

    private ArrayList<Cliente> clientes;

    private ClienteDAO clienteDAO;

    public RepositorioClientes(ClienteDAO clienteDAO) {

        this.clienteDAO = clienteDAO;
        clientes = new ArrayList<>();
    }

    public boolean cadastrarCliente(String nome, String email, String cpf, String telefone) throws SQLException {

        Cliente c = new Cliente(nome, email, cpf, telefone);

        try {
            clienteDAO.cadastrarCliente(c);
            this.clientes.add(c);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        
    }

    public boolean editarCliente(int id, String nome, String email, String cpf, String telefone) throws SQLException {

        Cliente cliente = new Cliente(nome, email, cpf, telefone);

        try {
            return clienteDAO.editarCliente(id, cliente);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean removerCliente(int id) throws SQLException {

        try {
            return clienteDAO.removerCliente(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Cliente> listarCliente() throws Exception {

        return clienteDAO.listarCliente();
    }
    
}
