package com.rishabh.lldcabbooking.strategies;

import com.rishabh.lldcabbooking.model.Cab;
import com.rishabh.lldcabbooking.model.Location;
import com.rishabh.lldcabbooking.model.Rider;

import java.util.List;

public interface CabMatchingStrategy {

    Cab matchCabToRider(Rider rider, List<Cab> candidateCabs, Location fromPoint, Location toPoint);
}
