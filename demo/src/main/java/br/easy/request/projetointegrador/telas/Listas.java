package br.easy.request.projetointegrador.telas;

import br.easy.request.projetointegrador.App;
import br.easy.request.projetointegrador.models.*;
import br.easy.request.projetointegrador.repositorios.RepositorioAtendentes;
import br.easy.request.projetointegrador.repositorios.RepositorioClientes;
import br.easy.request.projetointegrador.repositorios.RepositorioEncomendas;
import br.easy.request.projetointegrador.repositorios.RepositorioProdutos;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

import java.time.format.DateTimeFormatter;

public class Listas {

    @FXML
    private ListView<Atendente> lstAtendentes;

    @FXML
    private ListView<Cliente> lstClientes;

    @FXML
    private ListView<Produto> lstProdutos;

    @FXML
    private TableColumn<Encomenda, String> tbcDataVenda;

    @FXML
    private TableColumn<Encomenda, String> tbcCliente;

    @FXML
    private TableColumn<Encomenda, String> tbcAtendente;

    @FXML
    private TableColumn<ItensEncomenda, String> tbcProdutos;

    @FXML
    private TableColumn<ItensEncomenda, Integer> tbcQuantidade;

    @FXML
    private TableColumn<ItensEncomenda, Double> tbcValorUnitario;

    @FXML
    private TableView<Encomenda> tbEncomenda;

    @FXML
    private TableView<ItensEncomenda> tbItens;

    @FXML
    private TextArea taInfo;

    @FXML
    private ProgressIndicator piCarregando;

    @FXML
    private FlowPane rootPane;


    private RepositorioAtendentes repositorioAtendentes;
    private RepositorioClientes repositorioClientes;
    private RepositorioProdutos repositorioProdutos;
    private RepositorioEncomendas repositorioEncomendas;

    public Listas(RepositorioAtendentes repositorioAtendentes, RepositorioClientes repositorioClientes, RepositorioProdutos repositorioProdutos, RepositorioEncomendas repositorioEncomendas) {
        this.repositorioAtendentes = repositorioAtendentes;
        this.repositorioClientes = repositorioClientes;
        this.repositorioProdutos = repositorioProdutos;
        this.repositorioEncomendas = repositorioEncomendas;
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void initialize() {

        taInfo.setEditable(false);

        tbcProdutos.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduto().getNome()));
        tbcValorUnitario.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValor()).asObject());
        tbcQuantidade.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantidade()).asObject());
        tbcAtendente.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAtendente().getNome()));

        tbcDataVenda.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDataVenda().format(formatter)));
        tbcCliente.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCliente().getNome()));

        Thread lista = new Thread(()-> {
        try {
            lstClientes.getItems().addAll(repositorioClientes.listarCliente());
            lstAtendentes.getItems().addAll(repositorioAtendentes.listarAtendente());
            lstProdutos.getItems().addAll(repositorioProdutos.listarProduto());
            tbEncomenda.getItems().addAll(repositorioEncomendas.listar());

            Platform.runLater(()->{
                piCarregando.setVisible(false);

            });

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        });
        lista.setDaemon(true);
        lista.start();
    }

    @FXML
    private void atualizarCliente(MouseEvent event) {
        Cliente clienteSelecionado = lstClientes.getSelectionModel().getSelectedItem();

         if (event.getClickCount() == 2) {
            if (clienteSelecionado != null) {

                StackPane painelCentral = (StackPane) rootPane.getParent();
                painelCentral.getChildren().clear();
                painelCentral.getChildren().add(App.loadTela("fxml/cadastro_cliente.fxml", (o) -> new CadastroCliente(clienteSelecionado, repositorioClientes)));
            }
        }

    }

    @FXML
    private void atualizarAtendente(MouseEvent event) {
        Atendente atendenteSelecionado = lstAtendentes.getSelectionModel().getSelectedItem();

        if (event.getClickCount() == 2) {
            if (atendenteSelecionado != null) {

                StackPane painelCentral = (StackPane) rootPane.getParent();
                painelCentral.getChildren().clear();
                painelCentral.getChildren().add(App.loadTela("fxml/cadastro_atendente.fxml", (o) -> new CadastroAtendente(atendenteSelecionado, repositorioAtendentes)));
            }
        }
    }

    @FXML
    private void atualizarProduto(MouseEvent event) {
        Produto produtoSelecionado = lstProdutos.getSelectionModel().getSelectedItem();

        if (event.getClickCount() == 2) {
            if (produtoSelecionado != null) {

                StackPane painelCentral = (StackPane) rootPane.getParent();
                painelCentral.getChildren().clear();
                painelCentral.getChildren().add(App.loadTela("fxml/cadastro_produto.fxml", (o) -> new CadastroProduto(produtoSelecionado, repositorioProdutos)));
            }
        }
    }

    @FXML
    private void Remover(ActionEvent event){
        Cliente clienteSelecionado = lstClientes.getSelectionModel().getSelectedItem();
        Atendente atendenteSelecionado = lstAtendentes.getSelectionModel().getSelectedItem();
        Produto produtoSelecionado = lstProdutos.getSelectionModel().getSelectedItem();

        if(clienteSelecionado != null){
            try {

                repositorioClientes.removerCliente(clienteSelecionado.getIdCliente());
                lstClientes.getItems().clear();
                lstClientes.getItems().addAll(repositorioClientes.listarCliente());
            } catch (Exception e) {

                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }

        }
        if(atendenteSelecionado != null){
            try {

                repositorioAtendentes.removerAtendente(atendenteSelecionado.getIdAtendente());
                lstAtendentes.getItems().clear();
                lstAtendentes.getItems().addAll(repositorioAtendentes.listarAtendente());
            } catch (Exception e) {

                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        }
        if (produtoSelecionado != null) {
            try {

                repositorioProdutos.removerProduto(produtoSelecionado.getIdProduto());
                lstProdutos.getItems().clear();
                lstProdutos.getItems().addAll(repositorioProdutos.listarProduto());
            } catch (Exception e) {

                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        }

    }

    @FXML
    private void MostraEncomendas(MouseEvent event) {
        Encomenda encomendaSelecionada = tbEncomenda.getSelectionModel().getSelectedItem();

        if (event.getClickCount() == 1) {
            if (encomendaSelecionada != null) {
                try {
                    tbItens.getItems().clear();
                    tbItens.getItems().addAll(encomendaSelecionada.getItens());
                    taInfo.setText("\nDescrição: " + encomendaSelecionada.getDescricao() + "\nData da Entrega: " + encomendaSelecionada.getDataEntrega().format(formatter) + "\nHora da Entrega: " + encomendaSelecionada.getHora() + "\nTotal: " + encomendaSelecionada.getValorTotal() + "");
                } catch (Exception e) {
                    Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                    alert.showAndWait();
                }

            }

        }


    }

    @FXML
    private void EncomendaEntregue(ActionEvent event) {
        Encomenda encomendaSelecionada = tbEncomenda.getSelectionModel().getSelectedItem();
        String msg = "Encomenda entregue!";

            if (encomendaSelecionada != null) {
                try {

                    repositorioEncomendas.removerEncomenda(encomendaSelecionada.getIdEncomenda());
                    tbEncomenda.getItems().clear();
                    tbItens.getItems().clear();
                    taInfo.clear();
                    tbEncomenda.getItems().addAll(repositorioEncomendas.listar());

                    Alert alert = new Alert(AlertType.INFORMATION,msg);
                    alert.showAndWait();

                } catch (Exception e) {
                    Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                    alert.showAndWait();
                }
            }

    }
}
