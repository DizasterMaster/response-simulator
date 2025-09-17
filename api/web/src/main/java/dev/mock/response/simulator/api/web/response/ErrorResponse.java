package dev.mock.response.simulator.api.web.response;

import lombok.Builder;

@Builder
public record ErrorResponse( String errorMessage )
{ }
