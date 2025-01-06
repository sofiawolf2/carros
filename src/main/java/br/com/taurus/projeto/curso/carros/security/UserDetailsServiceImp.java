package br.com.taurus.projeto.curso.carros.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//imp: implementação
@Service (value = "userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (username.equals("user")){
            return User.withUsername(username).password(encoder.encode("123")).roles("USER").build();
        }else if (username.equals("admin")){
            return User.withUsername(username).password(encoder.encode("taurus.")).roles("USER", "ADMIN").build();
        }// esta definindo qual ficha de usuário deve ser retornada dependendo do tipo de usuário
        throw new UsernameNotFoundException("Usuário não encontrado");
    }
}
