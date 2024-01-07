package mezo.restmvc.spring_6_rest_mvc.controllers;

import jakarta.persistence.RollbackException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

//you can use @ResponseStatus annotation for the exception class for more simplicity
//Look at NotFoundException class

@ControllerAdvice //Global exception handler
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException() {
        return ResponseEntity.notFound()
                             .build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethod(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest()
                             .body(exception.getFieldErrors()
                                            .stream()
                                            .map(fieldError -> {

                                                return new StringJoiner(" --> ").add(fieldError.getObjectName())
                                                                                .add(fieldError.getField())
                                                                                .add(fieldError.getDefaultMessage())
                                                                                .toString();
                                            })
                                            .collect(Collectors.toList()));
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity jpaIntegrityViolation(TransactionSystemException exception) {

        ConstraintViolationException exc = (ConstraintViolationException) exception.getCause()
                                                                                   .getCause();
        ConstraintViolation exc2 = exc.getConstraintViolations()
                                      .stream()
                                      .findFirst()
                                      .get();

        StringJoiner msg= new StringJoiner(" ").add(exc2.getPropertyPath()
                                      .toString())
                             .add("In --> ")
                             .add(exc2.getRootBeanClass()
                                      .getName())
                             .add(" : ")
                             .add(exc2.getMessage());

        return ResponseEntity.badRequest()
                             .body(msg.toString());
    }
}
