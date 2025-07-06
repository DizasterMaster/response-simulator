package dev.mock.response.simulator.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface HttpRequestHandler
{
    /**
     * Processes the incoming request and returns an appropriate response.
     *
     * @param request   The {@link HttpServletRequest}
     *
     * @return          The {@link ResponseEntity}
     */
    ResponseEntity handleRequest( final HttpServletRequest request );
}
