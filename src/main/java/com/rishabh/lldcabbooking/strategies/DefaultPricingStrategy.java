package com.rishabh.lldcabbooking.strategies;

import com.rishabh.lldcabbooking.model.Location;
import org.springframework.stereotype.Component;

@Component
public class DefaultPricingStrategy implements PricingStrategy {

    private static final Double RATE_BELOW_10KM = 8.0;
    private static final Double RATE_ABOVE_10KM = 12.0;

    @Override
    public Double findPrice(Location fromPoint, Location toPoint) {

        Double distance = fromPoint.calculateDistance(toPoint);
        if(distance > 10){
          return RATE_BELOW_10KM * 10  + (distance - 10) * RATE_ABOVE_10KM;
        }else{
          return RATE_BELOW_10KM * distance;
        }
    }
}
