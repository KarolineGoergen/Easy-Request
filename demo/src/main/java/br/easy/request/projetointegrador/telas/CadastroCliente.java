package br.easy.request.projetointegrador.telas;
    
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;

import br.easy.request.projetointegrador.models.Cliente;
import br.easy.request.projetointegrador.repositorios.RepositorioClientes;
import javafx.fxml.FXML;

public class CadastroCliente {
    
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

    private RepositorioClientes repositorioClientes;

    private Cliente clienteExistente = null;

    public CadastroCliente(RepositorioClientes repositorioClientes) {
        this.repositorioClientes = repositorioClientes;
    }

    public CadastroCliente(Cliente clienteExistente, RepositorioClientes repositorioClientes) {
        this.repositorioClientes = repositorioClientes;
        this.clienteExistente = clienteExistente;
    }

    public void initialize(){
        if( clienteExistente != null){
            tfNome.setText(clienteExistente.getNome());
            tfEmail.setText(clienteExistente.getEmail());
            tfCpf.setText(clienteExistente.getCpf());
            tfTelefone.setText(clienteExistente.getTelefone());

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
                
                if(clienteExistente != null){
                    ret = repositorioClientes.editarCliente(clienteExistente.getIdCliente(), nome, email, cpf, telefone);
                }else{
                    ret = repositorioClientes.cadastrarCliente(nome, email, cpf, telefone);
                }
                
                if (ret) {
                    msg = "Cliente cadastrado com sucesso!";
                    limpar();
                } else {
                    msg = "Erro ao cadastrar cliente!";
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

