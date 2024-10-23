package com.example.chatmessages.exception;

import com.example.chatmessages.constant.ErrorCode;
import com.example.chatmessages.dto.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String BAD_RQ = "Bad Request";

    /**
     * Handle exception when validate data
     *
     * @param e
     * @param request
     * @return errorResponse
     */
    @ExceptionHandler({
            ConstraintViolationException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleValidationException(Exception e, WebRequest request) {
        ErrorResponse.ErrorResponseBuilder builder = ErrorResponse.builder()
                .timestamp(new Date())
                .status(BAD_REQUEST.value())
                .path(request.getDescription(false).replace("uri=", ""));

        String message = e.getMessage();
        if (e instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errorMessages = fieldErrors.stream()
                    .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .toList();
            message = String.join(", ", errorMessages);
            builder.error("Invalid Payload").message(message);
        } else {
            builder.error("Invalid Data").message(message);
        }

        return builder.build();
    }

    /**
     * Handle exception when the request not found data
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        log.error("Resource Not Found Exception: ", e);
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(NOT_FOUND.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(NOT_FOUND.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }

    /**
     * Handle exception when the data is conflicted
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(CONFLICT)
    public ErrorResponse handleDuplicateKeyException(InvalidDataException e, WebRequest request) {
        log.error("Duplicate Key Exception: ", e);
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(CONFLICT.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(CONFLICT.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }

    /**
     * Handle exception when internal server error
     *
     * @param e
     * @param request
     * @return error
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e, WebRequest request) {
        log.error("Exception: ", e);
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(INTERNAL_SERVER_ERROR.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ErrorResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.FORBIDDEN;
        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ErrorResponse.builder()
                        .status(errorCode.getStatusCode().value())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    /*
     * No static resource - 404
     * */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleNoResourceFoundException(NoResourceFoundException e) {
        log.error("No Resource Found Exception: ", e);
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(NOT_FOUND.value())
                .path(e.getResourcePath())
                .error(e.getMessage())
                .message(NOT_FOUND.getReasonPhrase())
                .build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e,
            WebRequest request) {
        log.error("Method Argument Type Mismatch Exception: ", e);
        log.error(e.toString());
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(BAD_REQUEST.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(BAD_RQ)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e, WebRequest request) {
        log.error("Data Integrity Violation Exception: ", e);
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.CONFLICT.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message("Data integrity violation: " + e.getMessage())
                .build();
    }

    /**
     * Handle IllegalArgumentException.
     *
     * @param e       the exception
     * @param request the web request
     * @return the error response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(BAD_RQ)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(CONFLICT)
    public ErrorResponse handleAlreadyExistsException(AlreadyExistsException e, WebRequest request) {
        log.error("Already Exists Exception: ", e);
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(CONFLICT.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(CONFLICT.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(NotExistsException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleNotExistsException(NotExistsException e, WebRequest request) {
        log.error("Not exits exception: ", e);
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(NOT_FOUND.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(NOT_FOUND.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e, WebRequest request) {
        log.error("Not Found exception: ", e);

        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.NOT_FOUND.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(CustomJsonProcessingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleCustomJsonProcessingException(CustomJsonProcessingException e, WebRequest request) {
        log.error("JSON Processing Error: ", e);
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(BAD_RQ)
                .message("There was an error processing the JSON data: " + e.getMessage())
                .build();
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUnexpectedTypeException(UnexpectedTypeException e, WebRequest request) {
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(BAD_RQ)
                .message(e.getMessage())
                .build();
    }

    /**
     * Handle exception when a field with the same name already exists
     *
     * @param e the ExistsByNameException
     * @param request the web request
     * @return errorResponse
     */
    @ExceptionHandler(ExistsByNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleExistsByNameException(ExistsByNameException e, WebRequest request) {
        log.error("Exists By Name Exception: ", e);
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getStatusCode().value());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }
    @ExceptionHandler(RoleDeletionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRoleDeletionException(RoleDeletionException e, WebRequest request) {
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error("Role Deletion Error")
                .message(e.getMessage())
                .build();
    }
}