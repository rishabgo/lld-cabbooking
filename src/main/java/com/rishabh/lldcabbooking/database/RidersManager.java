package com.rishabh.lldcabbooking.database;

import com.rishabh.lldcabbooking.exception.RiderAlreadyExistsException;
import com.rishabh.lldcabbooking.exception.RiderNotFoundException;
import com.rishabh.lldcabbooking.model.Rider;
import com.rishabh.lldcabbooking.model.Trip;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RidersManager {

    private Map<String , Rider> riders = new HashMap<>();

    public void createRider(@NonNull final Rider newRider) {
        if(!riders.containsKey(newRider.getId()))
         throw new RiderAlreadyExistsException();

       riders.put(newRider.getId(),newRider);
    }

    public Rider getRider(@NonNull final String riderId) {

          if(!riders.containsKey(riderId))throw new RiderNotFoundException();
          return riders.get(riderId);
    }
}
