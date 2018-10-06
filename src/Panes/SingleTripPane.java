package Panes;

import Controller.AllTripsController;
import Controller.TripDisplayController;
import Serialize.Trip;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SingleTripPane{
    private Pane pane = new Pane();
    private Trip trip;

    private SingleTripPane self = this;
    private AllTripsController controller;
    private Image deleteImage = new Image(getClass().getResourceAsStream("../images/Delete.png"));
    private ImageView deleteView;
    private Button delete = new Button();


    public SingleTripPane(Trip trip, AllTripsController controller){
        this.trip = trip;
        this.controller = controller;

        pane.setMinSize(140, 50);


        deleteView = new ImageView(deleteImage);
        deleteView.setFitWidth(30);
        deleteView.setFitHeight(30);

        delete.setLayoutY(10);
        delete.setLayoutX(0);
        delete.setUserData(trip);
        delete.setGraphic(deleteView);
        delete.setId("imageButton");
        delete.setMinSize(30,30);
        delete.setMaxSize(30,30);


        setup();
    }
    private void setup(){
        Label tripName = new Label(trip.getName());
        Button tripbutton = new Button();
        tripbutton.setUserData(trip);
        tripName.setLayoutY(10);
        tripName.setLayoutX(30);
        tripbutton.setId("imageButton");
        tripbutton.setMinSize(140,50);
        tripbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)  {
                try{
                    Stage stage = (Stage)controller.getScene().getWindow();
                    Scene currentScene = controller.getScene();
                    FXMLLoader triploader  = new FXMLLoader(getClass().getResource("../Controller/TripDisplay.fxml"));
                    Parent root = triploader.load();
                    Scene scene = new Scene(root,500,500);
                    scene.getStylesheets().add("stylesheet.css");
                    stage.setScene(scene);
                    TripDisplayController tripController = triploader.getController();
                    tripController.setUp(trip,currentScene);

                }
                catch (IOException ex){
                    System.out.println("trip not found");
                }

            }
        });

        pane.getChildren().addAll(tripbutton,tripName);
    }

    public void manage(){
        pane.getChildren().add(delete);
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)  {
                //trips.removeTrip(trip);
                controller.removeTrip(self);
            }
        });
    }

    public void manageDone(){
        pane.getChildren().remove(delete);
    }

    public Pane getPane(){
        return pane;
    }

    public Trip getTrip() {
        return trip;
    }
}
