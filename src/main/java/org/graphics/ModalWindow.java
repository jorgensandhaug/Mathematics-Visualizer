package org.graphics;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;


public abstract class ModalWindow {
    public static Optional<ButtonType> alert(String message, AlertType alertType){
        Alert alert = new Alert(alertType, message);
        alert.setHeaderText(alertType.name());
        alert.setTitle(alertType.name());
        return alert.showAndWait();
    }

    public static void alertInvalidName(){
        alert("That name is invalid, please try again.\n\nLegal names must:\n1. Start with a letter\n2. Can only include letters, numbers and underscores", AlertType.ERROR);
    }
}
