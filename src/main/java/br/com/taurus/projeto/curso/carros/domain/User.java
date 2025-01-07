package br.com.taurus.projeto.curso.carros.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity @Data
@Table(name="`user`") // user é uma palavra reservada então deve ser escrito dessa maneira. Não é bom criar a tabela pelo postgresql pq la é necessario criar usando "user" como nome e isso atrabalha a classe identificar qual tabela ela pertence
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String login;
    private String senha;
    private String email;

    @ManyToMany(fetch =  FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn (name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;


    public static void main (String[] args){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123"));//esta gerando uma senha diferente todas as vezes e não reconhece todas as senhas. rever isso
        // 123: $2a$10$epKo8PwP1LP2GGVorIWgRu/Wuotbr4fRDILL2dfjwHAT/cajIpEi.
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

