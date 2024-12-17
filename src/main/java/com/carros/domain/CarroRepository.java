package com.carros.domain;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarroRepository extends CrudRepository<Carro, Long> { // objeto em uso, tipo do id dele
    List<Carro> findByTipo (String tipo); // agr é list pra ver se esta vazia
    //ele entende que queremos uma lista (iterable) filtrada pelo atributo tipo pois é o mesmo nome usado no atributo da tabela
}
