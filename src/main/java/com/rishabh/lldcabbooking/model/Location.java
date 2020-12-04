package com.rishabh.lldcabbooking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Location {

    private Double x;
    private Double y;

    public Double calculateDistance(Location loc){
        return Math.sqrt(Math.pow(this.x-loc.x,2) + Math.pow(this.y-loc.y,2));
    }

}
