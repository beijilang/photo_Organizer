import Controller.AllTripsController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Organizer extends Application {
    Button button;
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Organizer");
        primaryStage.setResizable(false);

        button = new Button();
        button.setText("new trip");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Controller/allTrips.fxml"));
        Parent root = loader.load();
        AllTripsController allTripController = loader.getController();
        Scene scene = new Scene(root, 500, 500);
        scene.getStylesheets().add("stylesheet.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                allTripController.serialize();
            }
        });
    }
}
