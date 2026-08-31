package checklist.proto.controller;


import checklist.proto.model.Task;
import checklist.proto.service.ChecklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final ChecklistService taskService;


    @Autowired
    TaskController(ChecklistService taskService){
        this.taskService = taskService;
    }


    @GetMapping
    public List<Task> findAll(){
        List<Task> tasks = taskService.findAllSorted();
        return tasks;
    };

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id){
        return taskService.findById(id);
    };


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task){

        return taskService.saveTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTaskForm(@PathVariable("id") Long id, @RequestBody Task task){
        Task newTask = taskService.findById(id);
        newTask.setTitle(task.getTitle());
        newTask.setComment(task.getComment());
        newTask.setDone(task.getIsDone());
        return taskService.saveTask(newTask);

    }


    @PatchMapping("/{id}/toggle")
    public void markAsDone(@PathVariable Long id){

        taskService.markAsDone(taskService.findById(id));
    };
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskForm(@PathVariable("id") Long id){
        taskService.deleteById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllTasks(){
        taskService.deleteAllById();

    }

}
