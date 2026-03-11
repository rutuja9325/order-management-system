package com.ecommerce.oms.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {

	   @ExceptionHandler(OutOfStockException.class)
	    public ResponseEntity<String> handleOutOfStock(OutOfStockException ex) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(ex.getMessage());
	    }

	    @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(ex.getMessage());
	    }
	    
	    @ExceptionHandler(ProductNotFoundException.class)
	    public ResponseEntity<String> handleProductNotFound(ProductNotFoundException ex) {
	        return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body(ex.getMessage());
	    }

	    @ExceptionHandler(ProductInactiveException.class)
	    public ResponseEntity<String> handleProductInactive(ProductInactiveException ex) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(ex.getMessage());
	    }
	    
	    
	    @ExceptionHandler(IllegalArgumentException.class)
	    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(ex.getMessage());
	    }
	    
	    
	    @ExceptionHandler(IllegalStateException.class)
	    public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(ex.getMessage());
	    }

	    @ExceptionHandler(RuntimeException.class)
	    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
	        return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body(ex.getMessage());
	    }

	    
	    
}
