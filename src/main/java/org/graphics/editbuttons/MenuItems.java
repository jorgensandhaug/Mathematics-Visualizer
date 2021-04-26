package org.graphics.editbuttons;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.graphics.ModalWindow;
import org.graphics.SimpleDialog;
import org.linalgfx.App;
import org.linalgfx.DefinedVariables;
import org.graphics.Icons;
import org.math.Editable;
import org.utils.DoubleFormatter;
import org.utils.Interpolatable;
import org.graphics.VariableContainer;
import org.math.Matrix;
import org.utils.RegexUtils;

/**
 * Abstract helper class for different MenuItems
 */
public abstract class MenuItems {
    /**
     * Returns a new MenuItem which has the functionality to interpolate the given variable
     * Takes in only variables which have underlying 2D or 3D objects which are interpolatable
     */
    public static MenuItem interpolate(VariableContainer<? extends Interpolatable> variableContainer){
        MenuItem transform = new MenuItem("Transform", Icons.of("transform.png", 20));
        transform.setOnAction(actionEvent ->{
            TextInputDialog dialog = new TextInputDialog();

            dialog.setHeaderText("Transform vector with a matrix");
            dialog.setContentText("Enter name of matrix");

            dialog.showAndWait().ifPresent(response ->{
                String name = dialog.getEditor().getText();
                if(DefinedVariables.contains(name)){
                    VariableContainer v = DefinedVariables.get(name);
                    if(v.getVariable() instanceof Matrix) {
                        try {
                            variableContainer.getVariable().startInterpolation((Matrix) v.getVariable(), 2000);
                        }catch (IllegalArgumentException e){
                            ModalWindow.alert("Could not transform: "+ variableContainer.getName() + " using the matrix: " + name + "\nError message: "+e.getMessage(), Alert.AlertType.ERROR);
                        }

                    }
                }
                else
                    ModalWindow.alert("That matrix is not defined!", Alert.AlertType.ERROR);
            });
        });

        return transform;
    }

    public static MenuItem editDoubleArray(VariableContainer<? extends Editable> variableContainer){
        MenuItem edit = new MenuItem("Edit", new ImageView(new Image(App.resourceURL("images/hammer.png"))));
        double[] doubles = variableContainer.getVariable().getCopy();
        edit.setOnAction(actionEvent ->{
            TextField[] coordInputs = new TextField[doubles.length];
            for(int i = 0; i < coordInputs.length; i++){
                coordInputs[i] = DoubleFormatter.getTextField(doubles[i]);
            }

            SimpleDialog dialog = new SimpleDialog("Edit name and element entries", coordInputs);

            dialog.getEditor().setText(variableContainer.getName());

            dialog.showAndWait().ifPresent(response -> {
                for (int i = 0; i < coordInputs.length; i++) {
                    doubles[i] = (double) coordInputs[i].getTextFormatter().getValue();
                }

                variableContainer.getVariable().set(doubles);
                
                
                String name = dialog.getEditor().getText();
                if (name.equals(variableContainer.getName()))
                    return;

                try {
                    if (!RegexUtils.isValidName(name)) {
                        ModalWindow.alert("The name is invalid! Name must start with a letter, and cant include any spaces.", Alert.AlertType.ERROR);
                        return;
                    }

                    variableContainer.setName(name);
                } catch (IllegalArgumentException e) {
                    ModalWindow.alert("That name is already in use!", Alert.AlertType.ERROR);
                }
            }
        );});

        return edit;
    }

    public static MenuItem invertMatrix(VariableContainer<Matrix> variableContainer){
        MenuItem invert = new MenuItem("Invert", Icons.of("inverse.png", 20));
        invert.setOnAction(actionEvent ->{
            try{
                variableContainer.getVariable().invert();
            }
            catch (Exception e){
                ModalWindow.alert("Matrix cannot be inverted. The determinant is 0.", javafx.scene.control.Alert.AlertType.ERROR);
            }
        });
        return invert;
    }
}
