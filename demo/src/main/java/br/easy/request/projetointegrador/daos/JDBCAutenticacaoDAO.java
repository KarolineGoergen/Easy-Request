package br.easy.request.projetointegrador.daos;

import br.easy.request.projetointegrador.daos.interfaces.AutenticacaoDAO;
import br.easy.request.projetointegrador.models.Usuario;
import br.easy.request.projetointegrador.utils.FabricaConexoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCAutenticacaoDAO implements AutenticacaoDAO {


    private FabricaConexoes fabricaConexoes;

    public JDBCAutenticacaoDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
    }

    @Override
    public boolean cadastrar(Usuario u) throws Exception {

        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO pi_usuario (login, senha) VALUES (?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, u.getLogin());
        pstmt.setString(2, u.getSenha());
        pstmt.execute();

        ResultSet rsId = pstmt.getGeneratedKeys();
        rsId.next();

        int id = rsId.getInt(1);

        rsId.close();
        pstmt.close();
        con.close();

        u.setId(id);

        return true;
    }

    @Override
    public Usuario login(String login, String senha) throws Exception {

        Usuario u = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * from pi_usuario WHERE login=? and senha=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, login);
        pstmt.setString(2, senha);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String loginUsuario = rs.getString("login");
            String senhaUsuario = rs.getString("senha");

            u = new Usuario(id, loginUsuario, senhaUsuario);
        }

        rs.close();
        pstmt.close();
        con.close();

        return u;

    }

}

