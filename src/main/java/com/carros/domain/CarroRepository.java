package com.carros.domain;


import org.springframework.data.jpa.repository.JpaRepository; // é filho do CrudRepository
//import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarroRepository extends JpaRepository<Carro, Long> {
    List<Carro> findByTipo (String tipo);
}
/*
INTERABLE
É uma interface mais geral, usada quando você só precisa iterar sobre os elementos de uma coleção,
sem precisar manipular a estrutura de dados em profundidade.

LIST
É mais específica e é a escolha adequada quando você precisa de uma estrutura de dados que ofereça
capacidades adicionais relacionadas a listas, como a capacidade de acessar e manipular elementos em posições específicas.
 */

