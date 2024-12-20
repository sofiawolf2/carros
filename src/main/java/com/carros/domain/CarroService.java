package com.carros.domain;

import com.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired // o spring vai injetar essa dependencia automaticamente. nao criaremos um new. p spring vai fazer
    private CarroRepository rep;

    public List<CarroDTO> getCarros(){
       return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());// ele chama a função create da classe CarroDTO
    }
    public List<CarroDTO> getCarrosByTipo(String tipo){
        return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public Optional<CarroDTO> getCarroById(Long id){
        return rep.findById(id).map(CarroDTO::new);//esse ultimo comando faz a conversão do Carro (que retorna do rep.findById) para CarroDTO
    }

    public Carro salvar(Carro Carro) {return rep.save(Carro);} // save ja existe e retorna o objeto

    /*

    public Carro update(Carro carro, Long id){
        Assert.notNull(id,"ID inválido. Não foi possivel atualizar o registro"); // verifica se o id é nulo

        Optional<Carro> desatualizado = new Carro(getCarroById(id)); // estou pegando o carro que quero modificar
        if (desatualizado.isPresent()){ // verifica se o carro existe
            Carro novoValor = desatualizado.get(); // retorna o carro, é uma função do optional
            novoValor.setNome(carro.getNome());
           // novoValor.setTipo(carro.getTipo());

            rep.save(novoValor); //não é recomendavel salvar direto sem fazer esse processo pois nesses sistemas é comum objetos terem relacionamentos então se apagar e salvar por cima vai perder essas caracteristicas
            return novoValor;
        }else{throw new RuntimeException("Carro não existe. Não foi possivel atualizar o registro");

        }
    }
     */

    public void delete(Long id){
        if (getCarroById(id).isPresent()){
            rep.deleteById(id); // esse metodo ja existe no repositorio
        }else{
            throw new RuntimeException("Carro não existe");
        }
    }


}
