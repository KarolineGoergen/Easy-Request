package br.easy.request.projetointegrador.daos.interfaces;

import br.easy.request.projetointegrador.models.Usuario;

public interface AutenticacaoDAO {
    Usuario login(String login, String senha) throws Exception;
    boolean cadastrar(Usuario u) throws Exception;

}
