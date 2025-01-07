package br.com.taurus.projeto.curso.carros.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration @EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // habilita a segurança por metodo
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired @Qualifier ("userDetailsService") // usado para definir qual classe estamos usando em vez de definitir UserDetailsServiceImp
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().csrf().disable(); //retira a requisição de autenticação de chamadas
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
