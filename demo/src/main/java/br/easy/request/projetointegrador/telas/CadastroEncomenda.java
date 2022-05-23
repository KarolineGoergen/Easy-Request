package br.easy.request.projetointegrador.telas;

import br.easy.request.projetointegrador.models.Atendente;
import br.easy.request.projetointegrador.models.Cliente;
import br.easy.request.projetointegrador.models.ItensEncomenda;
import br.easy.request.projetointegrador.models.Produto;
import br.easy.request.projetointegrador.repositorios.RepositorioAtendentes;
import br.easy.request.projetointegrador.repositorios.RepositorioClientes;
import br.easy.request.projetointegrador.repositorios.RepositorioEncomendas;
import br.easy.request.projetointegrador.repositorios.RepositorioProdutos;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class CadastroEncomenda {


    @FXML
    private ComboBox<Cliente> cbClientes;

    @FXML
    private ComboBox<Atendente> cbAtendentes;

    @FXML
    private TextField tfDataVenda;

    @FXML
    private ComboBox<Produto> cbProdutos;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private DatePicker dpDataEntrega;

    @FXML
    private TextArea taDescricao;

    @FXML
    private TableView<ItensEncomenda> tbItensEncomenda;

    @FXML
    private TableColumn<ItensEncomenda,String> tbcProduto;

    @FXML
    private TableColumn<ItensEncomenda,Double> tbcValorUnitario;

    @FXML
    private TableColumn<ItensEncomenda,Integer> tbcQuantidade;

    @FXML
    private TableColumn<ItensEncomenda,Double> tbcSubTotal;

    @FXML
    private Button btAdicionar;

    @FXML
    private Button btRegistrar;

    @FXML
    private ChoiceBox<String> cbHora;


    private RepositorioAtendentes repositorioAtendentes;
    private RepositorioClientes repositorioClientes;
    private RepositorioEncomendas repositorioEncomendas;
    private RepositorioProdutos repositorioProdutos;

    public CadastroEncomenda(RepositorioAtendentes repositorioAtendentes, RepositorioClientes repositorioClientes, RepositorioEncomendas repositorioEncomendas, RepositorioProdutos repositorioProdutos) {
        this.repositorioAtendentes = repositorioAtendentes;
        this.repositorioClientes = repositorioClientes;
        this.repositorioEncomendas = repositorioEncomendas;
        this.repositorioProdutos = repositorioProdutos;
    }

    private String[] hora = {"8:00","8:30","9:00","9:30","10:00","10:30","11:00","11:30",
    "13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00"};

    @FXML
    public void initialize(){

        cbHora.getItems().addAll(hora);
        btAdicionar.setDisable(true);
        cbProdutos.setDisable(true);
        tfQuantidade.setDisable(true);
        btRegistrar.setDisable(true);
        tbItensEncomenda.setDisable(true);

        tbcProduto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduto().getNome()));
        tbcValorUnitario.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValor()).asObject());
        tbcQuantidade.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantidade()).asObject());
        tbcSubTotal.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSubTotal()).asObject());

        try{
            cbClientes.getItems().addAll(repositorioClientes.listarCliente());
            cbAtendentes.getItems().addAll(repositorioAtendentes.listarAtendente());
            cbProdutos.getItems().addAll(repositorioProdutos.listarProduto());
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void iniciarEncomenda(){

        Cliente cliente = cbClientes.getSelectionModel().getSelectedItem();
        Atendente atendente = cbAtendentes.getSelectionModel().getSelectedItem();

        if(cliente != null && atendente != null){
            repositorioEncomendas.iniciaEncomenda(cliente,atendente);
            tfDataVenda.setText(repositorioEncomendas.getEncomenda().getDataVenda().toString());

            cbClientes.setDisable(true);
            cbAtendentes.setDisable(true);
            btAdicionar.setDisable(false);
            cbProdutos.setDisable(false);
            tfQuantidade.setDisable(false);
            btRegistrar.setDisable(false);
            tbItensEncomenda.setDisable(false);
            dpDataEntrega.setDisable(false);
            tfDataVenda.setDisable(true);
            taDescricao.setDisable(false);

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Selecione um Cliente e Atendente");
            alert.showAndWait();
        }
    }

    @FXML
    private void adicionarItem(){
        String strQuantidade = tfQuantidade.getText();
        Produto produto = cbProdutos.getSelectionModel().getSelectedItem();

        int quantidade = 0;
        boolean temErro = false;
        String msg = "";

        try{
            quantidade = Integer.parseInt(strQuantidade);
        }catch(NumberFormatException e){
            temErro = true;
            msg = "Quantidade inválida!\n";
        }

        if(quantidade <= 0 ){
            temErro = true;
            msg = "Quantidade inválida!\n";
        }

        if(produto == null){
            temErro = true;
            msg += "Selecione um produto!\n";
        }

        if(!temErro){
            repositorioEncomendas.adicionaProduto(produto, quantidade);

            atualizaTabela();

            cbProdutos.getSelectionModel().clearSelection();
            tfQuantidade.clear();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,msg);
            alert.showAndWait();
        }
    }

    private void atualizaTabela(){
        tbItensEncomenda.getItems().clear();
        tbItensEncomenda.getItems().addAll(repositorioEncomendas.getEncomenda().getItens());
    }


    @FXML
    private void finalizarEncomenda(){

        String descricao = taDescricao.getText();
        String hora = cbHora.getSelectionModel().getSelectedItem();
        LocalDate dataVenda = dpDataEntrega.getValue();

        boolean temErro = false;
        String msg = "Adicione pelo menos um produto\n";

        if(descricao == null){
            temErro = true;
            msg += "Adicione uma descrição!";
        }

        if(dataVenda == null){
            temErro = true;
            msg += "Selecione o dia da entrega!\n";
        }

        if(hora == null){
            temErro = true;
            msg += "Seleciona uma hora para a entrega\n";
        }

        if(repositorioEncomendas.getEncomenda().getItens().size() > 0){
            try{
                repositorioEncomendas.adicionaisProduto(dataVenda,hora,descricao);
                boolean ret = repositorioEncomendas.finalizarEncomenda();

                if(ret) {
                    msg = "Encomenda registrada com sucesso!";

                }else {
                    msg = "Erro ao registrar encomenda";
                }

                reiniciar();
            }catch(Exception e){
                e.printStackTrace();
                msg = e.getMessage();
            }

        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION,msg);
        alert.showAndWait();
    }

    private void reiniciar(){

        cbClientes.getSelectionModel().clearSelection();
        cbClientes.setDisable(false);

        cbAtendentes.getSelectionModel().clearSelection();
        cbAtendentes.setDisable(false);

        tbItensEncomenda.getItems().clear();
        btRegistrar.setDisable(true);
        cbProdutos.setDisable(true);
        tfQuantidade.setDisable(true);
        btRegistrar.setDisable(true);

        tbItensEncomenda.setDisable(true);

        tfDataVenda.clear();
        tfDataVenda.setDisable(true);

        dpDataEntrega.getEditor().clear();
        dpDataEntrega.setDisable(true);
        
        cbHora.getSelectionModel().clearSelection();
        taDescricao.setDisable(true);

        taDescricao.clear();
    }

    @FXML
    public void limper(){
       reiniciar();

    }

}

