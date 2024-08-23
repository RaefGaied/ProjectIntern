package org.jhipster.projectintern.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Reservation getReservationSample1() {
        return new Reservation().id(1L).nombrePersonnes(1);
    }

    public static Reservation getReservationSample2() {
        return new Reservation().id(2L).nombrePersonnes(2);
    }

    public static Reservation getReservationRandomSampleGenerator() {
        return new Reservation().id(longCount.incrementAndGet()).nombrePersonnes(intCount.incrementAndGet());
    }
}
