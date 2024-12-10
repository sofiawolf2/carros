package com.carros.domain;


import org.springframework.data.repository.CrudRepository;

public interface CarroRepository extends CrudRepository<Carro, Long> { // objeto em uso, tipo do id dele
    Iterable<Carro> findByTipo (String tipo);
    //ele entende que queremos uma lista (iterable) filtrada pelo atributo tipo pois é o mesmo nome usado no atributo da tabela
}
