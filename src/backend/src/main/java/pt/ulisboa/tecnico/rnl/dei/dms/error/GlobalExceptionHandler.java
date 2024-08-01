package pt.ulisboa.tecnico.rnl.dei.dms.error;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.BindException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		for (ConstraintViolation<?> violation : violations) {
			errors.put(violation.getPropertyPath().toString(), violation.getMessage());
		}
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		return new ErrorResponse("Invalid input", "Malformed JSON request: " + ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		return new ErrorResponse("Validation failed", ex.getBindingResult().toString());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse handleException(Exception ex) {
		return new ErrorResponse("Internal server error", ex.getMessage());
	}
}
