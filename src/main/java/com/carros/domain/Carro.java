package com.carros.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Carro {

    @Id //indicando que esse atributo é o da chave primaria do bd
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // ao salvar um nosso cavalo a jpa vai fazer o auto incremento do id atravez de uma chave primaria
    private Long id;
    // se o atributo fosse diferente o nome teriamos q colocar isso @Colum(name = "nome")
    private String nome;
   // private String tipo;


    public Carro() {
    }

    public Carro(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}

