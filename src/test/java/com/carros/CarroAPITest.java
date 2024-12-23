package com.carros.api.carros;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api/v1/carros")
public class CarroAPITest {

    @Autowired
    private CarroService carroService;

    @GetMapping
    public ResponseEntity getListaCarros() {
        return ResponseEntity.ok(carroService.getCarros());
    }

    @GetMapping("teste")
    public List<Carro> testeCarros(){
        return carroService.testeCarros();
    }


    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        Optional<CarroDTO> carro = carroService.getCarroById(id);
        if (carro.isPresent()){
            return ResponseEntity.ok(carro.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity getCarrosByTipo(@PathVariable("tipo") String tipo){
        List<CarroDTO> carros = carroService.getCarrosByTipo(tipo);
        return carros.isEmpty()?
                ResponseEntity.noContent().build() : ResponseEntity.ok(carros);
    }


    @PostMapping
    public ResponseEntity post(@RequestBody Carro carro){
        try{
            carroService.insert(carro);
            URI location = geturi(carro.getId());
            return ResponseEntity.created(location).build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    private URI geturi (Long id){
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

    }


    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Carro carro){
        CarroDTO c = carroService.update(carro, id);
        return  c != null? 
                ResponseEntity.ok(c) : ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        return carroService.delete(id)?
                ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }


}
