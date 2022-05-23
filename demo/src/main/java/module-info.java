module br.easy.request.projetointegrador {
    requires javafx.controls;
    requires java.sql;
    requires javafx.fxml;
    exports br.easy.request.projetointegrador;
    opens br.easy.request.projetointegrador.telas to javafx.fxml;
}
