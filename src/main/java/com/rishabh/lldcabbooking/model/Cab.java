package com.rishabh.lldcabbooking.model;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Cab {

    private String id;
    private String driverName;
    @Setter private Boolean isAvailable;
    @Setter private Location currentLocation;
    @Setter private Trip currentTrip;
}
