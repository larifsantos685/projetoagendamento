package com.example.task_manager.service;

import java.time.LocalDate;
// import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import org.springframework.web.context.annotation.ApplicationScope;

import com.example.task_manager.classes.Task;
import com.example.task_manager.repositories.TaskRepositories;

// O que estiver comentado, era a versao antiga, ou seja, tudo feito localmente por session, agora entendi!

@Service
public class TaskService {
    @Autowired
    private TaskRepositories taskRepo;

    // private static List<Task> tasks = new ArrayList<>();
    // private static int currentId = 1;

    public TaskService() {}

    public void add(Task task){
        // task.setId(currentId++); 
        task.setDataCriada(LocalDate.now());
        // tasks.add(task);

        taskRepo.save(task);
    }

    public List<Task> getAll() { 
        // return tasks; 
        return taskRepo.findAll();
    }

    public Task get(Task task){
        // for (Task t : tasks) {
        //     if(t.equals(task)) {
        //         return t;
        //     }
        // }
        // return null;

        Optional<Task> _task = taskRepo.findById(task.getId());
        return _task.orElse(null);
    }

    public Task get(Long id){
        return get(new Task(id));
    }

    // @Override
    // public String toString() {
    //     return tasks.toString();
    // }

    public boolean update(Task updatedTask) {
        // for (int i = 0; i < tasks.size(); i++) {
        //     Task current = tasks.get(i);
        //     if (current.equals(updatedTask)) {
        //         tasks.set(i, updatedTask);
        //         return true;
        //     }
        // }
        // return false;

        if(taskRepo.existsById(updatedTask.getId())){
            // FEITO PARA MODIFICAR PELO ID DO JSON COMPLETO
            taskRepo.save(updatedTask);
            return true;
        }
        return false;
    }

    public boolean remove(Long id) {
        // return tasks.remove(new Task(id));

        Optional<Task> _task = taskRepo.findById(id);
        if(_task.isPresent()){
            taskRepo.delete(_task.get());
            return true;
        }
        return false;
    }
}
