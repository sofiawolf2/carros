package br.com.taurus.projeto.curso.carros.security.jwt;

import br.com.taurus.projeto.curso.carros.domain.User;
import br.com.taurus.projeto.curso.carros.domain.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Essa é a classe q controla o login. Ela que vai receber o jason com usuario e senha e vai retornar o userDTO juntamente com o token
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String AUTH_URL = "/api/v1/login";

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        // api/authenticate
        setFilterProcessesUrl(AUTH_URL);//aqui definimos que essa url vai ser a responsavel pelo endpoit de login
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {

        try {
            JwtLoginInput login = new ObjectMapper().readValue(request.getInputStream(), JwtLoginInput.class);
            String username = login.getUsername();
            String password = login.getPassword();

            if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                throw new BadCredentialsException("Invalid username/password.");
            }

            Authentication auth = new UsernamePasswordAuthenticationToken(username, password);// essa classe é do proprio spring

            return authenticationManager.authenticate(auth);// objetivo de usar o AuthenticationManager é chamar esse metodo
        } catch (IOException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();

        String jwtToken = JwtUtil.createToken(user);

//        String json = ServletUtil.getJson("token", jwtToken);
        String json = UserDTO.create(user, jwtToken).toJson();//no caso de segurança é recomendado trabalhar com json e não retornar um objeto que vai ser transformado sozinho em json como fazemos nos arquivos controllers
        ServletUtil.write(response, HttpStatus.OK, json);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException error) throws IOException, ServletException {

        String json = ServletUtil.getJson("error", "Login incorreto");
        ServletUtil.write(response, HttpStatus.UNAUTHORIZED, json);
    }


}


