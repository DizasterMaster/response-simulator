package dev.mock.response.simulator.service.exception.handler;

import dev.mock.response.simulator.api.web.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CoreExceptionHandler
{
    @ExceptionHandler( Exception.class )
    public ResponseEntity<ErrorResponse> handleGenericException( final Exception ex )
    {
        log.error( ex.getMessage( ) );

        return ResponseEntity
                .status( HttpStatus.INTERNAL_SERVER_ERROR )
                .body( ErrorResponse.builder( )
                                    .errorMessage( ex.getMessage( ) )
                                    .build( ) );
    }
}
