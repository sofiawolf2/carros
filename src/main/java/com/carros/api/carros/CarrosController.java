package com.carros.api.carros;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {

    @Autowired
    private CarroService servise;

    @GetMapping
    public ResponseEntity getListaCarros() {// TIPAGEM DE RESPONSEENTITY: na vdd o responseentity é do tipo generico, ou seja ele aceita qualquer tipo. Estavamos especificando antes mais para verificar se o metodo esta funcionando e retornando corretamente. Agora não precisa mais
        return ResponseEntity.ok(servise.getCarros());
    }


    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){ //aqui não colocamos que o ResponseEntity é do tipo Optional<Carro> pois pode ser que não retorne um carro. Pode ser que retorne apenas um erro. No caso um Renponse Entity
        Optional<CarroDTO> carro = servise.getCarroById(id);
        if (carro.isPresent()){
            return ResponseEntity.ok(carro.get()); // o paratro poderia ser direto servise.getCarroById(id) mas separamos para poder verificar antes se existe. Pois o Optional aceita null
        }else{
            return ResponseEntity.notFound().build(); // no caso de ok tambem deveriamos colocar o .build() mas como estamos colocando um parametro dentro, não precisa
        }// no caso de notFound não queremos que retorne nem sequer null. deve retornar nada e um erro. apenas. 404
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity getCarrosByTipo(@PathVariable("tipo") String tipo){
        List<CarroDTO> carros = servise.getCarrosByTipo(tipo);
        return carros.isEmpty()?
                ResponseEntity.noContent().build() : ResponseEntity.ok(carros);
    }




    @PostMapping // se fizer um post no /api/v1/cavalos passando um JSON de cavalo como parametro, ele vai executar esse metodo:
    public String post(@RequestBody Carro carro){//@RequestBody converte o JSON do cavalo para o objeto cavalo
        // o JSON precisa ter os mesmos atributos do objeto para funcionar
        return "Carro salvo " + servise.salvar(carro).getId();
    }

    /*
    @PutMapping("/{id}")
    public String put(@PathVariable("id") Long id, @RequestBody Carro carro){
        return "Carro atualizado com sucesso de id: " + servise.update(carro, id).getId();
    }
    */

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        servise.delete(id);
        return "Carro deletado com sucesso id: " + id;
    }


}
