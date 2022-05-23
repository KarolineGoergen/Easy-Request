package br.easy.request.projetointegrador.telas;

import java.sql.SQLException;

import br.easy.request.projetointegrador.models.Atendente;
import br.easy.request.projetointegrador.repositorios.RepositorioAtendentes;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CadastroAtendente {

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfCpf;

    @FXML
    private TextField tfTelefone;

    @FXML
    private Button btCadastrar;

    private RepositorioAtendentes repositorioAtendentes;

    private Atendente atendenteExistente = null;

    public CadastroAtendente(RepositorioAtendentes repositorioAtendentes) {
        this.repositorioAtendentes = repositorioAtendentes;
    }

    public CadastroAtendente(Atendente atendenteExistente, RepositorioAtendentes repositorioAtendentes) {
        this.repositorioAtendentes = repositorioAtendentes;
        this.atendenteExistente = atendenteExistente;
    }

    public void initialize(){
        if( atendenteExistente != null){
            tfNome.setText(atendenteExistente.getNome());
            tfEmail.setText(atendenteExistente.getEmail());
            tfCpf.setText(atendenteExistente.getCpf());
            tfTelefone.setText(atendenteExistente.getTelefone());

            btCadastrar.setText("Atualizar");

        }
    }

    @FXML
    private void cadastrar() {
        String nome = tfNome.getText();
        String email = tfEmail.getText();
        String cpf = tfCpf.getText();
        String telefone = tfTelefone.getText();

        boolean temErro = false;
        String msg = "";

        if (nome.isEmpty() || nome.isBlank()) {
            temErro = true;
            msg += "O campo Nome está vazio!\n";
        }

        if (email.isEmpty() || email.isBlank()) {
            temErro = true;
            msg += "O campo E-mail está vazio!\n";
        }

        if (cpf.isEmpty() || cpf.isBlank()) {
            temErro = true;
            msg += "O campo CPF está vazio!\n";
        }

        if (telefone.isEmpty() || telefone.isBlank()) {
            temErro = true;
            msg += "O campo Telefone está vazio!\n";
        }

        if (!temErro) {
            try {
                boolean ret;
                
                if(atendenteExistente != null){
                    ret = repositorioAtendentes.editarAtendente(atendenteExistente.getIdAtendente(), nome, email, cpf, telefone);
                }else{
                    ret = repositorioAtendentes.cadastrarAtendente(nome, email, cpf, telefone);
                }
                
                if (ret) {
                    msg = "Atendente cadastrado com sucesso!";
                    limpar();
                } else {
                    msg = "Erro ao cadastrar atendente!";
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
        tfEmail.clear();
        tfCpf.clear();
        tfTelefone.clear();
    }

    
}
