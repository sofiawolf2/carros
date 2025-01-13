package br.com.taurus.projeto.curso.carros.service;

import br.com.taurus.projeto.curso.carros.exception.ObjectNotFoundException;
import br.com.taurus.projeto.curso.carros.domain.Carro;
import br.com.taurus.projeto.curso.carros.domain.dto.CarroDTO;
import br.com.taurus.projeto.curso.carros.repository.CarroRepository;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    public List<CarroDTO> getCarros(Pageable pageable){// agora podemos definir quantos carros aparecem por vez
       return carroRepository.findAll(pageable).stream().map(CarroDTO::create).collect(Collectors.toList());
    }
    public List<CarroDTO> getCarrosByTipo(String tipo,Pageable pageable){
        return carroRepository.findByTipo(tipo,pageable).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO getCarroById(Long id){
        Optional<Carro> carro = carroRepository.findById(id);
        return carro.map(CarroDTO::create).orElseThrow(()-> new ObjectNotFoundException("Carro não encontrado"));
    }

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possivel atualizar o registro");
        return CarroDTO.create(carroRepository.save(carro));
    }


    public CarroDTO update(Carro carro, Long id){
        Assert.notNull(id,"ID inválido. Não foi possivel atualizar o registro"); // verifica se o id eh nulo
        Optional<Carro> desatualizado = carroRepository.findById(id);; // estou pegando o carro que quero modificar
        if (desatualizado.isPresent()){ // verifica se o carro existe
            Carro novoValor = desatualizado.get(); // retorna o carro, é uma função do optional
            novoValor.setNome(carro.getNome());

            carroRepository.save(novoValor); //noo eh recomendavel salvar direto sem fazer esse processo pois nesses sistemas é comum objetos terem relacionamentos então se apagar e salvar por cima vai perder essas caracteristicas
            return CarroDTO.create(novoValor);
        }else{ return null;}
    }

    public void delete(Long id){
        carroRepository.deleteById(id);
    }


    public List<Carro> testeCarros() {
        return carroRepository.findAll();
    }
}
