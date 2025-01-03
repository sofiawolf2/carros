package br.com.taurus.projeto.curso.carros.exception;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice// permite que tratar exceções de maneira global, na aplicação inteira
public class ExceptionConfg {

    @ExceptionHandler ({ EmptyResultDataAccessException.class}) // estamos definindo que ao ocorrer essa exceção no sistema, vai ser chamado o metodo abaixo "errorNotFound" e executado. dessa forma definimos o que deve ser feito personalizadamente nesse caso
    public ResponseEntity errorNotFound (Exception ex){
        return ResponseEntity.notFound().build();
    }
}
