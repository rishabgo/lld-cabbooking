package com.rishabh.lldcabbooking.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Trip {

    private Rider rider;
    private Cab cab;
    private Location fromLoc;
    private Location toLoc;
    private Double price;
    private TripStatus tripstatus;

    public void endTrip(){
        this.tripstatus = TripStatus.FINISHED;
    }
}
