package com.example.task_manager.service;


import java.util.ArrayList;
import java.util.List;

import com.example.task_manager.classes.User;

public class UserService {
    private static final List<User> users = new ArrayList<>();
    private static int currentId = 1; 

public void add(User user) {
    user.setId(currentId++);
    users.add(user); 
}

public User getByUsername(String username) {
    for (User user : users) {
        if(user.getUsername().equals(username)){
            return user;
        }
    }
    return null; 
}
public List<User> getAll() {
    return users; 
}

public User authenticate(String username, String password){
    User user = getByUsername(username);
    if (user != null && user.getPassword().equals(password)) {
        return user; //autenticado com sucesso
    }
    return null; // falha na autenticação
}

}
