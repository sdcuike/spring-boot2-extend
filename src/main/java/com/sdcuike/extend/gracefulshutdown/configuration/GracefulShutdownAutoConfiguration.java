package com.sdcuike.extend.gracefulshutdown.configuration;

import com.sdcuike.extend.gracefulshutdown.endpoint.GracefulShutdownEndpoint;
import com.sdcuike.extend.gracefulshutdown.health.GracefulHealth;
import com.sdcuike.extend.gracefulshutdown.properties.GracefulShutdownProperties;
import com.sdcuike.extend.gracefulshutdown.service.SchedulingShutDown;
import com.sdcuike.extend.gracefulshutdown.service.Shutdown;
import com.sdcuike.extend.gracefulshutdown.service.ShutdownManager;
import com.sdcuike.extend.gracefulshutdown.service.TomcatShutdown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * This configuration class will be picked up by Spring Boot's auto configuration capabilities as soon as it's
 * on the classpath.
 */
@Configuration
@ConditionalOnProperty(prefix = "endpoints.shutdown.graceful", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(GracefulShutdownProperties.class)
public class GracefulShutdownAutoConfiguration {

    /**
     * Properties.
     */
    @Autowired
    private GracefulShutdownProperties gracefulShutdownProperties;

    /**
     * Configuration for Tomcat.
     */
    @Configuration
    @ConditionalOnClass(name = {"org.apache.catalina.startup.Tomcat", "javax.servlet.Servlet"})
    public static class EmbeddedTomcat {
        @Bean
        public TomcatShutdown tomcatShutdown() {
            return new TomcatShutdown();
        }
    }


    /**
     * Graceful shutdown util.
     *
     * @return the unique tool to perform the graceful shutdown.
     */
    @Bean
    @ConditionalOnMissingBean
    protected GracefulShutdownEndpoint gracefulShutdownEndpoint() {
        return new GracefulShutdownEndpoint(gracefulShutdownProperties.getTimeout(), gracefulShutdownProperties.getWait());
    }

    /**
     * Graceful health.
     *
     * @return a graceful health.
     */
    @Bean
    public GracefulHealth gracefulHealth() {
        return new GracefulHealth();
    }


    @Bean
    public SchedulingShutDown schedulingShutDown() {
        return new SchedulingShutDown();
    }

    @Bean
    public ShutdownManager shutdownManager(List<Shutdown> shutdowns) {
        return new ShutdownManager(shutdowns);
    }
}
