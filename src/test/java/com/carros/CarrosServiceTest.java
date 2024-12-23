package com.carros;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

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
        Optional<CarroDTO> op = service.getCarroById(id);
        assertTrue(op.isPresent());
        c2 = op.get();
        assertEquals(c.getNome(),c2.getNome());
        assertEquals(c.getTipo(), c2.getTipo());

        service.delete(id);

        // veficicar se realmente apagou:
        assertFalse(service.getCarroById(id).isPresent());
    }


    @Test
    public void contextLoads(){
        service.delete(31L);

    }

    @Test
    public void testeListas(){

    }

}
