package com.carros.api.carros;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {

    @Autowired
    private CarroService servise; // não precisa mais criar um new objeto pq o @Autowired vai faer isso por si so
    // isso é o conceito de DI = INJEÇÃO DE DEPENDENCIAS

    @GetMapping // esse metodo não precisaria da aplicação dos status de retorno do http pois ele deve voltar 200 que é o numero padrão mas faremos o codigo em todos
    public ResponseEntity<Iterable<Carro>> getListacarros() {
       //return new ResponseEntity<>(servise.getCarros(), HttpStatus.OK);
        // aqui vai chavar o servise que precisamos e caso finalizado retorna o status correspoendente do comando http (200)
        // o responseEntity recebe o objeto que vai retornar como JSON

        return ResponseEntity.ok(servise.getCarros()); // isso é outra maneira de fazer a linha 22. é um atalho
    }


    @GetMapping ("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){ //aqui não colocamos que o ResponseEntity é do tipo Optional<Carro> pois pode ser que não retorne um carro. Pode ser que retorne apenas um erro. No caso um Renponse Entity
        Optional<Carro> carro = servise.getCarroById(id);
        if (carro.isPresent()){
            return ResponseEntity.ok(carro.get()); // o paratro poderia ser direto servise.getCarroById(id) mas separamos para poder verificar antes se existe. Pois o Optional aceita null
        }else{
            return ResponseEntity.notFound().build(); // no caso de ok tambem deveriamos colocar o .build() mas como estamos colocando um parametro dentro, não precisa
        }// no caso de notFound não queremos que retorne nem sequer null. deve retornar nada e um erro. apenas. 404
    }
    /* podemos simplificar o codigo usando a versão reduzida do if else (com operadores ternarios):
    return condição?
        returno caso sim:
        retorno caso não

     Usando outra forma:
     return carro
             .map(ResponseEntity::ok) // aqui estou passando o objeto carro como parametro dentro do ResponseEntity.ok caso não fosse o proprio objeto teria que usar essa estrutura carro.map(c -> ResponseEntity.ok(c))
             .orElse(ResponseEntity.notFound().build());
             // no .map ele ja faz a verificação se existe
     */



    @PostMapping // se fizer um post no /api/v1/cavalos passando um JSON de cavalo como parametro, ele vai executar esse metodo:
    public String post(@RequestBody Carro carro){//@RequestBody converte o JSON do cavalo para o objeto cavalo
        // o JSON precisa ter os mesmos atributos do objeto para funcionar
        return "Carro salvo " + servise.salvar(carro).getId();
    }

    @PutMapping("/{id}")
    public String put(@PathVariable("id") Long id, @RequestBody Carro carro){
        return "Carro atualizado com sucesso de id: " + servise.update(carro, id).getId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        servise.delete(id);
        return "Carro deletado com sucesso id: " + id;
    }


}
