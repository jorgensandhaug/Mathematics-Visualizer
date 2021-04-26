package org.graphics;


import org.canvas2d.Render2D;
import org.canvas3d.Render3D;
import org.graphics.editbuttons.GenericEditButton;
import org.graphics.editbuttons.ShowHideButton;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import org.canvas2d.Mapping;
import org.linalgfx.DefinedVariables;
import org.linalgfx.Writable;



/**
 * Handles the textual representation of mathematical variables in the GUI
 */
public class VariableContainer<T> extends HBox {
    private T variable;
    private String name;
    private final transient Text contentField;
    private final transient Text nameField;
    private final transient ColorPicker colorPicker;
    //private final Pane spacer = new Pane();


    public VariableContainer(T variable, String name){
        this.variable = variable;
        this.name = name;
        contentField = new Text();
        nameField = new Text();
        updateContentText();
        updateNameText();
        nameField.getStyleClass().add("variable-name");
        contentField.getStyleClass().add("variable-content");
        HBox nameWrapper = new HBox(nameField, contentField);
        nameWrapper.getStyleClass().add("variable-text");

        GenericEditButton editButton = new GenericEditButton(this);
        ShowHideButton showHideButton = new ShowHideButton(variable);

        colorPicker = new ColorPicker(Color.BLACK);
        colorPicker.getStyleClass().add("transparent-button");
        colorPicker.setPrefWidth(32.5);

        //DefinedVariables.getVBox().getChildren().add(nameWrapper);


        getChildren().addAll(showHideButton, colorPicker, editButton, nameWrapper);

        getStyleClass().add("variable");


        //set name for renderables
        if(variable instanceof Render3D)
            ((Render3D) variable).setName(name);
        else if(variable instanceof Render2D)
            ((Render2D) variable).setName(name);


        colorPicker.setOnAction(a ->{
            setColor(colorPicker.getValue());
        });
    }

    /**
     * Sets the color of the VariableContainer
     */
    public void setColor(Color color){
        if(variable instanceof Render3D)
            ((Render3D) variable).setColor(color);
        else if(variable instanceof Render2D)
            ((Render2D) variable).setPaint(getPaint());
    }

    /**
     * Deletes the variable container
     */
    public boolean delete(){
        return DefinedVariables.remove(getName());
    }

    /**
     * Returns the Paint selected in the colorPicker
     */
    public Paint getPaint(){
        return Paint.valueOf(colorPicker.getValue().toString());
    }

    /**
     * Returns the Color selected in the colorPicker
     */
    public Color getColor(){
        return colorPicker.getValue();
    }
    @Override
    public String toString(){
        if(variable instanceof Mapping)
            return name + "(x) = " + variable.toString();

        return name + " = " + variable.toString();
    }

    public boolean equals(VariableContainer other){
        return other.variable.equals(variable);
    }

    /**
     * Returns the name of a variable as a String
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the variable from the container
     */
    public T getVariable(){
        return variable;
    }

    /**
     * Returns the mathematical variable object from of the variable from the container
     */
    public Object getMath(){
        if(variable instanceof Render3D)
            return ((Render3D) variable).getMath();
        else if(variable instanceof Render2D)
            return ((Render2D) variable).getMath();
        else
            return getVariable();
    }

    /**
     * Sets the name of the VariableContainer
     */
    public void setName(String name) throws IllegalArgumentException{
        if(DefinedVariables.contains(name))
            throw new IllegalArgumentException("Name already exist!");

        //dette kan kanskje gjøres på en bedre måte
        DefinedVariables.remove(this);

        this.name = name;
        updateNameText();
        updateContentText();

        DefinedVariables.add(this);

        if(variable instanceof Render3D)
            ((Render3D) variable).setName(name);
        else if(variable instanceof Render2D)
            ((Render2D) variable).setName(name);
    }

    /**
     * Updates the representation of the variable in the GUI
     */
    public void updateContentText(){
        contentField.setText(variable.toString());
    }

    /**
     * Updates the name of the variable in the GUI
     */
    private void updateNameText(){
        String[] classPath = variable.getClass().getName().split("\\.");
        nameField.setText("(" + classPath[classPath.length-1] + ")\t" + name);
    }

    /**
     * Sets the variable of the VariableContainer
     */
    public void setVariable(T variable){
        this.variable = variable;
        contentField.setText(variable.toString());
    }

    /**
     * Gets the string representation to be stores in the txt save file
     */
    public String toFile(){
        if(variable instanceof Writable)
            return name+"---"+((Writable)variable).writeString();
        return null;
    }
}
