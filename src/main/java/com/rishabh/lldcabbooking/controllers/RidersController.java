package com.rishabh.lldcabbooking.controllers;

import com.rishabh.lldcabbooking.database.RidersManager;
import com.rishabh.lldcabbooking.database.TripsManager;
import com.rishabh.lldcabbooking.model.Location;
import com.rishabh.lldcabbooking.model.Rider;
import com.rishabh.lldcabbooking.model.Trip;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController("/api/v1/rest")
public class RidersController {

    private RidersManager ridersManager;
    private TripsManager tripsManager;

    public RidersController(RidersManager ridersManager, TripsManager tripsManager) {
        this.ridersManager = ridersManager;
        this.tripsManager = tripsManager;
    }

    @PostMapping("/register/rider")
    public ResponseEntity<Rider> registerRider(@RequestBody Rider rider){
         ridersManager.createRider(rider);
         return new ResponseEntity(rider,HttpStatus.CREATED);
    }

    @PostMapping("/book")
    public ResponseEntity book(final String riderId,final Double sourceX,final Double sourceY,final Double destX,final Double destY) throws InterruptedException, ExecutionException, TimeoutException {

       tripsManager.createTrip(ridersManager.getRider(riderId),new Location(sourceX,sourceY),new Location(destX,destY));
       return  ResponseEntity.ok().build();
    }

    @GetMapping("/fetch/rides/history")
    public ResponseEntity<List<Trip>> fetchHistory(final String riderId){

        List<Trip> trips = tripsManager.tripHistory(ridersManager.getRider(riderId));
        return new ResponseEntity(trips,HttpStatus.OK);
    }
}
