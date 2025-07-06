package dev.mock.response.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class ResponseSimulatorApp
{
    public static void main( final String[] args )
    {
        SpringApplication.run( ResponseSimulatorApp.class,
                               args );
    }
}
