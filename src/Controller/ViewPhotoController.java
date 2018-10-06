package Controller;

import Panes.PhotoPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class ViewPhotoController {
    @FXML
    private Pane tableView;
    private PhotoPane current_photo;
    private ArrayList<PhotoPane> photo_list;
    private int index;
    private int screenSize = 600;
    private Pane imagePane = new Pane();
    private Button leftButton, rightButton;

    public void initialize(){
        imagePane.setMinSize(screenSize,screenSize);
        leftButton = new Button();
        rightButton = new Button();
        Image rightImage = new Image(getClass().getResourceAsStream("../images/right.png"));
        ImageView rightView = new ImageView(rightImage);
        rightButton.setGraphic(rightView);
        rightButton.setId("imageButton");
        rightButton.setLayoutX(screenSize-50);
        rightButton.setLayoutY((screenSize/2)-25);
        rightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)  {
                switchPic(1);
            }
        });

        Image leftImage = new Image(getClass().getResourceAsStream("../images/left.png"));
        ImageView leftView = new ImageView(leftImage);
        leftButton.setGraphic(leftView);
        leftButton.setId("imageButton");
        leftButton.setLayoutX(0);
        leftButton.setLayoutY((screenSize/2)-25);
        leftButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)  {
                switchPic(-1);
            }
        });
    }
    public void switchPic(int dire){
       index += dire;
        if(index == photo_list.size()){
            index = 0;
        }
        else if(index == -1){
            index = photo_list.size()-1;
        }

       imagePane.getChildren().remove(0);
        imagePane.getChildren().addAll(photo_list.get(index).getPane());
    }

    public void setup(ArrayList<PhotoPane> photo_list, int index){
        this.index = index;
        this.photo_list = photo_list;
        imagePane.getChildren().addAll(photo_list.get(index).getPane());
        tableView.getChildren().addAll(imagePane,rightButton,leftButton);

    }
}
