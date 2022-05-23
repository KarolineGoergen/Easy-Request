package br.easy.request.projetointegrador.telas;

import java.sql.SQLException;

import br.easy.request.projetointegrador.models.Produto;
import br.easy.request.projetointegrador.repositorios.RepositorioProdutos;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CadastroProduto {

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfValorUnitario;

    @FXML
    private Button btCadastrar;

    private RepositorioProdutos repositorioProdutos;

    private Produto produtoExistente = null;

    public CadastroProduto(RepositorioProdutos repositorioProdutos) {
        this.repositorioProdutos = repositorioProdutos;
    }

    public CadastroProduto(Produto produtoExistente, RepositorioProdutos repositorioProdutos) {
        this.repositorioProdutos = repositorioProdutos;
        this.produtoExistente = produtoExistente;
    }

    public void initialize(){
        if( produtoExistente != null){
            tfNome.setText(produtoExistente.getNome());
            tfValorUnitario.setText(""+produtoExistente.getValorUnitario());

            btCadastrar.setText("Atualizar");

        }
    }

    @FXML
    private void cadastrar() {
        String nome = tfNome.getText();
        String strValor = tfValorUnitario.getText();

        boolean temErro = false;
        String msg = "";
        double valorUnitarioD = 0;


        if (nome.isEmpty() || nome.isBlank()) {
            temErro = true;
            msg += "O campo Nome está vazio!\n";
        }

        try{
            valorUnitarioD = Double.parseDouble(strValor);
        }catch(NumberFormatException e){
            temErro = true;
            msg += "Valor inválido!";
        }

        if (!temErro) {
            try {
                boolean ret;
                
                if(produtoExistente != null){
                     ret = repositorioProdutos.editarProduto(produtoExistente.getIdProduto(), nome,valorUnitarioD);
                }else{
                    ret = repositorioProdutos.cadastrarProduto(nome, valorUnitarioD);
                }
                
                if (ret) {
                    msg = "Produto cadastrado com sucesso!";
                    limpar();
                } else {
                    msg = "Erro ao cadastrar produto!";
                }

            } catch (SQLException e) {
                temErro = true;
                msg = e.getMessage();
            }
        }

        Alert alert = new Alert(temErro ? AlertType.ERROR : AlertType.INFORMATION, msg);
        alert.showAndWait();

    }

    @FXML
    private void limpar() {
        tfNome.clear();
        tfValorUnitario.clear();
    }

    
}
