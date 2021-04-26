package org.graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.linalgfx.App;

/**
 * Helper class for loading simple images (icons)
 */
public abstract class Icons {

    /**
     * Tries loading an image from file and return an ImageView containing that image which can be displayed
     */
    public static ImageView of(String fileName, double size){
        try {
            String url = App.resourceURL("images/" + fileName);
            ImageView imageView = new ImageView(new Image(url));
            imageView.setFitHeight(size);
            imageView.setFitWidth(size);
            return imageView;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
