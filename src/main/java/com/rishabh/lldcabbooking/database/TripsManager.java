package com.rishabh.lldcabbooking.database;

import com.rishabh.lldcabbooking.exception.RiderNotFoundException;
import com.rishabh.lldcabbooking.exception.TripNotFoundException;
import com.rishabh.lldcabbooking.model.*;
import com.rishabh.lldcabbooking.strategies.CabMatchingStrategy;
import com.rishabh.lldcabbooking.strategies.PricingStrategy;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Component
public class TripsManager {

    public static final Double MAX_ALLOWED_TRIP_MATCHING_DISTANCE = 10.0;
    private Map<String, List<Trip>> trips = new HashMap<>();
    private PricingStrategy pricingStrategy;
    private CabMatchingStrategy cabMatchingStrategy;
    private CabsManager cabsManager;

    public TripsManager(PricingStrategy pricingStrategy,CabMatchingStrategy cabMatchingStrategy,CabsManager cabsManager){
        this.cabMatchingStrategy = cabMatchingStrategy;
        this.pricingStrategy = pricingStrategy;
        this.cabsManager = cabsManager;
    }

    public void endTrip(String cabId) {

        Cab currentCab = cabsManager.getCab(cabId);
        if(currentCab.getCurrentTrip() == null)throw new TripNotFoundException();
        currentCab.getCurrentTrip().endTrip();
        currentCab.setCurrentTrip(null);
    }

    public void createTrip(Rider rider, Location fromLoc, Location toLoc) throws InterruptedException, ExecutionException, TimeoutException {

        //show cabs upto distance of max_allowed_distance
        Future<List<Cab>> availableCabsFuture = CompletableFuture.supplyAsync(()-> cabsManager.getAllCabs(), Executors.newFixedThreadPool(10));
        List<Cab> availableCabs = availableCabsFuture.get(10,TimeUnit.MILLISECONDS);
        List<Cab> cabsWithingReachableDistance =availableCabs.parallelStream().filter(cab-> cab.getCurrentLocation().calculateDistance(fromLoc) <= MAX_ALLOWED_TRIP_MATCHING_DISTANCE)
                .filter(cab-> cab.getCurrentTrip() == null).collect(Collectors.toList());


        //Default cabs matching strategy used
        Cab cabAllocated = cabMatchingStrategy.matchCabToRider(rider,cabsWithingReachableDistance,fromLoc,toLoc);

        //Pricing strategy
        Double charge = pricingStrategy.findPrice(fromLoc,toLoc);

        Trip trip = Trip.builder().rider(rider)
                .cab(cabAllocated)
                .fromLoc(fromLoc)
                .price(charge)
                .toLoc(toLoc)
                .tripstatus(TripStatus.INPROGRESS)
                .price(10D).build();
        if(trips.get(rider.getId()) == null){
            trips.put(rider.getId(), Arrays.asList(trip));
        }else{
            trips.get(rider.getId()).add(trip);
        }
      cabAllocated.setCurrentTrip(trip);
    }

    public List<Trip> tripHistory(Rider rider) {

        if(!trips.containsKey(rider.getId())) throw new RiderNotFoundException();
        return trips.get(rider.getId());
    }
}
