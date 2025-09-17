package dev.mock.response.simulator.service;

import dev.mock.response.simulator.config.EndpointConfig;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EndpointMatcher
{

    /**
     * Finds a matching endpoint pattern from the configuration map based on the provided request URI.
     * <p>
     * This method iterates through the endpoint configurations in the {@code endpointConfigMap} and attempts to match the {@code requestUri}
     * against any endpoint pattern.
     * </p>
     * For patterns with a wildcard '*', each asterisk in an endpoint
     * is treated as a placeholder for a single word segment (alphanumeric characters), which is translated into
     * a regular expression matching {@code \w+}. Dashes in the {@code requestUri} are removed before matching.
     * </p>
     * If a match is found, the corresponding endpoint pattern is returned.
     * </p>
     *
    * @param requestUri             The URI from the incoming request
    * @param endpointConfigMap      A map of endpoint identifiers to their {@link EndpointConfig}, where some endpoints may contain wildcards
    *
     * @return                      The matching endpoint pattern if found; Otherwise {@code null}
    */
    @Nullable
    public String findMatchingEndpointPattern( final String requestUri,
                                               final Map<String, EndpointConfig> endpointConfigMap )
    {
        for ( final EndpointConfig config : endpointConfigMap.values( ) )
        {
            final String endpoint = config.getEndpoint( );

            if ( endpoint.contains( "*" ) )
            {
                final String patternString = "^".concat( endpoint )
                                                .replace( "*",
                                                          "\\w+" )
                                                .concat( "$" );

                final Pattern pattern = Pattern.compile( patternString,
                                                         Pattern.MULTILINE );

                final Matcher matcher = pattern.matcher( requestUri.replace( "-",
                                                                             "" ) );

                if ( matcher.find( ) )
                {
                    return endpoint;
                }
            }
            else if ( endpoint.equals( requestUri ) )
            {
                return endpoint;
            }
        }

        return null;
    }
}
