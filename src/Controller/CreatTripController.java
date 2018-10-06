package Controller;

import Serialize.Trip;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class CreatTripController {
    private TextField nameInput, dateInput;
    private Stage stage;
    private Label name,date,selectFolderDir;
    private boolean succeed = true;
    private Trip trip = new Trip();
    @FXML
    private GridPane tableView;

    public Trip getTrip() {
        return trip;
    }

    private void createTrip(){
       String nameGet = nameInput.getText();
       if (nameGet.equals("")){
           nameInput.setText("Enter trip name");
       }
        String dateGet = dateInput.getText();
        if (dateGet.equals("")){
            dateInput.setText("Enter trip date");
        }
        if(selectFolderDir.getText().equals((" "))){
            selectFolderDir.setText("please select");
        }
        else if (!nameGet.equals("")){
            createFolder();
            trip.setName(nameGet);
            trip.setDate(dateGet);
            trip.setDir(selectFolderDir.getText()+ "\\" +nameGet);
            stage.close();
        }
    }

    private void createFolder(){
        new File(selectFolderDir.getText()+"\\"+nameInput.getText()).mkdir();
    }

    /**
     * select which folder you want to save your photos
     */
    public void selectFolder(){
        DirectoryChooser projectDirectory = new DirectoryChooser();
        projectDirectory.setTitle("Create Project Directory:");
        File refLocation = projectDirectory.showDialog(null);
        if (refLocation != null) {

            String location = refLocation.getPath();
            selectFolderDir.setText(location);
        }
    }
    public void initialize() {
        name = new Label("name");
        date = new Label("date");
        selectFolderDir = new Label(" ");
        nameInput = new TextField();
        dateInput = new TextField();
        tableView.setId("pane");
        Button enter = new Button("create");
        Button cancel = new Button("Cancel");
        Button selectFolder = new Button("select Folder");
        selectFolder.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)  {
                selectFolder();

            }
        });
        enter.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)  {
                createTrip();

            }
        });

        //TODO enter should be avaiable everywhere ,not only on date input
        dateInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    createTrip();
                }
            }
        });
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)  {
                succeed = false;
                stage.close();

            }
        });

        tableView.setAlignment(Pos.CENTER);
        tableView.setPadding(new Insets(10, 10, 10, 10));
        tableView.add(name,0,0);
        tableView.add(date,0,1);
        tableView.add(nameInput,1,0);
        tableView.add(dateInput,1,1);
        tableView.add(selectFolder,0,2);
        tableView.add(selectFolderDir,1,2);
        tableView.add(enter,2,3);
        tableView.add(cancel,0,3);
    }

    public boolean succeed(){
        return succeed;
    }

    public void setStage(){
        stage = (Stage)name.getScene().getWindow();
        stage.initStyle(StageStyle.UNDECORATED);
    }

}
