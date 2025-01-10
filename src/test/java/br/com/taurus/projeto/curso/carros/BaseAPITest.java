package br.com.taurus.projeto.curso.carros;

import br.com.taurus.projeto.curso.carros.security.jwt.JwtUtil;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static junit.framework.TestCase.assertNotNull;
import static org.springframework.http.HttpMethod.*;
//classe mae para testes
@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseAPITest {
    @Autowired
    protected TestRestTemplate rest;

    @Autowired
    @Qualifier("userDetailsService")
    protected UserDetailsService userDetailsService;

    private String jwtToken = "";

    HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
        return headers;
    }

    @Before // vai ser executado antes de todos os testes
    public void setupTest() {
        // Le usuário
        UserDetails user = userDetailsService.loadUserByUsername("sofia");// carrego/chamo o usuario admin
        assertNotNull(user);

        // Gera token
        jwtToken = JwtUtil.createToken(user); // cria o token para o usuario e salva no atributo  "jwtToken"
        System.out.println(jwtToken);
        assertNotNull(jwtToken);
    }

    <T> ResponseEntity<T> post(String url, Object body, Class<T> responseType) {
        HttpHeaders headers = getHeaders(); // pego os headers e passo como entrada para fazer o post na linha 50

        return rest.exchange(url, POST, new HttpEntity<>(body, headers), responseType);
    }

    <T> ResponseEntity<T> get(String url, Class<T> responseType) {

        HttpHeaders headers = getHeaders();

        return rest.exchange(url, GET, new HttpEntity<>(headers), responseType);
    }

    <T> ResponseEntity<T> delete(String url, Class<T> responseType) {

        HttpHeaders headers = getHeaders();

        return rest.exchange(url, DELETE, new HttpEntity<>(headers), responseType);
    }
}