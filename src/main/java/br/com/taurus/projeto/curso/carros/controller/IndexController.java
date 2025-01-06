package br.com.taurus.projeto.curso.carros.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/")
public class IndexController {
    @GetMapping()
    public String get(){return "API dos carros";}

    @GetMapping("/userinfo")
    public UserDetails userinfo (@AuthenticationPrincipal UserDetails user){
        return user;
    }
}
