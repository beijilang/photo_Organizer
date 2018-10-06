package Manager;

import Serialize.Trip;

import java.util.ArrayList;
import java.util.Iterator;

public class TripManager implements Iterable<Trip>, java.io.Serializable{
    private ArrayList<Trip> trips = new ArrayList<>();

    private static TripManager tripManager = new TripManager();


    @Override
    public Iterator<Trip> iterator() {
            return new Iterator<Trip>() {
                private final Iterator<Trip> tripI = trips.iterator();

                @Override
                public boolean hasNext() {
                    return tripI.hasNext();
                }

                @Override
                public Trip next() {
                    return tripI.next();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException("no changes allowed");
                }
            };

        }


    public  TripManager(){

    }

    public static TripManager getTripManager() {
        return tripManager;
    }

    public void addTrip(Trip trip){
        trips.add(trip);
    }

    public void removeTrip(Trip trip){
        trip.delete();
        trips.remove(trip);}

    public int size(){
        return trips.size();
    }

    public void loadSavedInfo(TripManager t){
        trips = t.getTrips();
    }

    public ArrayList<Trip> getTrips(){
        return trips;
    }

}
