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
        return new ResponseEntity<>(servise.getCarros(), HttpStatus.OK); // aqui vai chavar o servise que precisamos e caso finalizado retorna o status correspoendente do comando http (200)
    }


    @GetMapping ("/{id}")
    public Optional<Carro> get(@PathVariable("id") Long id){ return servise.getCarroById(id);}



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
