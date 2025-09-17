package dev.mock.response.simulator.service;

import dev.mock.response.simulator.config.EndpointConfig;
import dev.mock.response.simulator.config.EndpointMappingConfig;
import dev.mock.response.simulator.service.exception.InternalProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static org.springframework.http.HttpMethod.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultHttpHttpRequestHandler
        implements HttpRequestHandler
{
    private final EndpointMatcher endpointMatcher;
    private final EndpointMappingConfig endpointMappingConfig;

    @Override
    public ResponseEntity<String> handleRequest( final HttpServletRequest request )
    {
        final String requestUri = request.getRequestURI( );
        final Map<String, EndpointConfig> endpointConfigMap = getEndpointConfigMapBasedOnRequestType( request );

        final String endpoint = endpointMatcher.findMatchingEndpointPattern( requestUri,
                                                                             endpointConfigMap );

        final EndpointConfig endpointConfig = endpointConfigMap.entrySet( )
                                                               .stream( )
                                                               .filter( entry -> entry.getValue( )
                                                                                      .getEndpoint( )
                                                                                      .equals( endpoint ) )
                                                               .findFirst( )
                                                               .orElseThrow( ( ) -> new InternalProcessingException( String.format( "No mapping found for request method: %s URI: %s",
                                                                                                                                    request.getMethod( ),
                                                                                                                                    requestUri ) ) )
                                                               .getValue( );

        return new ResponseEntity<>( endpointConfig.getResponseBody( ),
                                     buildHeaders( endpointConfig.getResponseHeaders( ) ),
                                     HttpStatus.valueOf( getResponseCode( endpointConfig.getResponseStatus( ) ) ) );
    }

    private Map<String, EndpointConfig> getEndpointConfigMapBasedOnRequestType( final HttpServletRequest request )
    {
        final HttpMethod method = HttpMethod.valueOf( request.getMethod( ) );

        if ( method.matches( GET.name( ) ) )
        {
            return endpointMappingConfig.getGetMapping( );
        }
        else if ( method.matches( POST.name( ) ) )
        {
            return endpointMappingConfig.getPostMapping( );
        }
        else if ( method.matches( PATCH.name( ) ) )
        {
            return endpointMappingConfig.getPatchMapping( );
        }
        else if ( method.matches( PUT.name( ) ) )
        {
            return endpointMappingConfig.getPutMapping( );
        }
        else if ( method.matches( DELETE.name( ) ) )
        {
            return endpointMappingConfig.getDeleteMapping( );
        }
        else
        {
            throw new UnsupportedOperationException( "Unsupported request method type: " + request.getMethod( ) );
        }
    }

    private MultiValueMap<String, String> buildHeaders( final Map<String, String> headersMap )
    {
        final HttpHeaders headers = new HttpHeaders( );

        if ( headersMap != null && !headersMap.isEmpty( ) )
        {
            for ( final Map.Entry<String, String> entry : headersMap.entrySet( ) )
            {
                headers.set( entry.getKey( ),
                             entry.getValue( ) );
            }
        }

        return CollectionUtils.toMultiValueMap( headers );
    }

    private int getResponseCode( final Integer responseStatus )
    {
        return responseStatus != null ? responseStatus : 200;
    }
}
