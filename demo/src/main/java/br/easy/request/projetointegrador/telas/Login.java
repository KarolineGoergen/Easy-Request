package br.easy.request.projetointegrador.telas;

import br.easy.request.projetointegrador.servicos.AutenticacaoServico;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.sql.SQLException;

public class Login {

    @FXML
    private PasswordField pfSenha;

    @FXML
    private TextField tfUsuario;

    @FXML
    private FlowPane root;

    private AutenticacaoServico autenticacaoServico;
    private Home home;


    public Login(AutenticacaoServico autenticacaoServico, Home home){
        this.autenticacaoServico = autenticacaoServico;
        this.home = home;

    }

    @FXML
    public void logar(){
        String usuario = tfUsuario.getText();
        String senha = pfSenha.getText();
        String msg = "Usuário ou senha incorretos";


        try{
            autenticacaoServico.logar(usuario, senha);

            if(autenticacaoServico.estaLogado()){
                msg = "Usuario logado com sucesso";
                home.atualizaTela();
            }
        }catch(Exception e){
            msg = e.getMessage();

        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION,msg);
        alert.showAndWait();
    }

    @FXML
    public void cadastrar() throws SQLException {
        String usuario = tfUsuario.getText();
        String senha = pfSenha.getText();
        String msg = "Usuário ja existente";
        boolean temErro = false;

        if (usuario.isEmpty() || usuario.isBlank()) {
            temErro = true;
            msg += "Usuário não pode ser vazio!\n";
        }
        if (senha.isEmpty() || senha.isBlank()) {
            temErro = true;
            msg += "Senha não pode ser vazio!\n";
        }

        if(!temErro) {
            try {
                boolean ret;
                ret = autenticacaoServico.cadastrar(usuario, senha);

                if(ret){
                    msg = "Atendente cadastrado com sucesso";
                    limpar();
                }

            } catch (Exception e) {
                msg = e.getMessage();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
            alert.showAndWait();
        }
    }

    @FXML
    public void cadastrar2() {
        home.carregaTela("cadastro");

    }

    @FXML
    public void login(){
        home.atualizaTela();
    }

    @FXML
    private void limpar() {
        tfUsuario.clear();
        pfSenha.clear();
    }
}
