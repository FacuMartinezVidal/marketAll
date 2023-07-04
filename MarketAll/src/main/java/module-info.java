module marketall.interfaz {
    requires javafx.controls;
    requires javafx.fxml;


    opens marketall.interfaz to javafx.fxml;
    exports marketall.interfaz;
}