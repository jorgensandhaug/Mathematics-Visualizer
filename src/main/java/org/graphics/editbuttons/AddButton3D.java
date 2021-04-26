package org.graphics.editbuttons;

import org.canvas3d.*;
import org.graphics.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.linalgfx.DefinedVariables;
import org.math.Matrix;
import org.math3d.Vector3;
import org.utils.DoubleFormatter;
import org.utils.RegexUtils;
import org.terraingeneration.InfiniteTerrain;

import java.util.HashMap;
import java.util.Map;
import java.util.Map;

/**
 * The button to press for adding new elements in the 3D canvas, fills up the MenuItem list with clickable menuItems
 * for creating different types of 3D elements (vectors, points etc.)
 */
public class AddButton3D extends MenuButton {

    public AddButton3D() {
        super("Add 3D shape");
        setGraphic(Icons.of("addnew.png", 30));
        getStyleClass().add("transparent-button");
        getStyleClass().add("new-menu-button");


        MenuItem meshItem = new MenuItem("Mesh");
        meshItem.getStyleClass().add("new-menu-item");
        meshItem.setOnAction(actionEvent -> {


            //Creates a set of radiobuttons to pick what mesh to create
            ToggleGroup toggleGroup = new ToggleGroup();
            RadioButton terrain = new RadioButton("Infinite Terrain");
            VBox vBox = new VBox(terrain);
            vBox.setStyle("-fx-spacing: 5px;");
            terrain.setToggleGroup(toggleGroup);
            terrain.setSelected(true);

            Map<String, String> nameToFileMap = new HashMap<>();
            nameToFileMap.put("Chevrolet", "chevrolet.obj");
            nameToFileMap.put("Dragon", "dragon.obj");




            nameToFileMap.forEach( (key, value) -> {
                RadioButton rb = new RadioButton(key);
                vBox.getChildren().add(rb);
                rb.setToggleGroup(toggleGroup);
            });

            SimpleDialog dialog = new SimpleDialog("Select Mesh to add to canvas", vBox);


            dialog.showAndWait().ifPresent(response ->{
                if(!RegexUtils.isValidName(dialog.getEditor().getText())) {
                    ModalWindow.alertInvalidName();
                    return;
                }

                String selected = ((RadioButton) toggleGroup.getSelectedToggle()).getText();

                if(selected.equals("Infinite Terrain"))
                    DefinedVariables.add(new InfiniteTerrain(), dialog.getEditor().getText());

                else
                    DefinedVariables.add(new Mesh(nameToFileMap.get(selected), Vector3.ZERO(), 1), dialog.getEditor().getText());

            });
        });


        MenuItem vector = new MenuItem("Vector 3D", Icons.of("vector.png", 20));
        vector.getStyleClass().add("new-menu-item");
        vector.setOnAction(actionEvent -> {
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();
            TextField zInput = DoubleFormatter.getTextField();

            SimpleDialog dialog = new SimpleDialog("Vector 3D", new Text("Enter x, y, z :   "), xInput, yInput, zInput);

            dialog.showAndWait().ifPresent(response ->{

                 if(!RegexUtils.isValidName(dialog.getEditor().getText())) {
                    ModalWindow.alertInvalidName();
                    return;
                }

                DefinedVariables.add(new Vector3D((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue(), (double) zInput.getTextFormatter().getValue()), dialog.getEditor().getText());
            });
        });

        MenuItem matrix = new MenuItem("Matrix 3x3", Icons.of("matrix2d.png", 20));
        matrix.getStyleClass().add("new-menu-item");
        matrix.setOnAction(actionEvent -> {
            var matrixInputRows = new VBox();
            var aRow = new HBox();
            var bRow = new HBox();
            var cRow = new HBox();

            TextField[][] inputs = new TextField[3][3];
            for(int i = 0; i < 3; i++)
                for(int j = 0; j < 3; j++)
                    inputs[i][j] = DoubleFormatter.getTextField();

            aRow.getChildren().addAll(inputs[0]);
            bRow.getChildren().addAll(inputs[1]);
            cRow.getChildren().addAll(inputs[2]);

            matrixInputRows.getChildren().addAll(new Text("Enter values:   "), aRow, bRow, cRow);

            SimpleDialog dialog = new SimpleDialog("Matrix 3x3", matrixInputRows);

            dialog.showAndWait().ifPresent(response ->{
                 if(!RegexUtils.isValidName(dialog.getEditor().getText())) {
                    ModalWindow.alertInvalidName();
                    return;
                }


                double[][] mat = new double[3][3];
                for(int i = 0; i < 3; i++)
                    for(int j = 0; j< 3; j++)
                        mat[i][j] = ((double) inputs[i][j].getTextFormatter().getValue());

                DefinedVariables.add(new VariableContainer<>(new Matrix(mat), dialog.getEditor().getText()));
            });
        });



        MenuItem point = new MenuItem("Point 3D", Icons.of("point.png", 20));
        point.getStyleClass().add("new-menu-item");
        point.setOnAction(actionEvent -> {
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();
            TextField zInput = DoubleFormatter.getTextField();

           SimpleDialog dialog = new SimpleDialog("Point 3D", new Text("Enter x, y, z:   "), xInput, yInput, zInput);
            dialog.showAndWait().ifPresent(response ->{
                 if(!RegexUtils.isValidName(dialog.getEditor().getText())) {
                    ModalWindow.alertInvalidName();
                    return;
                }

                DefinedVariables.add(new Point3D((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue(), (double) zInput.getTextFormatter().getValue()), dialog.getEditor().getText());
            });
        });


        //Cube
        MenuItem cube = new MenuItem("Cube");
        cube.getStyleClass().add("new-menu-item");
        cube.setOnAction(actionEvent -> {
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();
            TextField zInput = DoubleFormatter.getTextField();

            TextField rInput = DoubleFormatter.getTextField(1);


            SimpleDialog dialog = new SimpleDialog("Cube", new Text("Enter x, y, z:   "), xInput, yInput, zInput, new Text("Enter size"), rInput);

            dialog.showAndWait().ifPresent(response ->{
                 if(!RegexUtils.isValidName(dialog.getEditor().getText())) {
                    ModalWindow.alertInvalidName();
                    return;
                }

                DefinedVariables.add(new Cube(new Vector3((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue(), (double) zInput.getTextFormatter().getValue()), ((double) rInput.getTextFormatter().getValue())), dialog.getEditor().getText());
            });
        });


        //Sphere
        MenuItem sphere = new MenuItem("Sphere");
        sphere.getStyleClass().add("new-menu-item");
        sphere.setOnAction(actionEvent -> {
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();
            TextField zInput = DoubleFormatter.getTextField();

            TextField rInput = DoubleFormatter.getTextField(1);

            SimpleDialog dialog = new SimpleDialog("Sphere", new Text("Enter x, y, z:   "), xInput, yInput, zInput, new Text("Enter radius"), rInput);

            dialog.showAndWait().ifPresent(response ->{
                 if(!RegexUtils.isValidName(dialog.getEditor().getText())) {
                    ModalWindow.alertInvalidName();
                    return;
                }

                DefinedVariables.add(new Sphere(new Vector3((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue(), (double) zInput.getTextFormatter().getValue()), ((double) rInput.getTextFormatter().getValue())), dialog.getEditor().getText());
            });
        });

        //lightbulb
        MenuItem lightBulb = new MenuItem("Light Bulb", Icons.of("lightbulb.png", 20));
        lightBulb.getStyleClass().add("new-menu-item");
        lightBulb.setOnAction(actionEvent -> {
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();
            TextField zInput = DoubleFormatter.getTextField();

            SimpleDialog dialog = new SimpleDialog("Light Bulb", new Text("Position:\t"), xInput, yInput, zInput);

            dialog.showAndWait().ifPresent(response ->{

                 if(!RegexUtils.isValidName(dialog.getEditor().getText())) {
                    ModalWindow.alertInvalidName();
                    return;
                }

                DefinedVariables.add(new LightSource(new Vector3((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue(), (double) zInput.getTextFormatter().getValue())), dialog.getEditor().getText());
            });
        });

        getItems().addAll(meshItem, vector, matrix, point, cube, sphere, lightBulb);
    }
}

