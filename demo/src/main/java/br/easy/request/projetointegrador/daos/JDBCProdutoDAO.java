package br.easy.request.projetointegrador.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import br.easy.request.projetointegrador.daos.interfaces.ProdutoDAO;
import br.easy.request.projetointegrador.models.Produto;
import br.easy.request.projetointegrador.utils.FabricaConexoes;

public class JDBCProdutoDAO implements ProdutoDAO {

    private FabricaConexoes fabricaConexoes;

    public JDBCProdutoDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;
    }


    @Override
    public boolean cadastrarProduto(Produto p) throws Exception {

        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO pi_produto(nome,valorUnitario) VALUES (?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, p.getNome());
        pstmt.setDouble(2, p.getValorUnitario());

        pstmt.execute();

        ResultSet rsId = pstmt.getGeneratedKeys();
        rsId.next();
        int idProduto = rsId.getInt(1);

        rsId.close();
        pstmt.close();
        con.close();

        p.setId(idProduto);

        return true;
    }

    @Override
    public boolean editarProduto(int idProduto, Produto p) throws Exception {

        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE pi_produto SET nome=?,valorUnitario=? WHERE idProduto=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, p.getNome());
        pstmt.setDouble(2, p.getValorUnitario());
        pstmt.setInt(3, idProduto);

        int ret = pstmt.executeUpdate();

        pstmt.close();
        con.close();

        return ret==1;
    }

    @Override
    public boolean removerProduto(int idProduto) throws Exception {

        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE pi_produto SET ativo=0 WHERE idProduto=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, idProduto);

        int ret = pstmt.executeUpdate();

        return ret==1;
    }

    private Produto montarProduto(ResultSet rs) throws Exception{

        int idProduto = rs.getInt("idProduto");
        String nome = rs.getString("nome");
        Double valorUnitario = rs.getDouble("valorUnitario");

        Produto p = new Produto(idProduto, nome, valorUnitario);

        return p;
    }

    @Override
    public ArrayList<Produto> listarProduto() throws Exception {
        ArrayList<Produto> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "call lista_produtos()";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            Produto p = montarProduto(rs);
            lista.add(p);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public Produto buscar(int id) throws Exception {

        Produto p = null;
        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM pi_produto WHERE idProduto=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            p = montarProduto(rs);

        }

        rs.close();
        pstmt.close();
        con.close();

        return p;

    }


    @Override
    public Produto buscarProdutoItem(int idItem) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sqlIdProduto = "SELECT idProduto FROM pi_itensencomenda WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sqlIdProduto);

        pstmt.setInt(1, idItem);

        ResultSet rsIdProduto = pstmt.executeQuery();

        rsIdProduto.next();
        int idProduto = rsIdProduto.getInt(1);

        rsIdProduto.close();
        pstmt.close();
        con.close();

        return buscar(idProduto);

    }
    
}
