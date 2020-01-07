package com.sdcuike.extend.gracefulshutdown.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sdcuike
 * @DATE 2020/1/7
 */
public final class ShutdownManager implements Shutdown {


    List<Shutdown> shutdowns;


    public ShutdownManager(List<Shutdown> shutdowns) {
        this.shutdowns = shutdowns.stream().sorted(Comparator.comparingInt(Shutdown::getOrder)).collect(Collectors.toList());
    }


    @Override
    public void pause() throws InterruptedException {
        for (Shutdown shutdown : shutdowns) {
            shutdown.pause();
        }
    }

    @Override
    public void shutdown(Integer delay) throws InterruptedException {
        for (Shutdown shutdown : shutdowns) {
            shutdown.shutdown(delay);
        }
    }
}
