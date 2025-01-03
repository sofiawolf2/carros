package br.com.taurus.projeto.curso.carros.controller;

import br.com.taurus.projeto.curso.carros.domain.Carro;
import br.com.taurus.projeto.curso.carros.service.CarroService;
import br.com.taurus.projeto.curso.carros.domain.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {

    @Autowired
    private CarroService carroService;

    @GetMapping
    public ResponseEntity getListaCarros() {// TIPAGEM DE RESPONSEENTITY: na vdd o responseentity é do tipo generico, ou seja ele aceita qualquer tipo. Estavamos especificando antes mais para verificar se o metodo esta funcionando e retornando corretamente. Agora não precisa mais
        return ResponseEntity.ok(carroService.getCarros());
    }

    @GetMapping("teste")
    public List<Carro> testeCarros(){
        return carroService.testeCarros();
    }


    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){ //aqui não colocamos que o ResponseEntity é do tipo Optional<Carro> pois pode ser que não retorne um carro. Pode ser que retorne apenas um erro. No caso um Renponse Entity
        Optional<CarroDTO> carro = carroService.getCarroById(id);
        if (carro.isPresent()){
            return ResponseEntity.ok(carro.get()); // o paratro poderia ser direto carroService.getCarroById(id) mas separamos para poder verificar antes se existe. Pois o Optional aceita null
        }else{
            return ResponseEntity.notFound().build(); // no caso de ok tambem deveriamos colocar o .build() mas como estamos colocando um parametro dentro, não precisa
        }// no caso de notFound não queremos que retorne nem sequer null. deve retornar nada e um erro. apenas. 404
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity getCarrosByTipo(@PathVariable("tipo") String tipo){
        List<CarroDTO> carros = carroService.getCarrosByTipo(tipo);
        return carros.isEmpty()?
                ResponseEntity.noContent().build() : ResponseEntity.ok(carros);
    }


    @PostMapping
    public ResponseEntity post(@RequestBody Carro carro){//@RequestBody converte o JSON do cavalo para o objeto carro
        try{
            carroService.insert(carro);
            URI location = geturi(carro.getId());
            return ResponseEntity.created(location).build(); // http status 201. Indica que um novo recurso foi criado com sucesso (nova localização é a prova)
            // não é obrigatorio inserir a nova localizao, pode so colocar um null
        }catch (Exception e){
            return ResponseEntity.badRequest().build();// http status 400
            //indica que a requisição não foi processada devido a um problema, como um objeto malformado ou uma falha na persistência
        }
    }

    private URI geturi (Long id){
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        /* EXPLICAÇÃO DO COMANDO-----
         ServletUriComponentsBuilder.fromCurrentRequest(): cria um objeto ServletUriComponentsBuilder que inicia a construção da URI baseada na requisição atual
         ou seja: ele vai pegar o endereço atual (http://localhost:8080/api/vi/carros) para se basear e contruir a URI do id indicado
         .path("/{id}"): esse metodo define um caminho que será adicionado à URI que estamos criando. no caso vai ser add /algo. no caso o algo é o id
         .buildAndExpand(id): constroi a URI final, substituindo o {id} pelo valor do id
         .toUri(): converte a construção atual (que é uma instancia de UriComponents) em um objeto URI.
         */
    }


    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Carro carro){
        CarroDTO c = carroService.update(carro, id);
        return  c != null? // se o carro existe
                ResponseEntity.ok(c) : ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        carroService.delete(id);
        return ResponseEntity.ok().build();
    }


}
