package br.com.taurus.projeto.curso.carros.service;

import br.com.taurus.projeto.curso.carros.domain.Carro;
import br.com.taurus.projeto.curso.carros.domain.CarroDTO;
import br.com.taurus.projeto.curso.carros.repository.CarroRepository;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired // o spring vai injetar essa dependencia automaticamente. nao criaremos um new. p spring vai fazer
    private CarroRepository carroRepository;

    public List<CarroDTO> getCarros(){
       return carroRepository.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());// ele chama a função create da classe CarroDTO
    }
    public List<CarroDTO> getCarrosByTipo(String tipo){
        return carroRepository.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public Optional<CarroDTO> getCarroById(Long id){
        return carroRepository.findById(id).map(CarroDTO::create);//esse ultimo comando faz a conversão do Carro (que retorna do rep.findById) para CarroDTO
    }

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possivel atualizar o registro");
        return CarroDTO.create(carroRepository.save(carro));
    } // save ja existe e retorna o objeto


    public CarroDTO update(Carro carro, Long id){
        Assert.notNull(id,"ID inválido. Não foi possivel atualizar o registro"); // verifica se o id é nulo
        Optional<Carro> desatualizado = carroRepository.findById(id);; // estou pegando o carro que quero modificar
        if (desatualizado.isPresent()){ // verifica se o carro existe
            Carro novoValor = desatualizado.get(); // retorna o carro, é uma função do optional
            novoValor.setNome(carro.getNome());
           // novoValor.setTipo(carro.getTipo());

            carroRepository.save(novoValor); //não é recomendavel salvar direto sem fazer esse processo pois nesses sistemas é comum objetos terem relacionamentos então se apagar e salvar por cima vai perder essas caracteristicas
            return CarroDTO.create(novoValor);
        }else{ return null;}
    }

    public boolean delete(Long id){
        if (getCarroById(id).isPresent()){
            carroRepository.deleteById(id); // esse metodo ja existe no repositorio
            return true;
        }
        return false;
    }


    public List<Carro> testeCarros() {
        return carroRepository.findAll();
    }
}
