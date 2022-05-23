package br.easy.request.projetointegrador.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Statement;

import br.easy.request.projetointegrador.daos.interfaces.ClienteDAO;
import br.easy.request.projetointegrador.models.Cliente;
import br.easy.request.projetointegrador.models.Encomenda;
import br.easy.request.projetointegrador.utils.FabricaConexoes;

public class JDBCClienteDAO implements ClienteDAO {

    private FabricaConexoes fabricaConexoes;

    public JDBCClienteDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;
    }


    @Override
    public boolean cadastrarCliente(Cliente c) throws Exception {

        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO pi_cliente(nome,email,cpf,telefone) VALUES (?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, c.getNome());
        pstmt.setString(2, c.getEmail());
        pstmt.setString(3, c.getCpf());
        pstmt.setString(4, c.getTelefone());

        pstmt.execute();

        ResultSet rsId = pstmt.getGeneratedKeys();
        rsId.next();
        int idCliente = rsId.getInt(1);

        rsId.close();
        pstmt.close();
        con.close();

        c.setId(idCliente);

        return true;
    }

    @Override
    public boolean editarCliente(int idCliente, Cliente c) throws Exception {

        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE pi_cliente SET nome=?,email=?,cpf=?,telefone=? WHERE idCliente=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, c.getNome());
        pstmt.setString(2, c.getEmail());
        pstmt.setString(3, c.getCpf());
        pstmt.setString(4, c.getTelefone());
        pstmt.setInt(5, idCliente);

        int ret = pstmt.executeUpdate();

        pstmt.close();
        con.close();

        return ret==1;
    }

    @Override
    public boolean removerCliente(int idCliente) throws Exception {

        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE pi_cliente SET ativo=0 WHERE idCliente=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, idCliente);

        int ret = pstmt.executeUpdate();

        return ret==1;
    }

    private Cliente montarCliente(ResultSet rs) throws Exception{

        int idCliente = rs.getInt("idCliente");
        String nome = rs.getString("nome");
        String email = rs.getString("email");
        String cpf = rs.getString("cpf");
        String telefone = rs.getString("telefone");

        Cliente c = new Cliente(idCliente, nome, email, cpf, telefone);

        return c;
    }

    @Override
    public ArrayList<Cliente> listarCliente() throws Exception {
        ArrayList<Cliente> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "call clientes_ativos()";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            Cliente c = montarCliente(rs);
            lista.add(c);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public Cliente buscar(int id) throws Exception {
        Cliente c = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM pi_cliente WHERE idCliente=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1,id);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            c = montarCliente(rs);
        }

        rs.close();
        pstmt.close();
        con.close();

        return c;
    }


    @Override
    public Cliente buscarClienteEncomenda(int idEncomenda) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sqlIdCliente = "SELECT idCli FROM pi_encomenda WHERE idEncomenda=?";

        PreparedStatement pstmt = con.prepareStatement(sqlIdCliente);

        pstmt.setInt(1, idEncomenda);

        ResultSet rsIdCliente = pstmt.executeQuery();

        rsIdCliente.next();
        int idCliente = rsIdCliente.getInt(1);

        rsIdCliente.close();
        pstmt.close();
        con.close();

        return buscar(idCliente);

    }

}

