package pt.ulisboa.tecnico.rnl.dei.dms.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CMSException extends RuntimeException {
	private static final Logger logger = LoggerFactory.getLogger(CMSException.class);

	private final ErrorMessage errorMessage;

	public CMSException(ErrorMessage errorMessage) {
		super(errorMessage.label);
		logger.error(errorMessage.label);
		this.errorMessage = errorMessage;
	}

	public CMSException(ErrorMessage errorMessage, String value) {
		super(String.format(errorMessage.label, value));
		logger.error(String.format(errorMessage.label, value));
		this.errorMessage = errorMessage;
	}

	public CMSException(ErrorMessage errorMessage, Long value) {
		super(String.format(errorMessage.label, value));
		logger.error(String.format(errorMessage.label, value));
		this.errorMessage = errorMessage;
	}

	public CMSException(ErrorMessage errorMessage, double sum) {
		super(String.format(errorMessage.label, sum));
		logger.error(String.format(errorMessage.label, sum));
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

