package com.example.task_manager.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task_manager.classes.Task;
import com.example.task_manager.service.TaskService;

// import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService; 

    // @GetMapping
    // public ResponseEntity<?> getAll(HttpSession session) {
    //     if (session.getAttribute("user") == null) {
    //         return ResponseEntity.status(401).body("Usuário não autenticado.");
    //     }
    //     return ResponseEntity.ok(taskService.getAll());
    // }
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(taskService.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Task _task = taskService.get(id);
        if(_task != null){
            return ResponseEntity.ok(_task);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Task task) {
        taskService.add(task);
        return ResponseEntity.ok(task);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Task updatedTask) {
//         if (session.getAttribute("user") == null) {
//             return ResponseEntity.status(401).body("Usuário não autenticado.");
//         }
//         //Garante que o id da tarefa atualizada seja o mesmo do path
//         updatedTask.setId(id);
//         boolean updated = taskService.update(updatedTask);
//        if (updated) {
//     return ResponseEntity.ok(updatedTask);
// } else {
//     return ResponseEntity.notFound().build();
// }
        if(taskService.update(updatedTask)){
            return ResponseEntity.ok(updatedTask);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        // if (session.getAttribute("user") == null) {
        //     return ResponseEntity.status(401).body("Usuário não autenticado.");
        // }
        // boolean removed = taskService.remove(id);
        // if (removed) {
        //     return ResponseEntity.ok("Tarefa removida com sucesso.");
        // } else {
        //     return ResponseEntity.notFound().build();
        // }
        if(taskService.remove(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
