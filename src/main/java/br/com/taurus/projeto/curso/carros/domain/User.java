package br.com.taurus.projeto.curso.carros.domain;


import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity @Data
@Table(name = "usuario") // não foi possivel salvar a tabela com o mesmo nome pois no postgresql user é um nome reservado
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String login;
    private String senha;
    private String email;

    public static void main (String[] args){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("taurus"));
    }

}
