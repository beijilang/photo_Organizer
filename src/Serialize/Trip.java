package Serialize;

import Manager.PhotoManager;
import Serialize.Photo;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;

public class Trip  implements java.io.Serializable{
    public static int numOfTrip = 0;
    private int tripNum;
    private PhotoManager photoManager;
    private String name, date;
    public Trip(){
        numOfTrip ++;
        tripNum = numOfTrip;
    }
    public String getPath(){
        return photoManager.getPath();
    }
    public PhotoManager getPhotoManager(){
        return photoManager;
    }
    public void addPhoto(Photo p){
        photoManager.addPhoto(p);
    }

    public void delete(){
        File f = new File(photoManager.getPath());
        f.delete();
    }
    public int getTripNum() {
        return tripNum;
    }

    public void setDir(String dir){
        photoManager = new PhotoManager(dir);
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
