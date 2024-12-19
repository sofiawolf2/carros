package com.carros.domain;


import org.springframework.data.jpa.repository.JpaRepository; // é filho do CrudRepository
//import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarroRepository extends JpaRepository<Carro, Long> {
    List<Carro> findByTipo (String tipo);
}
