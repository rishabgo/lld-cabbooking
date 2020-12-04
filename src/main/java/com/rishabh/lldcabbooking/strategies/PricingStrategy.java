package com.rishabh.lldcabbooking.strategies;

import com.rishabh.lldcabbooking.model.Location;

public interface PricingStrategy {
    Double findPrice(Location fromPoint, Location toPoint);
}
