package Controller;

import Panes.AllPhotoPane;
import Panes.PhotoPane;
import Serialize.Photo;
import Serialize.Trip;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;


public class TripDisplayController {
    @FXML
    private Pane tableView;
    private Trip trip;
    private Scene previous;
    private AllPhotoPane allPhotoPane;
    private FlowPane photos = new FlowPane();
    private ScrollPane photoScroll = new ScrollPane();

    private ArrayList<PhotoPane> photoList = new ArrayList<>();


    public void initialize(){
        photoScroll.setMinSize(400,400);
        photos.setMinSize(380,380);
    }

    public void setUp(Trip trip, Scene previous){
        this.previous = previous;
        this.trip = trip;

        allPhotoPane = new AllPhotoPane(trip);
        allPhotoPane.setUp();

        //go back to previous scene
        Image image = new Image(getClass().getResourceAsStream("../images/back.png"));
        Button back = new Button();
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        back.setGraphic(imageView);
        back.setId("imageButton");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Stage stage = (Stage)tableView.getScene().getWindow();
                stage.setScene(previous);
            }
        });
        back.setLayoutX(10);
        back.setLayoutY(10);


        tableView.getChildren().addAll(back,allPhotoPane.getPane());

    }


}
