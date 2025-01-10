package br.com.taurus.projeto.curso.carros;

import br.com.taurus.projeto.curso.carros.domain.Carro;
import br.com.taurus.projeto.curso.carros.domain.dto.CarroDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarrosAPITest extends BaseAPITest {

    private ResponseEntity<CarroDTO> getCarro(String url) {return get(url, CarroDTO.class);} //dentro chama o get da classe mae

    private ResponseEntity<List<CarroDTO>> getListaCarros(String url) {
        HttpHeaders headers = getHeaders();

        return rest.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers), //´passando os headers com o parametro autozizado
                new ParameterizedTypeReference<List<CarroDTO>>() {
                });
    }

    @Test
    public void testeList (){
        List<CarroDTO> carros = getListaCarros("/api/v1/carros").getBody();
        assertNotNull(carros);
        assertEquals(30,carros.size()); // apenas verificando se tem exatamente 30 carros no banco de dados
    }

    @Test
    public void testListaPorTipo(){
        assertEquals(10,getListaCarros("/api/v1/carros/tipo/classicos").getBody().size()); // testando se tem 10 de cada tipo
        assertEquals(10,getListaCarros("/api/v1/carros/tipo/esportivos").getBody().size());
        assertEquals(10,getListaCarros("/api/v1/carros/tipo/luxo").getBody().size());

        assertEquals(HttpStatus.NO_CONTENT, getListaCarros("/api/v1/carros/tipo/xxx").getStatusCode());// testando um tipo q não esxiste
    }

    @Test
    public void testGetOk(){
        ResponseEntity<CarroDTO> response = getCarro("/api/v1/carros/11");
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        CarroDTO c = response.getBody();
        assertEquals("Ferrari FF", c.getNome());
        // resumo esta testando se o carro tem esses atributos, se o satus http é ok 200 etc
    }

    @Test
    public void testGetNotFound(){
        ResponseEntity response = getCarro("/api/v1/carros/200"); // solicitando um carro que não existe
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND); // verificando se o status de retorno é que não achou o carro
    }

    @Test
    public void testSave(){
        Carro carro = new Carro();
        carro.setNome("Fusca");
        carro.setTipo("classicos");

        //insert
        ResponseEntity response = post("/api/v1/carros", carro, null);// fazer um post enviando a url, o objeto e o tipo de retorno como parametro
        System.out.println(response);

        // verificando se foi criado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        //bucando o objeto
        String location = response.getHeaders().get("location").get(0);
        CarroDTO c = getCarro(location).getBody();
        assertNotNull(c);
        assertEquals("Fusca", c.getNome());
        assertEquals("classicos", c.getTipo());

        // deletar o objeto
        delete(location,null);

        //verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, getCarro(location).getStatusCode());
    }
}