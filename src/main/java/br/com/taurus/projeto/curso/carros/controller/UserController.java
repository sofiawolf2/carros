package br.com.taurus.projeto.curso.carros.controller;

import br.com.taurus.projeto.curso.carros.domain.User;
import br.com.taurus.projeto.curso.carros.domain.dto.UserDTO;
import br.com.taurus.projeto.curso.carros.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController @RequestMapping ("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity get(){
        return ResponseEntity.ok(userService.getUsers());
    }
    @GetMapping("/info")
    public UserDTO userInfo(@AuthenticationPrincipal User user) {
        return UserDTO.create(user);
        /*
        outra forma de recuperar o usuário logado:
        UserDetails userDetails = JwtUtil.getUserDetails;
        */
    }
}
