package br.com.taurus.projeto.curso.carros.domain;

import lombok.*;
//O Lombok vai gerar os getters e setters (entre outros metodos inclusive construtores vazios ou não mas nao usaremos contrutores) automaticamente agora
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data // equivalente a @Getter @Setter @ToString @EqualsAndHashCode tudo junto
public class Carro {

    @Id //indicando que esse atributo é o da chave primaria do bd
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // ao salvar um nosso carro a jpa vai fazer o auto incremento do id atravez de uma chave primaria
    private Long id;
    private String nome;
    private String tipo;

}

