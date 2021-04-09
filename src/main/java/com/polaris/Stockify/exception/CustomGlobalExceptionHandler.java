package com.polaris.Stockify.exception;

import com.polaris.Stockify.payload.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler{


	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exc){
		ErrorResponse  error = new ErrorResponse();
		
		List<String> errors = new ArrayList<>();
		
		errors.add(exc.getLocalizedMessage());
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setMessage(exc.getLocalizedMessage());
		error.setErrors(errors);
		
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
		
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AuthenticationFailedException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationFailedException exc){
		ErrorResponse  error = new ErrorResponse();
		
		List<String> errors = new ArrayList<>();
		
		errors.add(exc.getLocalizedMessage());
		error.setStatus(HttpStatus.UNAUTHORIZED);
		error.setMessage(exc.getLocalizedMessage());
		error.setErrors(errors);
		
		return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
	}
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exc){
		ErrorResponse  error = new ErrorResponse();
		
		List<String> errors = new ArrayList<>();
		
		errors.add(exc.getLocalizedMessage());
		error.setStatus(HttpStatus.NOT_FOUND);
		error.setMessage(exc.getLocalizedMessage());
		error.setErrors(errors);
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	 
	 @Override
	 protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		 
	     List<String> errors = new ArrayList<String>();
	     
	     for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	         errors.add(error.getField() + ": " + error.getDefaultMessage());
	     }
	     for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
	         errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
	     }
	     
	    ErrorResponse apiError = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
	     return handleExceptionInternal(
	       ex, apiError, headers, apiError.getStatus(), request);
	 }

	 @Override
	 protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	    
		 String error = ex.getParameterName() + " parameter is missing";
	     
	    ErrorResponse apiError = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), List.of(error));
	     return new ResponseEntity<Object>(
	       apiError, new HttpHeaders(), apiError.getStatus());
	 }

	 @ExceptionHandler({ ConstraintViolationException.class })
	 public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
	     
		 List<String> errors = new ArrayList<String>();
	     
		 for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
	         errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
	     }

	     ErrorResponse apiError = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
	    
	     return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	 }
	 
	 @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	 public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
	   MethodArgumentTypeMismatchException ex, WebRequest request) {
	     
		 String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

	     ErrorResponse apiError = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),List.of(error));
	     return new ResponseEntity<Object>(
	       apiError, new HttpHeaders(), apiError.getStatus());
	 }
	 
	 @Override
	 protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	     String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

	     ErrorResponse apiError = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), List.of(error));
	     return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	 }
	 
	 @Override
	 protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
	   HttpRequestMethodNotSupportedException ex, 
	   HttpHeaders headers, 
	   HttpStatus status, 
	   WebRequest request) {
	     StringBuilder builder = new StringBuilder();
	     builder.append(ex.getMethod());
	     builder.append(
	       " method is not supported for this request. Supported methods are ");
	     ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

	     ErrorResponse apiError = new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), List.of(builder.toString()));
	     return new ResponseEntity<Object>(
	       apiError, new HttpHeaders(), apiError.getStatus());
	 }

	 @Override
	 protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
	   HttpMediaTypeNotSupportedException ex, 
	   HttpHeaders headers, 
	   HttpStatus status, 
	   WebRequest request) {
	     StringBuilder builder = new StringBuilder();
	     builder.append(ex.getContentType());
	     builder.append(" media type is not supported. Supported media types are ");
	     ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

	     ErrorResponse apiError = new ErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE,ex.getLocalizedMessage(), List.of(builder.substring(0, builder.length() - 2)));
	     return new ResponseEntity<Object>(
	       apiError, new HttpHeaders(), apiError.getStatus());
	 }
	 
	 @ExceptionHandler({AppException.class })
	 public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
	     ErrorResponse apiError = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), List.of("error occurred"));
	     return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	 }
}
