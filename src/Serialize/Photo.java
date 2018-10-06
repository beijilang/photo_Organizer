package Serialize;

public class Photo implements java.io.Serializable {
    private String path;

    public Photo(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
