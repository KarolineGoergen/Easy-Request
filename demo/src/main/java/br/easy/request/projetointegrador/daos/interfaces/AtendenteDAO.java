package br.easy.request.projetointegrador.daos.interfaces;

import java.util.ArrayList;

import br.easy.request.projetointegrador.models.Atendente;
import br.easy.request.projetointegrador.models.Cliente;

public interface AtendenteDAO {

    boolean cadastrarAtendente(Atendente a) throws Exception;
    boolean editarAtendente(int idAtendente, Atendente a) throws Exception;
    boolean removerAtendente(int idAtendente) throws Exception;
    ArrayList<Atendente> listarAtendente() throws Exception;
    Atendente buscar(int id) throws Exception;
    Atendente buscarAtendenteEncomenda(int idEncomenda) throws Exception;
}
