package com.rishabh.lldcabbooking.controllers;

import com.rishabh.lldcabbooking.database.CabsManager;
import com.rishabh.lldcabbooking.database.RidersManager;
import com.rishabh.lldcabbooking.database.TripsManager;
import com.rishabh.lldcabbooking.model.Cab;
import com.rishabh.lldcabbooking.model.Location;
import com.sun.istack.internal.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/rest")
public class CabsController {

    private CabsManager cabsManager;
    private TripsManager tripsManager;

    public CabsController(CabsManager cabsManager,TripsManager tripsManager){
        this.cabsManager = cabsManager;
        this.tripsManager = tripsManager;
    }

    @PostMapping("/register/cab")
   public ResponseEntity<Cab> registerCab(@RequestBody Cab cab){

      Cab registeredCab = cabsManager.createCab(cab);
      return new ResponseEntity(registeredCab,HttpStatus.CREATED);
   }

   @PostMapping("/update/cab/location/cabId/{cabId}")
   public ResponseEntity<Cab> updateCabLocation(@PathVariable("cabId") final String cabId,@RequestBody Location location){
         Cab cab = cabsManager.updateCabLocation(cabId,location);
        return ResponseEntity.ok(cab);
   }

   @PostMapping("/update/cab/availability")
   public ResponseEntity<Cab> updateCabAvailability(@RequestBody @NotNull Cab updatedCab){
       Cab cab = cabsManager.updateCabAvailability(updatedCab.getId(),updatedCab.getIsAvailable());
       return ResponseEntity.ok(cab);
   }

   @PostMapping("/update/cab/end/trip")
   public ResponseEntity endTrip(final String cabId){
        tripsManager.endTrip(cabId);
       return ResponseEntity.ok("");
   }

}
