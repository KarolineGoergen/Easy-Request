package br.easy.request.projetointegrador.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import br.easy.request.projetointegrador.daos.interfaces.AtendenteDAO;
import br.easy.request.projetointegrador.models.Atendente;
import br.easy.request.projetointegrador.models.Cliente;
import br.easy.request.projetointegrador.utils.FabricaConexoes;

public class JDBCAtendenteDAO implements AtendenteDAO{

    private FabricaConexoes fabricaConexoes;

    public JDBCAtendenteDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;
    }


    @Override
    public boolean cadastrarAtendente(Atendente a) throws Exception {

        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO pi_atendente(nome,email,cpf,telefone) VALUES (?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, a.getNome());
        pstmt.setString(2, a.getEmail());
        pstmt.setString(3, a.getCpf());
        pstmt.setString(4, a.getTelefone());

        pstmt.execute();

        ResultSet rsId = pstmt.getGeneratedKeys();
        rsId.next();
        int idAtendente = rsId.getInt(1);

        rsId.close();
        pstmt.close();
        con.close();

        a.setId(idAtendente);

        return true;
    }

    @Override
    public boolean editarAtendente(int idAtendente, Atendente a) throws Exception {

        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE pi_atendente SET nome=?,email=?,cpf=?,telefone=? WHERE idAtendente=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, a.getNome());
        pstmt.setString(2, a.getEmail());
        pstmt.setString(3, a.getCpf());
        pstmt.setString(4, a.getTelefone());
        pstmt.setInt(5, idAtendente);

        int ret = pstmt.executeUpdate();

        pstmt.close();
        con.close();

        return ret==1;
    }

    @Override
    public boolean removerAtendente(int idAtendente) throws Exception {

        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE pi_atendente SET ativo=0 WHERE idAtendente=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, idAtendente);

        int ret = pstmt.executeUpdate();

        return ret==1;
    }

    private Atendente montarAtendente(ResultSet rs) throws Exception{

        int idAtendente = rs.getInt("idAtendente");
        String nome = rs.getString("nome");
        String email = rs.getString("email");
        String cpf = rs.getString("cpf");
        String telefone = rs.getString("telefone");

        Atendente a = new Atendente(idAtendente, nome, email, cpf, telefone);

        return a;
    }

    @Override
    public ArrayList<Atendente> listarAtendente() throws Exception {
        ArrayList<Atendente> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "call atendentes_ativos()";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            Atendente a = montarAtendente(rs);
            lista.add(a);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public Atendente buscar(int id) throws Exception {
        Atendente a = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM pi_atendente WHERE idAtendente=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1,id);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            a = montarAtendente(rs);
        }

        rs.close();
        pstmt.close();
        con.close();

        return a;
    }


    @Override
    public Atendente buscarAtendenteEncomenda(int idEncomenda) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sqlIdAtendente = "SELECT idAten FROM pi_encomenda WHERE idEncomenda=?";

        PreparedStatement pstmt = con.prepareStatement(sqlIdAtendente);

        pstmt.setInt(1, idEncomenda);

        ResultSet rsIdAtendente = pstmt.executeQuery();

        rsIdAtendente.next();
        int idAtendente = rsIdAtendente.getInt(1);

        rsIdAtendente.close();
        pstmt.close();
        con.close();

        return buscar(idAtendente);

    }

    
}
