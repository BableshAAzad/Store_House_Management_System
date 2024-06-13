package com.storehousemgm.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.storehousemgm.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {
	
	private ResponseEntity<ErrorStructure<String>> errorResponse(HttpStatus status, String errorMessage,String rootCause) {
		return ResponseEntity.status(status).body(new ErrorStructure<String>().setStatus(status.value())
				.setMessage(errorMessage).setRootCause(rootCause));
	}
	
     @ExceptionHandler
     public  ResponseEntity<ErrorStructure<String>> handleIllegalOperation(IllegalOperationException ex){
    	 return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Already SUPER_ADMIN exist");
     }

	 @ExceptionHandler
	 public ResponseEntity<ErrorStructure<String>> handleStoreHouseNotExist(StoreHouseNotExistException ex){
		 return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "StoreHouse not exist");
	 }

	 @ExceptionHandler
	 public ResponseEntity<ErrorStructure<String>> handleAdminNotFound(AdminNotExistException ex){
		 return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Admin not found in database");
	 }

	 @ExceptionHandler
	 public ResponseEntity<ErrorStructure<String>> handleAdminAlreadyExist(AdminAlreadyExistException ex){
		 return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Admin already exist database");
	 }

	 @ExceptionHandler
	 public ResponseEntity<ErrorStructure<String>> handleAddressNotExist(AddressNotExistException ex){
		 return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Address is not exist");
	 }

	 @ExceptionHandler
	 public ResponseEntity<ErrorStructure<String>> handleStorageNotExist(StorageNotExistException ex){
		 return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Storage is not exist");
	 }

     
 	@ExceptionHandler
 	public ResponseEntity<ErrorStructure<Map<String, String>>> handleMethodArgumentNotValid(
 			MethodArgumentNotValidException ex) {
 		List<ObjectError> objErrors = ex.getAllErrors();

 		Map<String, String> allErrors = new HashMap<>();
 		objErrors.forEach(error -> {
 			FieldError fieldError = (FieldError) error;
 			String field = fieldError.getField();
 			String message = fieldError.getDefaultMessage();
 			allErrors.put(field, message);
 		});

 		return ResponseEntity.status(HttpStatus.NOT_FOUND)
 				.body(new ErrorStructure<Map<String, String>>().setStatus(HttpStatus.NOT_FOUND.value())
 						.setMessage("Invalid Input").setRootCause(allErrors));
 	}
}
