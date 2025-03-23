package com.coopbank.admin.administrative_service.config;

import com.coopbank.admin.administrative_service.dtos.StandardResponse;
import com.coopbank.admin.administrative_service.exception.AbstractApiException;
import com.coopbank.admin.administrative_service.exception.AbstractCustomException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {
	    
	    @ExceptionHandler(AbstractCustomException.class)
	    public ResponseEntity<?> handleCustomException(AbstractCustomException ex, WebRequest request) {
	    	log.error("AbstractCustomException: " + 
	    			(ex instanceof AbstractApiException ? ((AbstractApiException) ex).getUri() : ""),  
	    			ex);
	    	
	    	var status = ex.getStatusCode();
	    	
			return new ResponseEntity<StandardResponse<?>>(
					StandardResponse.builder()
						.message(ex.getMessage())
						.status(status)
						.path(ex instanceof AbstractApiException ? null :
								request instanceof ServletWebRequest ? 
										((ServletWebRequest) request).getRequest().getRequestURI() : 
											null)
						.timestamp(LocalDateTime.now())
						.build(), HttpStatus.valueOf(status));
	    }
	    
		@Override
		protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
				HttpHeaders headers, HttpStatusCode status, WebRequest request) {
	    	log.error("MethodArgumentNotValidException: ", ex);

	        List<String> errors = ex.getBindingResult()
	                                .getFieldErrors()
	                                .stream()
	                                .map(FieldError::getDefaultMessage)
	                                .collect(Collectors.toList());

			return new ResponseEntity<>(
					StandardResponse.builder()
						.message(String.join(", ", errors))
						.status(status.value())
						.timestamp(LocalDateTime.now())
						.path(request instanceof ServletWebRequest ? ((ServletWebRequest) request).getRequest().getRequestURI() : null)
						.build(),  HttpStatus.BAD_REQUEST);
		}
		
		 @ExceptionHandler(Exception.class)
	    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
	    	log.error("Exception: ", ex);

	        var status = HttpStatus.INTERNAL_SERVER_ERROR;
	        
			return new ResponseEntity<>(
					StandardResponse.builder()
						.message("An unexpected error occurred")
						.status(status.value())
						.timestamp(LocalDateTime.now())
						.path(request instanceof ServletWebRequest ? ((ServletWebRequest) request).getRequest().getRequestURI() : null)
						.build(),  status);
	    }
}
