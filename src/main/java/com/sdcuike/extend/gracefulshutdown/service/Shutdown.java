package com.sdcuike.extend.gracefulshutdown.service;


/**
 * Shutdown service.
 */
public interface Shutdown {


    /**
     * 升序依次执行
     *
     * @return
     */
    default int getOrder() {
        return 0;
    }

    /**
     * Perform a pause on the server.
     *
     * @throw InterruptedException if we have an interruption
     */
    void pause() throws InterruptedException;

    /**
     * Perform shutdown.
     *
     * @param delay is delay to force
     * @throw InterruptedException if we have an interruption
     */
    void shutdown(Integer delay) throws InterruptedException;
}
