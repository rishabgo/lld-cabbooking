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
    private Map<String,Cab> cabs = new HashMap();

    public void createCab(@NonNull final Cab cab) {
      if(cabs.containsKey(cab.getId())){
          throw new CabAlreadyExistsException();
      }
     cabs.put(cab.getId(),cab);
    }

    public Cab getCab(@NonNull final String cabId){
        if(!cabs.containsKey(cabId)){
            throw new CabNotFoundException();
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

    public void updateCabLocation(@NonNull final String cabId,@NonNull Location newLocation) {
        if(!cabs.containsKey(cabId)){
            throw new CabNotFoundException();
        }
        cabs.get(cabId).setCurrentLocation(newLocation);
    }

    public void updateCabAvailability(@NonNull final String cabId,@NonNull final boolean newAvailability) {
        if(!cabs.containsKey(cabId)){
            throw new CabNotFoundException();
        }
        cabs.get(cabId).setIsAvailable(newAvailability);
    }
}
