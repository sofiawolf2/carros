package com.carros.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarroService {

    @Autowired // o spring vai injetar essa dependencia automaticamente. nao criaremos um new. p spring vai fazer
    private CarroRepository rep;

    public Iterable<Carro> getCarros(){
        return rep.findAll();
    } // esse metodo findall ja existe no crudRepositorio

    public Optional<Carro> getCarroById(Long id){ return rep.findById(id);} // esse metodo findById ja existe no crudRepositorio
    public Iterable<Carro> getCarroByTipo(String tipo){return rep.findByTipo(tipo);} // ele não existe no crudRepositorio. Vamos criar
    public Carro salvar(Carro Carro) {return rep.save(Carro);} // save ja existe e retorna o objeto

    public List<Carro> getCarrosfake(){
        List<Carro> Carros = new ArrayList<Carro>();

        //no momento os Carros serão add manualmente e futuramente no banco de dados
        Carros.add(new Carro(1L,"Indomável")); //se não colocar o L ele entende como inteiro. isso é coisa do java
        Carros.add(new Carro(2L, "Amendoin"));
        Carros.add(new Carro(3L, "Viado"));


        return Carros;
    }

    public Iterable<Carro> getAll(){
        return rep.findAll();
    }
}
