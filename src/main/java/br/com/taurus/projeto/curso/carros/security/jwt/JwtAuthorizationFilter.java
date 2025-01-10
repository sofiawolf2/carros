package br.com.taurus.projeto.curso.carros.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//filtro responsavel por ler o parametro authorization do header, obter o token e verificar se é valido
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        String token = request.getHeader("Authorization");// leu o header

        if (StringUtils.isEmpty(token) || !token.startsWith("Bearer ")) {
            // Não informou o authorization
            filterChain.doFilter(request, response);
            return;// // continua a requisição normalmente e o proprio spring vai ver que não foi autorizado
        }

        try {

            if(! JwtUtil.isTokenValid(token)) {// ao chegar aqui significa que o header não esta vazio ou mal estruturado. agora vai chamar o metodo responsavel por validar ele
                throw new AccessDeniedException("Acesso negado.");
            }

            String login = JwtUtil.getLogin(token);// pega o login do token

            UserDetails userDetails = userDetailsService.loadUserByUsername(login);// carrega o login

            List<GrantedAuthority> authorities = JwtUtil.getRoles(token);// pegas as roles tambem usando o token

            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);// cria esse objeto autenticado com base nos dados do usuario retirados do token

            // Salva o Authentication no contexto do Spring
            SecurityContextHolder.getContext().setAuthentication(auth);// define esse objeto autenticado
            //um objeto autenticado não significa que tem permissão para acessa os recursos (vai depender da role que esse objeto representa)
            filterChain.doFilter(request, response);// continua a requisição

        } catch (RuntimeException ex) {
            logger.error("Authentication error: " + ex.getMessage(),ex);

            throw ex;
        }
    }
}
