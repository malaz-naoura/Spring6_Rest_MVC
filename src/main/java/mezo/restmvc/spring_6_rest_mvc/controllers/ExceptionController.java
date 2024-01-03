package mezo.restmvc.spring_6_rest_mvc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//you can use @ResponseStatus annotation for the exception class for more simplicity
//Look at NotFoundException class

@ControllerAdvice //Global exception handler
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException() {
            return ResponseEntity.notFound().build();
    }

}
