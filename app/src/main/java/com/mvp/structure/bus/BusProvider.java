package com.mvp.structure.bus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by thai.cao on 7/31/2019.
 */
public class BusProvider {
    public static final Bus bus = new Bus(ThreadEnforcer.MAIN);

    public static void post(Object event) {
        bus.post(event);
    }

    public static void post(int eventNum, Object event) {
        bus.post(event);
    }
}
