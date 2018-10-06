package Manager;

import Panes.SingleTripPane;

import java.util.ArrayList;
import java.util.Iterator;

public class TripPaneManager implements Iterable<SingleTripPane>{
    private ArrayList<SingleTripPane> trips = new ArrayList<>();

    private static TripPaneManager tripManager = new TripPaneManager();


    @Override
    public Iterator<SingleTripPane> iterator() {
        return new Iterator<SingleTripPane>() {
            private final Iterator<SingleTripPane> tripI = trips.iterator();

            @Override
            public boolean hasNext() {
                return tripI.hasNext();
            }

            @Override
            public SingleTripPane next() {
                return tripI.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("no changes allowed");
            }
        };

    }


    public  TripPaneManager(){

    }

    public static TripPaneManager getTripManager() {
        return tripManager;
    }

    public void addTrip(SingleTripPane trip){
        trips.add(trip);
    }

    public void removeTrip(SingleTripPane trip){trips.remove(trip);}

    public int size(){
        return trips.size();
    }

}
