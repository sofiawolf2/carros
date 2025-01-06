package br.com.taurus.projeto.curso.carros.service;

import br.com.taurus.projeto.curso.carros.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//imp: implementação
@Service (value = "userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // não usa mais o BCryptPasswordEncoder pois agora a senha ja é colocada criptografada direto no banco
        br.com.taurus.projeto.curso.carros.domain.User user = userRepository.findByLogin(username);
        // temos 2 user sendo trabalhados: o que criamos e o do spring. para diferenciar mudamos a escrita para essa em que define o pacote
        if (user == null){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return User.withUsername(username).password(user.getSenha()).roles("USER").build();

    }
}
