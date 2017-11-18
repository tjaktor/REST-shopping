package com.tjaktor.restshopping.api.v1.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tjaktor.restshopping.api.v1.exception.ApiError;
import com.tjaktor.restshopping.core.exception.CartServiceException;
import com.tjaktor.restshopping.core.exception.NotFoundException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);
	
	/**
	 * The client sent an invalid request
	 * 
	 * @param ex {@link BindException} or {@link MethodArgumentNotValidException}
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request) {
		
		List<String> errors = new ArrayList<String>();
		
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errors.toString());
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}
	
	/**
	 * Request is missing parameter or the part of a multipart request not found
	 * 
	 * @param ex {@link MissingServletRequestParameterException} or {@link MissingServletRequestPartException}
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request) {
		
		String error = ex.getParameterName() + " parameter is missing";
		
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	/**
	 * Contains constrain violations
	 * 
	 * @param ex {@link ConstraintViolationException}
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<Object> handleConstraintViolation(
			ConstraintViolationException ex, WebRequest request) {
		
		List<String> errors = new ArrayList<>();
		
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " +
			violation.getMessage());
		}
		
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errors.toString());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	/**
	 * Given argument is wrong type
	 * 
	 * @param ex {@link MethodArgumentTypeMismatchException}
	 * @param request
	 * @return
	 */
	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, WebRequest request) {
		
		String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	/**
	 * Used unsupported HTTP method
	 * 
	 * @param ex {@link HttpRequestMethodNotSupportedException}
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */
	@Override
	public ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request) {
		
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Use: ");
		ex.getSupportedHttpMethods().forEach(method -> builder.append(method + " "));
		
		ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, builder.toString());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	/**
	 * Requested media type not unsupported
	 * 
	 * @param ex {@link HttpMediaTypeNotSupportedException}
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
			HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request) {
		
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(mediaType -> builder.append(mediaType + " "));
		
		ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2));
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	/**
	 * Requested object not found
	 * 
	 * @param ex {@link NotFoundException}
	 * @return
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiError> notFoundException(NotFoundException ex) {
		
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
		return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	/**
	 * Cart service throws an exception
	 * 
	 * @param ex {@link CartServiceException}
	 * @return
	 */
	@ExceptionHandler(CartServiceException.class)
	public ResponseEntity<ApiError> cartServiceException(CartServiceException ex) {
		
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	/**
	 * Default exception handler
	 * 
	 * @param ex {@link Exception}
	 * @return
	 */
	@ExceptionHandler({Exception.class})
	public ResponseEntity<ApiError> defaultException(Exception ex) {
		
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		
		return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());		
	}
	
}
