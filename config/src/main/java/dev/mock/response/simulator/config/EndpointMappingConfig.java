package dev.mock.response.simulator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties( prefix = "endpoints" )
public class EndpointMappingConfig
{
    private Map<String, EndpointConfig> getMapping = new HashMap<>( );
    private Map<String, EndpointConfig> postMapping = new HashMap<>( );
    private Map<String, EndpointConfig> patchMapping = new HashMap<>( );
    private Map<String, EndpointConfig> putMapping = new HashMap<>( );
    private Map<String, EndpointConfig> deleteMapping = new HashMap<>( );
}
