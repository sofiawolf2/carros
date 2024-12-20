package com.carros.domain.dto;
//Data Transfer Object-> vamos usar um dto para definir quais atributos do objeto queremos mostrar. imagine um objeto com
// 100 atributos e toda vez que fossemos listar varios objetos tivessimos que listar todos os 100 atributos de cada objeto?
// o DTO vai ser quem define quais atributos queremos mostrar

import com.carros.domain.Carro;
import lombok.Data;
import org.modelmapper.ModelMapper;//Ajuda a copiar os atributos de um objeto para outro

@Data
public class CarroDTO {
    private Long id;
    private String nome;
    private String tipo;

    public static CarroDTO create(Carro c) {// transforma um Carro num CarroDTO e retorna ele
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(c, CarroDTO.class); // precisa apenas que os atributos sejam iguais
    }


}
