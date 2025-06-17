package com.example.task_manager.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task_manager.classes.User;
import com.example.task_manager.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService = new UserService();

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        userService.add(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody User user, HttpSession session) {
    User authenticatedUser = userService.authenticate(user.getUsername(), user.getPassword()); 
        if (authenticatedUser != null) {
            session.setAttribute("user", authenticatedUser); //Guarda o usuário na sessao
            return ResponseEntity.ok("Login Realizado com sucesso");
        } else {
            return ResponseEntity.status(401).body("Usuário ou senha inválido");
        }
}

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); //Encerra a sessao
        return ResponseEntity.ok("Logout realizado com sucesso");
    }

}
