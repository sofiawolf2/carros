package br.com.taurus.projeto.curso.carros.security;

import br.com.taurus.projeto.curso.carros.security.jwt.JwtAuthenticationFilter;
import br.com.taurus.projeto.curso.carros.security.jwt.JwtAuthorizationFilter;
import br.com.taurus.projeto.curso.carros.security.jwt.handler.AccessDeniedHandler;
import br.com.taurus.projeto.curso.carros.security.jwt.handler.UnauthorizedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration @EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // habilita a segurança por metodo
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired @Qualifier ("userDetailsService") // usado para definir qual classe estamos usando em vez de definitir UserDetailsServiceImp
    private UserDetailsService userDetailsService;

    @Autowired
    private UnauthorizedHandler unauthorizedHandler;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().csrf().disable(); //retira a requisição de autenticação de chamadas

         */
        AuthenticationManager authManager = authenticationManager();

        http
                .authorizeRequests()
                .anyRequest().authenticated() //qualquer request precisa estar autenticado
                .antMatchers(HttpMethod.GET, "/api/v1/login").permitAll()// no login pode sem autenticar
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .and().csrf().disable()
                .addFilter(new JwtAuthenticationFilter(authManager))// filtro q recebe json e retorna userdto mais token
                .addFilter(new JwtAuthorizationFilter(authManager, userDetailsService)) //filtro que vai receber o token e verificar de o token é valido
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)// config para tratar os erros de caeso negado
                .authenticationEntryPoint(unauthorizedHandler)//config para retornar os erros de quando não esta autorizado
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// desligando a criação de cookies na sessão



    }


    //CRIANDO OS USUARIOS:
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // responsavel por criptogravar as senhas de maneira segura
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
        /*
        auth
                .inMemoryAuthentication().passwordEncoder(encoder)
                    .withUser("user").password(encoder.encode("123")).roles("USER")
                    .and()
                    .withUser("admin").password(encoder.encode("taurus.")).roles("USER", "ADMIN");

         */
    }
}
