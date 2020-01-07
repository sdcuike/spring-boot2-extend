package com.sdcuike.extend.gracefulshutdown.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * Perform a tomcat shutdown.
 */
@Slf4j
public class TomcatShutdown implements Shutdown, TomcatConnectorCustomizer {

    /**
     * Implementation of a Coyote connector.
     */
    private volatile Connector connector;


    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    public void pause() {

    }

    /**
     * Perform a shutdown
     *
     * @param delay is delay to force is the delay before perform a force shutdown
     * @throws InterruptedException if we have an exception
     */
    public void shutdown(Integer delay) throws InterruptedException {
        // Used to properly handle the work queue.
        connector.pause();
        final Executor executor = connector.getProtocolHandler().getExecutor();
        final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;

        Instant start = Instant.now();
        /*
         * Initiates an orderly shutdown in which previously submitted
         * tasks are executed, but no new tasks will be accepted.
         * Invocation has no additional effect if already shut down.
         */
        threadPoolExecutor.shutdown();

        // We wait after the end of the current requests
        if (!threadPoolExecutor.awaitTermination(delay, TimeUnit.SECONDS)) {
            log.warn("Tomcat thread pool did not shut down gracefully within " + delay + " second(s). Proceeding with force shutdown");
        } else {
            log.debug("Tomcat thread pool is empty, we stop now");
        }
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);

        log.info("Tomcat thread pool  shut down cost {} s", duration.getSeconds());
    }

    /**
     * Set connector.
     *
     * @param connector is the catalina connector.
     */
    public void customize(final Connector connector) {
        this.connector = connector;
    }
}
