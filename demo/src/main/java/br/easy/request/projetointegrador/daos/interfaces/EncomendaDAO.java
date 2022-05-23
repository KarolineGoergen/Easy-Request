package br.easy.request.projetointegrador.daos.interfaces;

import br.easy.request.projetointegrador.models.Encomenda;

import java.util.ArrayList;

public interface EncomendaDAO {
    boolean cadastrarEncomenda(Encomenda e) throws Exception;
    ArrayList<Encomenda> listarEncomenda() throws Exception;
    boolean removerEncomenda(int idEncomenda) throws Exception;
}
