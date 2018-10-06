package Controller;

import Manager.TripManager;
import Manager.TripPaneManager;
import Panes.SingleTripPane;
import Serialize.Trip;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

public class AllTripsController{
    private Button createTrip = new Button();
    private final TripManager tripManager = TripManager.getTripManager();
    private final TripPaneManager tripPaneManager = TripPaneManager.getTripManager();
    @FXML private Pane tableView;
    private VBox trips = new VBox();
    private ScrollPane tripsScroll = new ScrollPane();
    private Pane topPane = new Pane();

    private void edit(Button b){
        if(b.getText().equals("edit")){
            b.setText("Done");
            for(SingleTripPane i : tripPaneManager){
                i.manage();
            }
        }
        else{
            b.setText("edit");
            for(SingleTripPane i : tripPaneManager){
                i.manageDone();
            }
        }

    }
    private void updateTrip() throws IOException{
        Stage window = new Stage();
        window.setTitle("Create your trip");
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("creatTrip.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root, 300, 200);
        scene.getStylesheets().add("stylesheet.css");
        window.setScene(scene);
        CreatTripController controller = loader.getController();
        controller.setStage();
        window.showAndWait();

        if(controller.succeed()){
            Trip trip = controller.getTrip();
            SingleTripPane tripPane = new SingleTripPane(trip, this);
            tripPane.getPane().setId("tripBox");
            tripManager.addTrip(trip);
            tripPaneManager.addTrip(tripPane);
            trips.getChildren().add(tripPane.getPane());

        }
    }

    /**
     * serialze tripManager object
     */
    public void serialize(){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("Manager.TripManager.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tripManager);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * remove a Panes.SingleTripPane
     * @param pane
     */
    public void removeTrip(SingleTripPane pane){
        trips.getChildren().remove(pane.getPane());
        tripManager.removeTrip(pane.getTrip());
        tripPaneManager.removeTrip(pane);
    }

    /**
     * load the previous saved object
     */
    private void loadSavedInfo(){
        TripManager e = null;
        try {
            FileInputStream fileIn = new FileInputStream("Manager.TripManager.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (TripManager) in.readObject();
            tripManager.loadSavedInfo(e);
            for(Trip t: tripManager){
                SingleTripPane pane = new SingleTripPane(t,this);
                pane.getPane().setId("tripBox");
                tripPaneManager.addTrip(pane);
                trips.getChildren().add(pane.getPane());
            }
            in.close();
            fileIn.close();
        } catch (IOException i) {
            System.out.println("failed to load Manager.TripManager");
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("failed to find Manager.TripManager class");
            c.printStackTrace();
            return;
        }
    }

    /**
     *
     * @return this scene
     */
    public Scene getScene(){
        return tableView.getScene();
    }

    /**
     * init the scene
     */
    public void initialize(){
        createTrip.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)  {
                try{
                    updateTrip();
                }
                catch (IOException ex){

                }

            }
        });
        createTrip.setText("new trip");
        createTrip.setLayoutX(0);
        createTrip.setLayoutY(5);

        tripsScroll.setLayoutX(0);
        tripsScroll.setLayoutY(30);
        tripsScroll.setContent(trips);
        tripsScroll.setMinSize(150,400);
        tripsScroll.setMaxHeight(400);

        Button edit  = new Button("edit");
        edit.setLayoutX(100);
        edit.setLayoutY(5);
        edit.setMinSize(60,20);

        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)  {
                    edit(edit);
            }
        });

        topPane.setLayoutX(0);
        topPane.setLayoutY(0);
        topPane.getChildren().addAll(edit, createTrip);

        tableView.getChildren().addAll(tripsScroll, topPane);
        loadSavedInfo();


    }

}
