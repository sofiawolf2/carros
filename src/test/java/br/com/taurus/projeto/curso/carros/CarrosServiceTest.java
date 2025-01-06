package br.com.taurus.projeto.curso.carros;

import br.com.taurus.projeto.curso.carros.domain.Carro;
import br.com.taurus.projeto.curso.carros.service.CarroService;
import br.com.taurus.projeto.curso.carros.domain.dto.CarroDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarrosServiceTest {
    @Autowired
    private CarroService service;


    @Test
    public void teste(){
        Carro c = new Carro();
        c.setNome("Aviao");
        c.setTipo("voador");
        CarroDTO c2 = service.insert(c);
        assertNotNull(c2);
        Long id = c.getId();
        assertNotNull(id);

        c2 = service.getCarroById(id);
        assertNotNull(c2);

        assertEquals(c.getNome(),c2.getNome());
        assertEquals(c.getTipo(), c2.getTipo());

        service.delete(id);

        // veficicar se realmente apagou:
        try {
            assertNull(service.getCarroById(id)); // como o carro não existe, o metodo dentro do servoce vai lançar uma exceção
        } catch (Exception e) { // aqui estamos apenas dizendo q se lançar exceção n faça nada (ta correto)
            //ok
        }
    }


    @Test
    public void contextLoads(){
        service.delete(31L);

    }

    @Test
    public void testeListas(){

    }

    @Test
    public void testGet(){
        CarroDTO c = service.getCarroById(11L);
        assertNotNull(c);
        assertEquals("Ferrari FF", c.getNome());
    }

}
