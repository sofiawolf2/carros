package br.com.taurus.projeto.curso.carros;

import br.com.taurus.projeto.curso.carros.security.jwt.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class)
public class TokenJwtTest {
    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Test
    public void testToken() {

        // Le usuário
        UserDetails user = userDetailsService.loadUserByUsername("sofia"); // carrego um usuario
        assertNotNull(user);

        // Gera token
        String jwtToken = JwtUtil.createToken(user);
        System.out.println(jwtToken);
        assertNotNull(jwtToken);

        // Valida Token
        boolean ok = JwtUtil.isTokenValid(jwtToken);
        assertTrue(ok);

        // Valida login
        String login = JwtUtil.getLogin(jwtToken);// com o token da para recuperar o usuario de forma criptografada
        assertEquals("sofia",login);

        // Valida roles
        List<GrantedAuthority> roles = JwtUtil.getRoles(jwtToken);// inclusive da pra descobrir os roles pelo token
        assertNotNull(roles);
        System.out.println(roles);
        String role = roles.get(0).getAuthority();
        assertEquals(role,"ROLE_ADMIN");
    }

}