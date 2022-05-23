package br.easy.request.projetointegrador.telas;

import br.easy.request.projetointegrador.App;
import br.easy.request.projetointegrador.daos.interfaces.AutenticacaoDAO;
import br.easy.request.projetointegrador.repositorios.RepositorioAtendentes;
import br.easy.request.projetointegrador.repositorios.RepositorioClientes;
import br.easy.request.projetointegrador.repositorios.RepositorioEncomendas;
import br.easy.request.projetointegrador.repositorios.RepositorioProdutos;
import br.easy.request.projetointegrador.servicos.AutenticacaoServico;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class Home {

    private RepositorioClientes repositorioClientes;
    private RepositorioAtendentes repositorioAtendentes;
    private RepositorioProdutos repositorioProdutos;
    private RepositorioEncomendas repositorioEncomendas;
    private AutenticacaoServico autenticacaoServico;

    @FXML
    private StackPane painelCentral;

    @FXML
    private BorderPane root;


    public Home(RepositorioClientes repositorioClientes, RepositorioAtendentes repositorioAtendentes, RepositorioProdutos repositorioProdutos, RepositorioEncomendas repositorioEncomendas, AutenticacaoServico autenticacaoServico){
        this.repositorioClientes = repositorioClientes;
        this.repositorioAtendentes = repositorioAtendentes;
        this.repositorioProdutos = repositorioProdutos;
        this.repositorioEncomendas = repositorioEncomendas;
        this.autenticacaoServico = autenticacaoServico;
    }

    public void initialize(){
        atualizaTela();
    }

    @FXML
    private void loadCadastroCliente(){
        painelCentral.getChildren().clear();
        painelCentral.getChildren().add(App.loadTela("fxml/cadastro_cliente.fxml", (o)->new CadastroCliente(repositorioClientes)));
    }

    @FXML
    private void loadCadastroAtendente(){
        painelCentral.getChildren().clear();
        painelCentral.getChildren().add(App.loadTela("fxml/cadastro_atendente.fxml", (o)->new CadastroAtendente(repositorioAtendentes)));
    }

    @FXML
    private void loadCadastroProduto(){
        painelCentral.getChildren().clear();
        painelCentral.getChildren().add(App.loadTela("fxml/cadastro_produto.fxml", (o)->new CadastroProduto(repositorioProdutos)));
    }

    @FXML
    private void loadCadastroEncomenda(){
        painelCentral.getChildren().clear();
        painelCentral.getChildren().add(App.loadTela("fxml/cadastro_encomenda.fxml", (o)->new CadastroEncomenda(repositorioAtendentes,repositorioClientes,repositorioEncomendas,repositorioProdutos)));
    }

    @FXML
    private void loadListas(){
        painelCentral.getChildren().clear();
        painelCentral.getChildren().add(App.loadTela("fxml/listas.fxml", (o)->new Listas(repositorioAtendentes,repositorioClientes,repositorioProdutos,repositorioEncomendas)));
    }

    @FXML
    public void logout(){
        autenticacaoServico.logout();
        atualizaTela();
    }

    @FXML
    public void atualizaTela(){
        if(!autenticacaoServico.estaLogado()){
            root.getLeft().setVisible(false);
            painelCentral.getChildren().clear();
            painelCentral.getChildren().add(App.loadTela("fxml/login.fxml", a->new Login(autenticacaoServico,this)));
        }else {
            painelCentral.getChildren().clear();
            root.getLeft().setVisible(true);
        }

    }

    @FXML
    public void carregaTela(String tela){
        if(tela.equals("cadastro")){
            painelCentral.getChildren().clear();
            painelCentral.getChildren().add(App.loadTela("fxml/cadastro.fxml", a->new Login(autenticacaoServico, this)));

        }
    }
}
