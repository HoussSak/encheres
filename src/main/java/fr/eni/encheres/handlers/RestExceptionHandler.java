package fr.eni.encheres.handlers;


import fr.eni.encheres.exception.EntityNotFoundException;
import fr.eni.encheres.exception.InvalidEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value ={EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleException(EntityNotFoundException exception) {
        log.error(exception.getMessage(),exception);
        return ErrorDto.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }
    @ResponseBody
    @ExceptionHandler(value = {InvalidEntityException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleException(InvalidEntityException exception) {
        log.error(exception.getMessage(),exception);
        return ErrorDto.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }



}
