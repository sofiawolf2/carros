package br.com.taurus.projeto.curso.carros.service;

import br.com.taurus.projeto.curso.carros.domain.User;
import br.com.taurus.projeto.curso.carros.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//imp: implementação
@Service (value = "userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // não usa mais o BCryptPasswordEncoder pois agora a senha ja é colocada criptografada direto no banco
        User user = userRepository.findByLogin(username);
        if (user == null){
            throw new UsernameNotFoundException("Usuário não encontrado"); // Não vejo a mensagem
        }
        return user; // por user (da classe User) implementar UserDetails pode retornar so ele

    }
}
