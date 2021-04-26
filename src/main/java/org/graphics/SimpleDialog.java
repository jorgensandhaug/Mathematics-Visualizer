package org.graphics;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;

// Dette er en hjelpeklasse for å enkelt kunne opprette nye dialog-vinduer med kun ett input-felt
public class SimpleDialog extends TextInputDialog{
    public SimpleDialog(String header, Node... extras){
        super();
        setContentText("Enter name: ");
        setHeaderText(header);

        setGraphic(new HBox(extras));

        initModality(Modality.APPLICATION_MODAL);

        javafx.scene.control.TextField inputField = getEditor();
        javafx.scene.control.Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);

        inputField.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });
    }
}
