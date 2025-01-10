package br.com.taurus.projeto.curso.carros.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class JwtUtil {
    // Chave com algoritmo HS512
    // http://www.allkeysgenerator.com
    private static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";// chave randomica criada no site listado
    //essa chave vai ser usada para a criptografia

    public static Claims getClaims(String token) {
        byte[] signingKey = JwtUtil.JWT_SECRET.getBytes();

        token = token.replace("Bearer ", "");

        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token).getBody();
    }

    public static String getLogin(String token) {
        Claims claims = getClaims(token); //com as claims conseguimos recuperar informações do token
        if (!isNull(claims)) {
            return claims.getSubject();//login
        }
        return null;
    }

    public static List<GrantedAuthority> getRoles(String token) {
        Claims claims = getClaims(token);
        if (claims == null) {
            return null;
        }
        return ((List<?>) claims
                .get("rol")).stream()// converte para stream para usar a função map
                .map(authority -> new SimpleGrantedAuthority((String) authority)) //convertendo para uma lista de roles (do tipo grandteAuthority)
                .collect(Collectors.toList());
        //grantedAuthority são permissões para fazer algo
    }

    public static boolean isTokenValid(String token) {
        Claims claims = getClaims(token);// chama as claims do tioken: ou seja suas credenciais
        if (nonNull(claims)) {
            String login = claims.getSubject();//login
            Date expiration = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            return login != null && expiration != null && now.before(expiration);
            // verifica se o login não é nulo e se ainda não expirou o token
        }
        return false;
    }

    public static String createToken(UserDetails user) {
        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        byte[] signingKey = JwtUtil.JWT_SECRET.getBytes();// pegamos os bytes da chave de criptografia

        int days = 10;// o token vai durar 10 dias
        long time = days * 24 /*horas*/ * 60 /*min*/ * 60 /*seg*/ * 1000  /*milis*/;
        Date expiration = new Date(System.currentTimeMillis() + time);// criando data de validade
//        System.out.println(expiration);

        return Jwts.builder()// chamando a api do jaison web token
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setSubject(user.getUsername())
                .setExpiration(expiration)
                .claim("rol", roles)
                .compact();
    }

    public static String getAuthLogin() {
        UserDetails user = getUserDetails();
        if(user != null){
            return user.getUsername();
        }
        return null;
    }

    public static UserDetails getUserDetails(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.getPrincipal() != null){
            return (UserDetails) auth.getPrincipal();
        }
        return null;
    }
}
