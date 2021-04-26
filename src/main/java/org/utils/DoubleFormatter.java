package org.utils;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * Handles formatting of real numbers
 */

public class DoubleFormatter{

    /**
     * Returns a TextFormatter for formatting doubles, used in almost all inputfields where the user should input a number
     */
    public static TextFormatter<Double> getFormatter(){
        Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c ;
            } else {
                return null ;
            }
        };

        StringConverter<Double> converter = new StringConverter<Double>() {

            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                    return 0.0 ;
                } else {

                    return Double.valueOf(s);
                }
            }


            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };

        return new TextFormatter<>(converter, 0.0, filter);
    }

    /**
     * Returns a TextField with the specified attributes
     */
    public static TextField getTextField(){
        var textField = new TextField();
        textField.setTextFormatter(getFormatter());
        return textField;
    }

    /**
     * Returns a TextField with the specified attributes and a default value
     */
    public static TextField getTextField(double current){
        var textField = new TextField();
        textField.setTextFormatter(getFormatter());

        textField.setText(Double.toString(current));
        return textField;
    }
}
