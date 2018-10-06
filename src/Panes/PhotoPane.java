package Panes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import Serialize.Photo;
public class PhotoPane {
    private Photo photo;
    private  ImageView display;
    private Pane pane = new Pane();
    private int photoSize;
    private Image image;
    private int pic_num;
    public PhotoPane(Photo photo, int photoSize, int num){
        this.photo = photo;
        this.photoSize = photoSize;
        pic_num = num;
        try {
            File file = new File(photo.getPath());
            image = new Image(new FileInputStream(file));


        }catch(FileNotFoundException e){
            System.out.println("BREAK");
        }
        setUp();

        pane.getChildren().addAll(display);
    }

    public PhotoPane(){

    }

    /**
     * set up init value
     */
    public void setUp(){
        display = new ImageView(image);
        display.setFitHeight(photoSize);
        display.setFitWidth(photoSize);
        display.setPickOnBounds(true); // allows click on transparent areas
        display.setOnMouseClicked((MouseEvent e) -> {
            System.out.println("Clicked!"); // change functionality
        });
    }

    public PhotoPane clonePane(int photoSize){
        PhotoPane returnPane = new PhotoPane();
        returnPane.photoSize = photoSize;
        returnPane.setUp();
        returnPane.display.setImage(display.getImage());
        returnPane.pane.getChildren().addAll(returnPane.display);

        return returnPane;
    }
    public Pane getPane() {
        return pane;
    }

    public int getPic_num() {
        return pic_num;
    }

    public Button test(){
        return new Button("WHY");
    }
}
