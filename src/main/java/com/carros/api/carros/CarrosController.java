package com.carros.api.carros;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {

    @Autowired
    private CarroService servise; // não precisa mais criar um new objeto pq o @Autowired vai faer isso por si so
    // isso é o conceito de DI = INJEÇÃO DE DEPENDENCIAS

    @GetMapping
    public Iterable<Carro> getListacarros() {
        return servise.getCarros();
    }
    // INTERABLE é uma interface que representa uma coleção que pode ser listada.
    // permite acessar os elementos de uma coleção um a um sem expor a implementação interna deles

    @GetMapping ("/{id}")
    public Optional<Carro> get(@PathVariable("id") Long id){ return servise.getCarroById(id);}
    // OPTIONAL também é uma interface. é uma classe conteiner que representa um valor que pode ou não estar presente
    // é usado quando pode retornar null (o valor pode ou não existir)

/*    @GetMapping("tipo/{tipo}")
    public Iterable<Carro> get(@PathVariable("tipo") String tipo){return servise.getCarroByTipo(tipo);}*/

    @PostMapping // se fizer um post no /api/v1/cavalos passando um JSON de cavalo como parametro, ele vai executar esse metodo:
    public String post(@RequestBody Carro carro){//@RequestBody converte o JSON do cavalo para o objeto cavalo
        // o JSON precisa ter os mesmos atributos do objeto para funcionar
        return "Carro salvo " + servise.salvar(carro).getId();
    }

}
