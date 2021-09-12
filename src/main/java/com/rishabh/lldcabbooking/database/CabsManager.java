package com.rishabh.lldcabbooking.database;

import com.rishabh.lldcabbooking.exception.CabAlreadyExistsException;
import com.rishabh.lldcabbooking.exception.CabNotFoundException;
import com.rishabh.lldcabbooking.model.Cab;
import com.rishabh.lldcabbooking.model.Location;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CabsManager {
    private static Map<String,Cab> cabs = new HashMap();

    public Cab createCab(@NonNull final Cab cab) {
      if(cabs.containsKey(cab.getId())){
          throw new CabAlreadyExistsException("Cab with ID:" + cab.getId() +" already exist");
      }
     cabs.put(cab.getId(),cab);
      return cab;
    }

    public Cab getCab(@NonNull final String cabId){
        if(!cabs.containsKey(cabId)){
            throw new CabNotFoundException("No cab found");
        }
        return cabs.get(cabId);
    }

    public List<Cab> getAllCabs(){
        List<Cab> availableCabs = new ArrayList<Cab>();

        for(Cab cab: cabs.values()){
            if(cab.getIsAvailable()){
                availableCabs.add(cab);
            }
        }
        return availableCabs;
    }

    public Cab updateCabLocation(@NonNull final String cabId,@NonNull Location newLocation) {
        if(!cabs.containsKey(cabId)){
            throw new CabNotFoundException("No cab found");
        }
        Cab cab = cabs.get(cabId);
        cab.setCurrentLocation(newLocation);
        return cab;
    }

    public Cab updateCabAvailability(@NonNull final String cabId,@NonNull final boolean newAvailability) {
        if(!cabs.containsKey(cabId)){
            throw new CabNotFoundException("No cab found");
        }
        Cab cab = cabs.get(cabId);
        cab.setIsAvailable(newAvailability);
        return cab;
    }
}
