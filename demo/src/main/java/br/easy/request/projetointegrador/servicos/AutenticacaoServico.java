package br.easy.request.projetointegrador.servicos;

import br.easy.request.projetointegrador.daos.interfaces.AutenticacaoDAO;
import br.easy.request.projetointegrador.models.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;

public class AutenticacaoServico {

    private Usuario logado;
    private AutenticacaoDAO autenticacaoDAO;

    public AutenticacaoServico(AutenticacaoDAO autenticacaoDAO){
        this.autenticacaoDAO = autenticacaoDAO;
    }

    public Usuario logar(String login, String senha) throws Exception{
        this.logado = autenticacaoDAO.login(login, senha);
        return this.logado;
    }

    public boolean cadastrar(String login, String senha) throws Exception{
        Usuario u = new Usuario(login, senha);

        try {
            autenticacaoDAO.cadastrar(u);
            return true;

        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public Usuario getLogado(){
        return this.logado;
    }

    public void logout(){
        this.logado = null;
    }

    public boolean estaLogado(){
        return this.logado != null;
    }


}
