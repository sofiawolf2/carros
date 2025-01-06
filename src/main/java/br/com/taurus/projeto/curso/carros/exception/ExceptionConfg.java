package br.com.taurus.projeto.curso.carros.exception;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice// permite que tratar exceções de maneira global, na aplicação inteira
public class ExceptionConfg extends ResponseEntityExceptionHandler {

    @ExceptionHandler ({ EmptyResultDataAccessException.class}) // estamos definindo que ao ocorrer essa exceção no sistema, vai ser chamado o metodo abaixo "errorNotFound" e executado. dessa forma definimos o que deve ser feito personalizadamente nesse caso
    public ResponseEntity errorNotFound (Exception ex){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({ IllegalArgumentException.class})
    public ResponseEntity errorBadRequest (Exception ex){
        return ResponseEntity.badRequest().build();
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("Operação não permitida", HttpStatus.METHOD_NOT_ALLOWED);
    }

}
