package br.easy.request.projetointegrador.repositorios;

import java.sql.SQLException;
import java.util.ArrayList;

import br.easy.request.projetointegrador.daos.interfaces.AtendenteDAO;
import br.easy.request.projetointegrador.models.Atendente;
import br.easy.request.projetointegrador.models.Cliente;

public class RepositorioAtendentes {

    private ArrayList<Atendente> atendentes;

    private AtendenteDAO atendenteDAO;

    public RepositorioAtendentes(AtendenteDAO atendenteDAO) {

        this.atendenteDAO = atendenteDAO;
        atendentes = new ArrayList<>();
    }

    public boolean cadastrarAtendente(String nome, String email, String cpf, String telefone) throws SQLException {

        Atendente a = new Atendente(nome, email, cpf, telefone);

        try {
            atendenteDAO.cadastrarAtendente(a);
            this.atendentes.add(a);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        
    }

    public boolean editarAtendente(int id, String nome, String email, String cpf, String telefone) throws SQLException {

        Atendente atendente = new Atendente(nome, email, cpf, telefone);

        try {
            return atendenteDAO.editarAtendente(id, atendente);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean removerAtendente(int id) throws SQLException {

        try {
            return atendenteDAO.removerAtendente(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Atendente> listarAtendente() throws Exception {

        return atendenteDAO.listarAtendente();
    }
    
}
