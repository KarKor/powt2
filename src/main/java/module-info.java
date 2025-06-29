module pl.umcs.oop.powt2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens pl.umcs.oop.powt2 to javafx.fxml;
    exports pl.umcs.oop.powt2;
}