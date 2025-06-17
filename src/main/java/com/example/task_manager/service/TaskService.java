package com.example.task_manager.service;

import java.time.LocalDate;
// import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.task_manager.classes.Task;
import com.example.task_manager.repositories.TaskRepositories;

@Service
public class TaskService {
    @Autowired
    private TaskRepositories taskRepo;

    // private final List<Task> tasks = new ArrayList<>();
    // private int currentId = 1;

    public TaskService() {}

    public void add(Task task){
        // task.setId(currentId++); 
        task.setDataCriada(LocalDate.now());
        // tasks.add(task);
        //ao inves de adicionar na lista local (linha acima) adicionara no banco
        taskRepo.save(task); 

    }

    public List<Task> getAll() { 
        // return tasks; deixa de retornar a lista local e retorna os valores da tablea do banco

        return taskRepo.findAll();

    }

    public Task get(Integer id) {
        // procurar na lista
        // for (Task t : tasks) {
        //     if(t.getId().equals(id)) {
        //         return t;
        //     }
        // }
        // return null;

        Optional<Task> _task = taskRepo.findById(id);// pra procurar pelo id, precisa que o tipo do id seja Integer, nao pode ser do tipo int ,_,
        return _task.orElse(null);
    }

    // public Task get(Integer id){
    //     return get(new Task(id));
    // }

    @Override
    public String toString() {
        return taskRepo.toString();
    }

    public boolean update(Task updatedTask) {
        // for (int i = 0; i < tasks.size(); i++) {
        //     Task current = tasks.get(i);
        //     if (current.getId().equals(updatedTask.getId())) {
        //           updatedTask.setDataCriada(current.getDataCriada());  // ✅ Mantém a data original
        //         tasks.set(i, updatedTask);
        //         return true;
        //     }
        // }
        if(taskRepo.existsById(updatedTask.getId())){
            // FEITO PARA MODIFICAR PELO ID DO JSON COMPLETO
            taskRepo.save(updatedTask);
            return true;
        }
        return false;
    }

    public boolean remove(Integer id) {
        // return task.remove(new Task(id));
        Optional<Task> _task = taskRepo.findById(id);
        if(_task.isPresent()){
            taskRepo.delete(_task.get());
            return true;
        }
        return false;
    }
}

