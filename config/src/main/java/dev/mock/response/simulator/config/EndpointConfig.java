package dev.mock.response.simulator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Configuration
public class EndpointConfig
{
    private String endpoint;
    private Integer responseStatus;
    private Map<String, String> responseHeaders = new HashMap<>( );
    private String responseBody;
}
