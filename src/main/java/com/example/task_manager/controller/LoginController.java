package com.example.task_manager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.task_manager.classes.UserLogin;

import jakarta.servlet.http.HttpSession;

@RestController
public class LoginController {

    //Usuário fixo para teste 
    private final String USERNAME = "admin"; 
    private final String PASSWORD = "123"; 

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin login, HttpSession session) {
        if(USERNAME.equals(login.getUsername()) && PASSWORD.equals(login.getPassword())) {
            session.setAttribute("user", login.getUsername()); 
            return ResponseEntity.ok("Login efetuado com sucesso");
        } else {
            return ResponseEntity.status(401).body("Usuário ou senha inválidos");
        }
    }

}
