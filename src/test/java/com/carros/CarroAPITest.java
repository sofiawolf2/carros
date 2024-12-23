package com.carros.api.carros;

import com.carros.CarrosApplication;
import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // web etc: da definindo que a porta de entrada é aleatoria
//CarrosApplication.class : idefine a classe que vai ser usada no teste

public class CarroAPITest {

    @Autowired
    protected TestRestTemplate rest;

    private ResponseEntity<CarroDTO> getCarro(String url){
        return rest.getForEntity(url, CarroDTO.class); // realiza uma solicitação http atravez de uma url e o tipo da classe usada
    }

    private ResponseEntity<List<CarroDTO>> getListaCarros( String url){
        return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CarroDTO>>() {// exchange: como o outro mas mais flexivel, tem mais variedade de entradas
        });
// ParameterizedTypeReference : aceita genericos, como List e Map
    }
}

