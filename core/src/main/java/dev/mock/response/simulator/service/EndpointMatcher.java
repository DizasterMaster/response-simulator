package dev.mock.response.simulator.service;

import dev.mock.response.simulator.config.EndpointConfig;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EndpointMatcher
{

    /**
     * Finds a matching endpoint pattern for the given request URI based on the provided endpoint configuration map.
     * <p>
     * This method iterates through the endpoint configurations and attempts to match the {@code requestUri}
     * against any configured endpoint patterns that contain a wildcard (`*`). Each asterisk in an endpoint
     * is treated as a placeholder for a single word segment (alphanumeric characters), which is translated into
     * a regular expression matching {@code \w+}. Dashes in the {@code requestUri} are removed before matching.
     * </p>
     * If a match is found, the corresponding endpoint pattern (with the wildcard) is returned.
     * If no match is found, the original {@code requestUri} is returned as-is.
     * </p>
     *
    * @param requestUri             The URI from the incoming request
    * @param endpointConfigMap      A map of endpoint identifiers to their {@link EndpointConfig}, where some endpoints may contain wildcards
    *
     * @return                      The matching endpoint pattern if found; otherwise, the original {@code requestUri}
    */
    public String findMatchingEndpoint( final String requestUri,
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
        }

        return requestUri;
    }
}
