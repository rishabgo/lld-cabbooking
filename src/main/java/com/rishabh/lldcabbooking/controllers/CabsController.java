package com.rishabh.lldcabbooking.controllers;

import com.rishabh.lldcabbooking.database.CabsManager;
import com.rishabh.lldcabbooking.database.RidersManager;
import com.rishabh.lldcabbooking.database.TripsManager;
import com.rishabh.lldcabbooking.model.Cab;
import com.rishabh.lldcabbooking.model.Location;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/rest")
public class CabsController {

    private CabsManager cabsManager;
    private TripsManager tripsManager;

    public CabsController(CabsManager cabsManager,TripsManager tripsManager){
        this.cabsManager = cabsManager;
        this.tripsManager = tripsManager;
    }

    @PostMapping("/register/cab")
   public ResponseEntity<Cab> registerCab(@RequestBody Cab cab){

      cabsManager.createCab(cab);
      return new ResponseEntity(cab,HttpStatus.CREATED);
   }

   @PostMapping("/update/cab/location")
   public ResponseEntity updateCabLocation(final String cabId,final Double newX,final Double newY){
        cabsManager.updateCabLocation(cabId,new Location(newX,newY));
        return ResponseEntity.ok("");
   }

   @PostMapping("/update/cab/availability")
   public ResponseEntity updateCabAvailability(final String cabId,final boolean newAvailability){
       cabsManager.updateCabAvailability(cabId,newAvailability);
       return ResponseEntity.ok("");
   }

   @PostMapping("/update/cab/end/trip")
   public ResponseEntity endTrip(final String cabId){
        tripsManager.endTrip(cabId);
       return ResponseEntity.ok("");
   }

}
