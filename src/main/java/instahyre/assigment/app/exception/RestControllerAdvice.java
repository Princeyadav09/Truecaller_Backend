package instahyre.assigment.app.exception;

import instahyre.assigment.app.enums.ErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestControllerAdvice.class);

	private final MessageSource messageSource;

	private final Locale currentLocale = LocaleContextHolder.getLocale();

	@Autowired
	public RestControllerAdvice(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleAllExceptionMethod(final MethodArgumentNotValidException ex,
			final WebRequest request) {
		List<ExceptionResponse> exceptionMessageList = new ArrayList<>();

		ex.getBindingResult().getAllErrors().forEach(error -> {
			ExceptionResponse exceptionMessageObj = new ExceptionResponse();
			exceptionMessageObj.setStatus(HttpStatus.BAD_REQUEST.value());
			exceptionMessageObj.setCode(ErrorCodes.NOT_NULL_VALIDATION.getValue());
			exceptionMessageObj.setMessage(error.getDefaultMessage());
			exceptionMessageObj.setError(ex.getClass().getCanonicalName());
			exceptionMessageObj.setPath(((ServletWebRequest) request).getRequest().getServletPath());
			exceptionMessageList.add(exceptionMessageObj);
		});
		return new ResponseEntity<>(exceptionMessageList, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<Object> handleAllExceptionMethod(final NumberFormatException ex, final WebRequest request) {
		ExceptionResponse exceptionMessageObj = new ExceptionResponse();
		exceptionMessageObj.setStatus(HttpStatus.BAD_REQUEST.value());
		exceptionMessageObj.setCode(ErrorCodes.NOT_NULL_VALIDATION.getValue());
		exceptionMessageObj.setMessage(ex.getMessage());
		exceptionMessageObj.setError(ex.getClass().getCanonicalName());
		exceptionMessageObj.setPath(((ServletWebRequest) request).getRequest().getServletPath());
		return new ResponseEntity<>(exceptionMessageObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ExceptionResponse> handleBusinessException(final BusinessException exc,
			final WebRequest request) {
		String errorMessage = "APP_000";
		try {
			errorMessage = messageSource.getMessage(exc.getMessage(), exc.getArgs(), currentLocale);
		} catch (Exception e) {
			LOGGER.info("Rest Controller Advice Exception {}", errorMessage);
		}
		return ResponseEntity.status(Objects.nonNull(exc.getStatus()) ? exc.getStatus() : HttpStatus.BAD_REQUEST)
				.body(new ExceptionResponse(exc.getStatus().value(), exc.getStatus().getReasonPhrase(), errorMessage,
						((ServletWebRequest) request).getRequest().getServletPath(), exc.getCode(), exc.getMessage()));
	}
}
