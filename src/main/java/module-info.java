module org.linalgfx {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.linalgfx to javafx.fxml;
    exports org.linalgfx;
    exports org.graphics.editbuttons;
}