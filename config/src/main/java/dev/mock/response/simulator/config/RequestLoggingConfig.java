package dev.mock.response.simulator.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingConfig
{
    @Bean
    public CommonsRequestLoggingFilter logFilter( )
    {
        final CustomRequestLoggingFilter filter = new CustomRequestLoggingFilter( );
        filter.setIncludeQueryString( true );
        filter.setIncludePayload( true );
        filter.setMaxPayloadLength( 10000 );
        filter.setIncludeHeaders( true );
        filter.setBeforeMessagePrefix( "INCOMING REQUEST: " );

        return filter;
    }

    private static class CustomRequestLoggingFilter
            extends CommonsRequestLoggingFilter
    {

        @Override
        protected void afterRequest( final HttpServletRequest request,
                                     final String message )
        {
            // Override and do nothing to skip after-request logging
        }
    }
}
