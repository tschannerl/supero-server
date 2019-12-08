package com.tschannerl.superoserver.controllers;


import com.tschannerl.superoserver.errors.BadRequestInfoException;
import com.tschannerl.superoserver.models.Task;
import com.tschannerl.superoserver.services.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private static final Logger logger = LogManager.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @GetMapping()
    public ResponseEntity<List<Task>> findAllTasks(){
        try{
            logger.info("Iniciando a busca de todos as tarefas");
            List<Task> clientList = taskService.findAllTask();
            return ResponseEntity.ok().body(clientList);
        }catch (Exception e){
            logger.error("Erro ao listar todos as tarefas",e);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestParam("title") String title, @RequestParam("description") String description){
        Task refTask;
        try{
            logger.info("Adicionando o tarefa ao Banco");
            refTask = taskService.save(new Task(title,description));
        }catch (Exception ex){
            logger.error(String.format("Problema ao inserir Tarefa [%s]", title), ex);
            throw new BadRequestInfoException(String.format("Problema ao inserir a Tarefa [%s]", title));
        }
        return ResponseEntity.ok().body(
                refTask
        );
    }

    @PutMapping
    public ResponseEntity<Task> update(@RequestParam("id") String id, @RequestParam("title") String title, @RequestParam("description") String description){

        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.ofOffset("UTC", ZoneOffset.of("-00:00"))).toInstant());
        Task refTask;
        try{
            logger.info("Atualizando a tarefa ao Banco");
            refTask = taskService.save(new Task(Long.parseLong(id), title, description, now));
        }catch (Exception ex){
            logger.error(String.format("Problema ao atualizar Tarefa [%s]", title), ex);
            throw new BadRequestInfoException(String.format("Problema ao atualizar a Tarefa [%s]", title));
        }
        return ResponseEntity.ok().body(
                refTask
        );
    }
}
