package br.com.taurus.projeto.curso.carros.domain;


import lombok.Data;
import javax.persistence.*;

@Entity @Data
//@Table(name = "usuario")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

}
