package pmb.recipes.adapter.rest;

import jakarta.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pmb.recipes.adapter.rest.exception.NotFoundException;

/**
 * Handles exceptions across the whole application.
 *
 * @see ResponseEntityExceptionHandler
 */
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(ErrorHandler.class);

  @ExceptionHandler
  public ResponseEntity<Object> handleNotFoundException(
      NotFoundException exception, NativeWebRequest request) {
    return handleException(exception, HttpStatus.NOT_FOUND, null, "handleNotFoundException");
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex, WebRequest request) {
    return handleException(
        ex,
        HttpStatus.BAD_REQUEST,
        "Parameter '"
            + ex.getName()
            + "' doesn't match type definition, expected type: '"
            + ex.getRequiredType()
            + "'",
        "handleMethodArgumentTypeMismatch");
  }

  @ExceptionHandler
  protected ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException ex, WebRequest request) {
    return handleException(
        ex,
        HttpStatus.BAD_REQUEST,
        ex.getConstraintViolations().stream()
            .map(c -> "Field: '" + c.getPropertyPath() + "', Message: '" + c.getMessage() + "'")
            .collect(Collectors.joining()),
        "handleConstraintViolationException");
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    return handleException(
        ex,
        HttpStatus.BAD_REQUEST,
        "Parameter '" + ex.getParameterName() + "' is missing.",
        "handleMissingServletRequestParameter");
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    return handleException(ex, HttpStatus.BAD_REQUEST, null, "MethodArgumentNotValid");
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    return handleException(ex, HttpStatus.BAD_REQUEST, null, "HttpMessageNotReadable");
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> exceptionHandler(Exception e) {
    return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR, null, "Exception thrown");
  }

  private static ResponseEntity<Object> handleException(
      Exception e, HttpStatus status, String body, String message) {
    LOG.error(message, e);
    return new ResponseEntity<>(Optional.ofNullable(body).orElse(e.getMessage()), status);
  }
}
