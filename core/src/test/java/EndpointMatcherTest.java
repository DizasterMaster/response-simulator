import dev.mock.response.simulator.service.EndpointMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EndpointMatcherTest
{
    private final EndpointMatcher endpointMatcher = new EndpointMatcher( );

    @Test
    public void test_whenIncomingStaticEndpointIsConfigured_enpointIsReturned( )
    {
        final String result = endpointMatcher.findMatchingEndpointPattern( TestUtils.ENDPOINT_STATIC,
                                                                           TestUtils.ENDPOINT_CONFIGS );

        Assertions.assertEquals( TestUtils.CONFIGURED_ENDPOINT_STATIC,
                                 result );
    }

    @Test
    public void test_whenIncomingDynamicEndpointIsConfigured_endpointIsReturned( )
    {
        final String result = endpointMatcher.findMatchingEndpointPattern( TestUtils.ENDPOINT_DYNAMIC,
                                                                           TestUtils.ENDPOINT_CONFIGS );

        Assertions.assertEquals( TestUtils.CONFIGURED_ENDPOINT_DYNAMIC,
                                 result );
    }

    @Test
    public void test_whenIncomingEndpointIsNotConfigured_nullIsReturned( )
    {
        final String result = endpointMatcher.findMatchingEndpointPattern( TestUtils.NOT_CONFIGURED_ENDPOINT,
                                                                           TestUtils.ENDPOINT_CONFIGS );

        Assertions.assertNull( result );
    }
}
