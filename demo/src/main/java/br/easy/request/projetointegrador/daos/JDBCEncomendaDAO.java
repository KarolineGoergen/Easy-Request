
package br.easy.request.projetointegrador.daos;

import br.easy.request.projetointegrador.daos.interfaces.EncomendaDAO;
import br.easy.request.projetointegrador.models.Encomenda;
import br.easy.request.projetointegrador.models.ItensEncomenda;
import br.easy.request.projetointegrador.utils.FabricaConexoes;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class JDBCEncomendaDAO implements EncomendaDAO {

    private FabricaConexoes fabricaConexoes;
    public JDBCEncomendaDAO(FabricaConexoes fabricaConexoes) { this.fabricaConexoes = fabricaConexoes; }

    private void salvarItens(Encomenda e) throws Exception{
        Connection con = fabricaConexoes.getConnection();
        String sql = "INSERT INTO pi_itensencomenda(idEncomenda, idProduto, quantidade, valor) VALUES (?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql);

        for(ItensEncomenda item:e.getItens()){
            pstmt.setInt(1,e.getIdEncomenda());
            pstmt.setInt(2,item.getProduto().getIdProduto());
            pstmt.setInt(3,item.getQuantidade());
            pstmt.setDouble(4, item.getValor());

            pstmt.execute();
        }

        pstmt.close();
        con.close();

    }

    @Override
    public boolean cadastrarEncomenda(Encomenda e) throws Exception {

        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO pi_encomenda(dataVenda,dataEntrega,hora,descricao,valorTotal,idCli,idAten) VALUES (?,?,?,?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setTimestamp(1, Timestamp.valueOf(e.getDataVenda()));
        pstmt.setString(2, e.getDataEntrega()+"");
        pstmt.setString(3,e.getHora());
        pstmt.setString(4,e.getDescricao());
        pstmt.setDouble(5,e.getValorTotal());
        pstmt.setInt(6,e.getCliente().getIdCliente());
        pstmt.setInt(7,e.getAtendente().getIdAtendente());

        pstmt.execute();

        ResultSet rsId = pstmt.getGeneratedKeys();
        rsId.next();
        int id = rsId.getInt(1);

        e.setIdEncomenda(id);

        rsId.close();
        pstmt.close();
        con.close();

        salvarItens(e);

        return true;
    }

    private ArrayList<ItensEncomenda> carregarItens(int idEncomenda) throws Exception{
        ArrayList<ItensEncomenda> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM pi_itensencomenda WHERE idEncomenda=?";

        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, idEncomenda);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            int id = rs.getInt("id");
            int quantidade = rs.getInt("quantidade");
            double valor = rs.getDouble("valor");

            ItensEncomenda item = new ItensEncomenda(id,null, quantidade, valor);

            lista.add(item);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public boolean removerEncomenda(int idEncomenda) throws Exception {

        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE pi_encomenda SET ativo=0 WHERE idEncomenda=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, idEncomenda);

        int ret = pstmt.executeUpdate();

        return ret==1;
    }

    @Override
    public ArrayList<Encomenda> listarEncomenda() throws Exception {
        ArrayList<Encomenda> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM pi_encomenda WHERE ativo=1";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            int id = rs.getInt("idEncomenda");
            LocalDateTime dataVenda = rs.getTimestamp("dataVenda").toLocalDateTime();
            LocalDate dataEntrega = rs.getDate("dataEntrega").toLocalDate();
            String hora = rs.getString("hora");
            String descricao = rs.getString("descricao").toString();
            double valorTotal = rs.getDouble("valorTotal");

            ArrayList<ItensEncomenda> itens = carregarItens(id);

            Encomenda encomenda = new Encomenda(id,dataVenda,dataEntrega,hora,descricao, valorTotal,itens,null,null);

            lista.add(encomenda);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }
}
