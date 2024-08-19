package order.processing.system;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDto.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Unexpected error!")
                .build();
    }

    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleException(ValidationException validationException) {
        ErrorDto errorDto;
        if (validationException instanceof ConstraintViolationException) {
            String violation = extractViolationFromException((ConstraintViolationException) validationException);
            log.error(violation, validationException);
            errorDto = ErrorDto.builder()
                    .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .message(violation)
                    .build();
        } else {
            String exceptionMessage = validationException.getMessage();
            log.error(exceptionMessage, validationException);
            errorDto = ErrorDto.builder()
                    .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .message(exceptionMessage)
                    .build();
        }
        return errorDto;
    }

    private String extractViolationFromException(ConstraintViolationException violationException) {
        return violationException.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("--"));
    }
}
