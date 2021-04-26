package org.graphics.editbuttons;


import org.canvas2d.Render2D;
import org.canvas3d.Render3D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import org.linalgfx.App;

/**
 * Represents the button used to toggle graphical representation of defined variables
 */
public class ShowHideButton extends Region {
    private final Render2D render2D;
    private final Render3D render3D;
    private final Image visible = new Image(App.resourceURL("images/visible.png"));
    private final Image hidden = new Image(App.resourceURL("images/hidden.png"));
    private final ImageView imageView = new ImageView();

    /**
     * Creates a button for a specific variable
     */
    public ShowHideButton(Object variable){
        super();
        if(variable instanceof Render2D) {
            this.render2D = (Render2D) variable;
            this.render3D = null;

            imageView.setImage(visible);
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);

            getStyleClass().add("showhide");


            setOnMouseClicked(ev -> {
                if (render2D.isHidden()){
                    render2D.show();
                    imageView.setImage(visible);
                }
                else {
                    render2D.hide();
                    imageView.setImage(hidden);
                }
            });
        }
        else if(variable instanceof Render3D){
            this.render3D = (Render3D) variable;
            this.render2D = null;

            imageView.setImage(visible);
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);

            getStyleClass().add("showhide");


            setOnMouseClicked(ev -> {
                if (render3D.isHidden()){
                    render3D.show();
                    imageView.setImage(visible);
                }
                else {
                    render3D.hide();
                    imageView.setImage(hidden);
                }
            });
        }
        else {
            render2D = null;
            render3D = null;
            imageView.setImage(hidden);
        }

        getChildren().add(imageView);
    }
}
