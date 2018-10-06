package Manager;

import Serialize.Photo;

import java.util.ArrayList;
import java.util.Iterator;

public class PhotoManager implements Iterable<Photo>, java.io.Serializable{
    private String path;
    private ArrayList<Photo> photos;
    @Override
    public Iterator<Photo> iterator() {
        return new Iterator<Photo>() {
            private final Iterator<Photo> photoI = photos.iterator();

            @Override
            public boolean hasNext() {
                return photoI.hasNext();
            }

            @Override
            public Photo next() {
                return photoI.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("no changes allowed");
            }
        };

    }
    public PhotoManager(String dir){
        path = dir;
        photos = new ArrayList<>();
    }

    public String getPath() {
        return path;
    }

    public void addPhoto(Photo p){
        photos.add(p);
    }

    public int photoNum(){
        return photos.size()+1;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }
}
