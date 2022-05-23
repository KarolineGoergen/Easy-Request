package br.easy.request.projetointegrador;

import java.io.IOException;

import br.easy.request.projetointegrador.daos.*;
import br.easy.request.projetointegrador.daos.interfaces.*;
import br.easy.request.projetointegrador.models.Produto;
import br.easy.request.projetointegrador.repositorios.RepositorioAtendentes;
import br.easy.request.projetointegrador.repositorios.RepositorioClientes;
import br.easy.request.projetointegrador.repositorios.RepositorioEncomendas;
import br.easy.request.projetointegrador.repositorios.RepositorioProdutos;
import br.easy.request.projetointegrador.servicos.AutenticacaoServico;
import br.easy.request.projetointegrador.telas.Home;
import br.easy.request.projetointegrador.utils.FabricaConexoes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;


/**
 * JavaFX App
 */
public class App extends Application {

    FabricaConexoes fabricaConexoes = FabricaConexoes.getInstance();
    ClienteDAO clienteDAO = new JDBCClienteDAO(fabricaConexoes);
    AtendenteDAO atendenteDAO = new JDBCAtendenteDAO(fabricaConexoes);
    ProdutoDAO produtoDAO = new JDBCProdutoDAO(fabricaConexoes);
    EncomendaDAO encomendaDAO = new JDBCEncomendaDAO(fabricaConexoes);
    AutenticacaoDAO autenticacaoDAO = new JDBCAutenticacaoDAO(fabricaConexoes);

    RepositorioClientes repositorioClientes = new RepositorioClientes(clienteDAO);
    RepositorioAtendentes repositorioAtendentes = new RepositorioAtendentes(atendenteDAO);
    RepositorioProdutos repositorioProdutos = new RepositorioProdutos(produtoDAO);
    RepositorioEncomendas repositorioEncomendas = new RepositorioEncomendas(atendenteDAO,clienteDAO,encomendaDAO,produtoDAO);
    AutenticacaoServico autenticacaoServico = new AutenticacaoServico(autenticacaoDAO);

    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(loadTela("fxml/home.fxml", o->new Home(repositorioClientes, repositorioAtendentes, repositorioProdutos,repositorioEncomendas,autenticacaoServico)), 640, 480);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Easy Request");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("img/icon.png")));
        stage.show();
    }


    public static Parent loadTela(String fxml, Callback controller){
        Parent root = null;
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(fxml));
            loader.setControllerFactory(controller);

            root = loader.load();

        }catch (Exception e){
            System.out.println("Problema no arquivo fxml. Está correto? "+fxml);
            e.printStackTrace();
            System.exit(0);
        }
        return root;
    }

    public static void main(String[] args) {
        launch();
    }

}