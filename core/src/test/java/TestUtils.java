import dev.mock.response.simulator.config.EndpointConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUtils
{
    static final String ENDPOINT_STATIC = "/test/static";
    static final String ENDPOINT_DYNAMIC = "/test/dynamic/123";

    static final String NOT_CONFIGURED_ENDPOINT = "/not/configured";

    static final String CONFIGURED_ENDPOINT_STATIC = "/test/static";
    static final String CONFIGURED_ENDPOINT_DYNAMIC = "/test/dynamic/*";

    private static final List<String> ENDPOINTS = List.of( CONFIGURED_ENDPOINT_STATIC,
                                                           CONFIGURED_ENDPOINT_DYNAMIC );

    static final Map<String, EndpointConfig> ENDPOINT_CONFIGS = createEndpointConfigs( );


    private static Map<String, EndpointConfig> createEndpointConfigs( )
    {
        final Map<String, EndpointConfig> endpointConfigs = new HashMap<>( );

        for ( final String endpoint : ENDPOINTS )
        {
            final EndpointConfig endpointConfig = new EndpointConfig( );

            endpointConfig.setEndpoint( endpoint );
            endpointConfigs.put( endpoint,
                                 endpointConfig );
        }

        return endpointConfigs;
    }

}
