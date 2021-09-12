package com.rishabh.lldcabbooking;

import com.rishabh.lldcabbooking.controllers.CabsController;
import com.rishabh.lldcabbooking.controllers.RidersController;
import com.rishabh.lldcabbooking.database.CabsManager;
import com.rishabh.lldcabbooking.database.RidersManager;
import com.rishabh.lldcabbooking.database.TripsManager;
import com.rishabh.lldcabbooking.strategies.CabMatchingStrategy;
import com.rishabh.lldcabbooking.strategies.DefaultCabMatchingStrategy;
import com.rishabh.lldcabbooking.strategies.DefaultPricingStrategy;
import com.rishabh.lldcabbooking.strategies.PricingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RunnerTest {

    CabsController cabsController;
    RidersController ridersController;

    @BeforeEach
    public void setUp(){
        CabsManager cabsManager = new CabsManager();
        RidersManager ridersManager = new RidersManager();

        CabMatchingStrategy cabMatchingStrategy = new DefaultCabMatchingStrategy();
        PricingStrategy pricingStrategy = new DefaultPricingStrategy();

        TripsManager tripsManager = new TripsManager(pricingStrategy,cabMatchingStrategy,cabsManager );

        cabsController = new CabsController(cabsManager, tripsManager);
        ridersController = new RidersController(ridersManager, tripsManager);
    }



}
