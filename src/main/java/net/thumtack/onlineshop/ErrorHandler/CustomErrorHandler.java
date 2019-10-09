package net.thumtack.onlineshop.ErrorHandler;

import net.thumtack.onlineshop.exceptions.ServerExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomErrorHandler {
    @ExceptionHandler(ServerExceptions.class)
    @ResponseBody
    public ResponseEntity handleServerErrors(ServerExceptions serverExceptions) {
        return new ResponseEntity<>(serverExceptions.getErrors(), HttpStatus.BAD_REQUEST);
    }
}
