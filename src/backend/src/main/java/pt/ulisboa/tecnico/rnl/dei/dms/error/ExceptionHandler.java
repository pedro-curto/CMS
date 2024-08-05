package pt.ulisboa.tecnico.rnl.dei.dms.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceptionHandler extends RuntimeException {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

	private final ErrorMessage errorMessage;

	public ExceptionHandler(ErrorMessage errorMessage) {
		super(errorMessage.label);
		logger.error(errorMessage.label);
		this.errorMessage = errorMessage;
	}

	public ExceptionHandler(ErrorMessage errorMessage, String value) {
		super(String.format(errorMessage.label, value));
		logger.error(String.format(errorMessage.label, value));
		this.errorMessage = errorMessage;
	}

	public ExceptionHandler(ErrorMessage errorMessage, String value1, String value2) {
		super(String.format(errorMessage.label, value1, value2));
		logger.error(String.format(errorMessage.label, value1, value2));
		this.errorMessage = errorMessage;
	}

	public ExceptionHandler(ErrorMessage errorMessage, Long value) {
		super(String.format(errorMessage.label, value));
		logger.error(String.format(errorMessage.label, value));
		this.errorMessage = errorMessage;
	}

	public ExceptionHandler(ErrorMessage errorMessage, Long value1, Long value2) {
		super(String.format(errorMessage.label, value1, value2));
		logger.error(String.format(errorMessage.label, value1, value2));
		this.errorMessage = errorMessage;
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}
}

