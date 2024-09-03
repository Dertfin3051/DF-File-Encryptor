module ru.dfhub.dfe {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens ru.dfhub.dfe to javafx.fxml;
    exports ru.dfhub.dfe;
}