package org.graphics.editbuttons;

import javafx.scene.control.Alert;
import org.graphics.*;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.linalgfx.DefinedVariables;
import org.math.Complex;
import org.math.Matrix;
import org.canvas2d.Line2D;
import org.canvas2d.Point2D;
import org.canvas2d.Vector2D;
import org.utils.DoubleFormatter;
import org.utils.RegexUtils;
/**
 * The button to press for adding new elements in the 2D canvas, fills up the MenuItem list with clickable menuItems
 * for creating different types of 2D elements (vectors, points etc.)
 */
public class AddButton2D extends MenuButton {

    public AddButton2D() {
        super("Add 2D Shape");
        setGraphic(Icons.of("addnew.png", 30));
        getStyleClass().add("transparent-button");
        getStyleClass().add("new-menu-button");


        MenuItem vector = new MenuItem("Vector 2D", Icons.of("vector.png", 20));
        vector.getStyleClass().add("new-menu-item");
        vector.setOnAction(actionEvent -> {
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();

            SimpleDialog dialog = new SimpleDialog("Vector 2D", new Text("Enter x, y :   "), xInput, yInput);
            dialog.showAndWait().ifPresent(result ->{

                 if(!RegexUtils.isValidName(dialog.getEditor().getText())) {
                    ModalWindow.alertInvalidName();
                }

                DefinedVariables.add(new Vector2D((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue()), dialog.getEditor().getText());
            });
        });

        MenuItem matrix = new MenuItem("Matrix 2x2", Icons.of("matrix2d.png", 20));
        matrix.getStyleClass().add("new-menu-item");
        matrix.setOnAction(actionEvent -> {
            var aRow = new VBox();
            var bRow = new VBox();

            TextField aInput = DoubleFormatter.getTextField();
            TextField bInput = DoubleFormatter.getTextField();
            TextField cInput = DoubleFormatter.getTextField();
            TextField dInput = DoubleFormatter.getTextField();

            aRow.getChildren().addAll(aInput, cInput);
            bRow.getChildren().addAll(bInput, dInput);

            SimpleDialog dialog = new SimpleDialog("Matrix 2x2", new Text("Enter values:   "), aRow, bRow);
            dialog.showAndWait().ifPresent(response ->{
                 if(!RegexUtils.isValidName(dialog.getEditor().getText())) {
                    ModalWindow.alertInvalidName();
                    return;
                }

                DefinedVariables.add(new VariableContainer<>(new Matrix((double) aInput.getTextFormatter().getValue(), (double) bInput.getTextFormatter().getValue(), (double) cInput.getTextFormatter().getValue(), (double) dInput.getTextFormatter().getValue()), dialog.getEditor().getText()));
            });
        });

        MenuItem line = new MenuItem("Line", Icons.of("line.png", 20));
        line.getStyleClass().add("new-menu-item");
        line.setOnAction(actionEvent -> {
            var lineInputRows = new VBox();
            var aRow = new HBox();
            var bRow = new HBox();

            TextField aInput = DoubleFormatter.getTextField();
            TextField bInput = DoubleFormatter.getTextField();
            TextField cInput = DoubleFormatter.getTextField();
            TextField dInput = DoubleFormatter.getTextField();

            aRow.getChildren().addAll(aInput, bInput);
            bRow.getChildren().addAll(cInput, dInput);

            lineInputRows.getChildren().addAll(new Text("Enter point:   "), aRow, new Text("Enter directional vector:   "), bRow);
            SimpleDialog dialog = new SimpleDialog("Line", lineInputRows);
            dialog.showAndWait().ifPresent(response ->{
                 if(!RegexUtils.isValidName(dialog.getEditor().getText())) {
                    ModalWindow.alertInvalidName();
                    return;
                }

                DefinedVariables.add(new Line2D(new Point2D((double) aInput.getTextFormatter().getValue(), (double) bInput.getTextFormatter().getValue()), new Vector2D((double) cInput.getTextFormatter().getValue(), (double) dInput.getTextFormatter().getValue())), dialog.getEditor().getText());
            });
        });



        MenuItem complex = new MenuItem("Complex", Icons.of("i.png", 20));
        complex.getStyleClass().add("new-menu-item");
        complex.setOnAction(actionEvent -> {
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();

            SimpleDialog dialog = new SimpleDialog("Complex Number", new Text("Enter a, b:   "), xInput, yInput);

            dialog.showAndWait().ifPresent(response ->{
                 if(!RegexUtils.isValidName(dialog.getEditor().getText())) {
                    ModalWindow.alertInvalidName();
                    return;
                }

                DefinedVariables.add(new VariableContainer<>(new Complex((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue()), dialog.getEditor().getText()));
            });
        });


        MenuItem point = new MenuItem("Point", Icons.of("point.png", 20));
        point.getStyleClass().add("new-menu-item");
        point.setOnAction(actionEvent -> {
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();

            SimpleDialog dialog = new SimpleDialog("Point", new Text("Enter x, y:   "), xInput, yInput);
            dialog.showAndWait().ifPresent(response ->{
                 if(!RegexUtils.isValidName(dialog.getEditor().getText())) {
                    ModalWindow.alertInvalidName();
                    return;
                }

                DefinedVariables.add(new Point2D((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue()), dialog.getEditor().getText());
            });
        });



        MenuItem scalar = new MenuItem("Scalar", Icons.of("number.png", 20));
        scalar.getStyleClass().add("new-menu-item");
        scalar.setOnAction(actionEvent -> {
            TextField input = DoubleFormatter.getTextField();

            SimpleDialog dialog = new SimpleDialog("Scalar", new Text("Enter scalar:   "), input);
            dialog.showAndWait().ifPresent(response ->{
                 if(!RegexUtils.isValidName(dialog.getEditor().getText())) {
                    ModalWindow.alertInvalidName();
                    return;
                }

                DefinedVariables.add(new VariableContainer<>((Double) input.getTextFormatter().getValue(), dialog.getEditor().getText()));
            });
        });




        getItems().addAll(vector, complex, matrix, line, point, scalar);

    }
}
