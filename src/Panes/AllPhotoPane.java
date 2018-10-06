package Panes;

import Controller.ViewPhotoController;
import Serialize.Photo;
import Serialize.Trip;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllPhotoPane {
    private FlowPane photos = new FlowPane();
    private ScrollPane photoScroll = new ScrollPane();
    private Trip trip;
    private int total_pic = 0;
    private int photoSize = 100;
    private Pane tableView = new Pane();
    private static String lastVisitedDirectory=System.getProperty("user.home");

    private ArrayList<PhotoPane> photoList = new ArrayList<>();

    public AllPhotoPane(Trip trip){
        this.trip = trip;
        photoScroll.setMinSize(400,400);
        photos.setMinSize(380,380);
        photos.setVgap(10);
        photos.setHgap(10);
        tableView.setLayoutX(90);
        tableView.setLayoutY(10);

        dragPhotoInit();
    }

    public void setUp(){
        for(Photo p: trip.getPhotoManager().getPhotos()){
            StackPane stackPane = new StackPane();
            total_pic ++;
            PhotoPane photoPane = new PhotoPane(p ,photoSize,total_pic);
            stackPane.getChildren().addAll(photoPane.getPane());
            photoList.add(photoPane.clonePane(600));

            Button zoom = createZoom(photoPane);
            stackPane.getChildren().addAll(zoom);
            photos.getChildren().addAll(stackPane);
        }

        Button addPic = new Button("add picture");
        addPic.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) { // get image from file
                addPic();
            }
        });
        addPic.setLayoutX(300);
        addPic.setLayoutY(400);
        photoScroll.setContent(photos);
        photoScroll.setMaxHeight(400);

        tableView.getChildren().addAll(addPic,photoScroll);
    }
    /**
     * add pic by open up choosing window
     */
    private void addPic(){
        Stage stage = (Stage)tableView.getScene().getWindow();
        //save the previous dictionary
        FileChooser.ExtensionFilter imageFilter
                = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.JPG");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(imageFilter);
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File(lastVisitedDirectory));
        File file = fileChooser.showOpenDialog(stage);
        lastVisitedDirectory=(file!=null )?file.getParent():System.getProperty("user.home");
        if(file!=null) {
            addPicToPane(file);
        }
    }

    /**
     * calls the view controller
     * @param index
     */
    public void viewPic(int index){
        try {
            Stage window = new Stage();
            window.setTitle("View Photo");
            FXMLLoader loader  = new FXMLLoader(getClass().getResource("../Controller/ViewPhoto.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 600);
            scene.getStylesheets().add("stylesheet.css");
            window.setScene(scene);
            ViewPhotoController controller = loader.getController();
            controller.setup(photoList, index);
            //controller.setStage();
            window.showAndWait();
        }catch (IOException i){
        }
    }
    /**
     * drag photo into photoScroll
     */
    public void dragPhotoInit(){
        List<String> validExtensions = Arrays.asList("jpg", "png","JPG");
        photos.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });

        // Dropping over surface
        photos.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    String filePath = null;
                    for (File file:db.getFiles()) {
                        String fileExtension = file.getName().substring( file.getName().lastIndexOf(".") + 1);
                        System.out.println(fileExtension);
                        if(fileExtension.equals("jpg")||fileExtension.equals("png")||fileExtension.equals("JPG")) {
                            System.out.println("ADDED");
                            addPicToPane(file);
                        }
                        // filePath = file.getAbsolutePath();
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
        //photos.getChildren().addAll(dropped);
    }

    /**
     * add photo to pane
     * @param file
     */
    void addPicToPane(File file){
        try{
            BufferedImage imageBuffered = ImageIO.read(new FileInputStream(file));
            String filePath = trip.getPath()+"\\" + trip.getName() + "_"+Integer.toString(trip.getPhotoManager().photoNum()) + ".png";
            Photo photo = new Photo(filePath);
            trip.addPhoto(photo);

            File saveDFile = new File(filePath);
            ImageIO.write(imageBuffered, "png", saveDFile);

            StackPane stackPane = new StackPane();
            total_pic++;
            PhotoPane photoPane = new PhotoPane(photo,photoSize,total_pic);
            photoList.add(photoPane.clonePane(600));

            stackPane.getChildren().addAll(photoPane.getPane());

            Button zoom = createZoom(photoPane);
            stackPane.getChildren().addAll(zoom);
            photos.getChildren().addAll(stackPane);
        }
        catch (IOException ex){
            System.out.println("still no");
        }
    }

    /**
     * create a zoom button that goes to View when click
     * @param photoPane
     * @return a zoom button
     */
    public Button createZoom(PhotoPane photoPane){
        Button zoom = new Button();
        zoom.setMinSize(photoSize,photoSize);
        zoom.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)  {
                viewPic(photoPane.getPic_num()-1);
            }
        });
        zoom.setId("imageButton");
        zoom.setUserData(photoPane);
        return zoom;
    }

    public Pane getPane() {
        return tableView;
    }

}
