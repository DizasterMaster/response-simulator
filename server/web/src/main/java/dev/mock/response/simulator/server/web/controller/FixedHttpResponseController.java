package dev.mock.response.simulator.server.web.controller;

import dev.mock.response.simulator.config.EndpointConfig;
import dev.mock.response.simulator.config.EndpointMappingConfig;
import dev.mock.response.simulator.service.DefaultHttpHttpRequestHandler;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Collection;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FixedHttpResponseController
{
    private final EndpointMappingConfig endpointMappingConfig;
    private final RequestMappingHandlerMapping handlerMapping;
    private final DefaultHttpHttpRequestHandler defaultHttpHttpRequestHandler;

    @PostConstruct
    private void init( )
    {
        initControllers( );
    }

    /**
     * Initializes all endpoints.
     */
    private void initControllers( )
    {
        buildRequestMethodToEndpointConfigsMap( ).forEach( ( method,
                                                             endpointConfigs ) ->
                                                                endpointConfigs.forEach( config ->
                                                                                                 initControllerHandler( config, method ) ) );
    }

    private Map<RequestMethod, Collection<EndpointConfig>> buildRequestMethodToEndpointConfigsMap( )
    {
        return Map.of(
                RequestMethod.GET,
                endpointMappingConfig.getGetMapping( )
                                     .values( ),
                RequestMethod.POST,
                endpointMappingConfig.getPostMapping( )
                                     .values( ),
                RequestMethod.PATCH,
                endpointMappingConfig.getPatchMapping( )
                                     .values( ),
                RequestMethod.PUT,
                endpointMappingConfig.getPutMapping( )
                                     .values( ),
                RequestMethod.DELETE,
                endpointMappingConfig.getDeleteMapping( )
                                     .values( )
        );
    }

    /**
     * Initializes the endpoint and assigns the underlying handler based on the request method type.
     *
     * @param endpointConfig The {@link EndpointConfig}
     * @param requestMethod  The {@link RequestMethod}
     */
    private void initControllerHandler( final EndpointConfig endpointConfig,
                                        final RequestMethod requestMethod )
    {
        try
        {
            log.info( "Initializing: {} Endpoint: {}",
                      requestMethod.name( ),
                      endpointConfig.getEndpoint( ) );

            handlerMapping.registerMapping( RequestMappingInfo.paths( endpointConfig.getEndpoint( ) )
                                                              .methods( requestMethod )
                                                              .build( ),
                                            defaultHttpHttpRequestHandler,
                                            DefaultHttpHttpRequestHandler.class.getDeclaredMethod( "handleRequest",
                                                                                                   HttpServletRequest.class ) );
        } catch ( final NoSuchMethodException e )
        {
            log.error( "Couldn't find a valid handler for request method: {} and endpoint: {}",
                       requestMethod.name( ),
                       endpointConfig.getEndpoint( ) );
        }
    }
}
