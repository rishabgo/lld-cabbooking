package com.rishabh.lldcabbooking.strategies;

import com.rishabh.lldcabbooking.database.CabsManager;
import com.rishabh.lldcabbooking.model.Cab;
import com.rishabh.lldcabbooking.model.Location;
import com.rishabh.lldcabbooking.model.Rider;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@NoArgsConstructor
@Component
public class DefaultCabMatchingStrategy implements CabMatchingStrategy{

    private CabsManager cabsManager;

    public DefaultCabMatchingStrategy(CabsManager cabsManager) {
        this.cabsManager = cabsManager;
    }

    @Override
    public Cab matchCabToRider(@NonNull final Rider rider,@NonNull final List<Cab> candidateCabs,@NonNull final Location fromPoint,@NonNull final Location toPoint) {

        return candidateCabs.get(new Random().nextInt(candidateCabs.size()));
    }
}
