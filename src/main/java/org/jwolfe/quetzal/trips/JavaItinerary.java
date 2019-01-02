package org.jwolfe.quetzal.trips;

import org.jwolfe.quetzal.library.trips.Itinerary;
import org.jwolfe.quetzal.library.trips.Trip;

import java.util.List;

public class JavaItinerary extends Itinerary {

    @Override
    public List<Trip> loadTrips() {
        super.loadTrips();
        trips.add(new Streams());
        return trips;
    }
}
