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
import java.util.Optional;

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
            Optional<Task> task = taskService.findById(Long.parseLong(id));

            if (task.isPresent()) {
                task.get().setTitle(title);
                task.get().setDescription(description);
                task.get().setDtUpdate(now);

                logger.info("Atualizando a tarefa ao Banco");
                refTask = taskService.save(task.get());
            }else{
                logger.warn(String.format("Não foi identificado a tarefa [%s]", title));
                throw new BadRequestInfoException(String.format("Não foi identificado a tarefa [%s]", title));
            }
        }catch (Exception ex){
            logger.error(String.format("Problema ao atualizar Tarefa [%s]", title), ex);
            throw new BadRequestInfoException(String.format("Problema ao atualizar a Tarefa [%s]", title));
        }
        return ResponseEntity.ok().body(
                refTask
        );
    }

    @PutMapping("/status")
    public ResponseEntity<Task> updateStatus(@RequestParam("id") String id, @RequestParam("status") String status){

        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.ofOffset("UTC", ZoneOffset.of("-00:00"))).toInstant());

        Task refTask;
        try{

            Optional<Task> task = taskService.findById(Long.parseLong(id));

            if (task.isPresent()) {
                task.get().setDtUpdate(now);

                logger.info("Atualizando o status da tarefa ao Banco");
                if (status.equals("true")){
                    task.get().setStatus(Task.Status.COMPLETED);
                    task.get().setDtConclusion(now);
                }else{
                    task.get().setStatus(Task.Status.RUN);
                    task.get().setDtConclusion(null);
                }

                refTask = taskService.save(task.get());
            }else{
                logger.warn(String.format("Não foi identificado a tarefa [%d]", id));
                throw new BadRequestInfoException(String.format("Não foi identificado a tarefa [%d]", id));
            }
        }catch (Exception ex){
            logger.error(String.format("Problema ao atualizar status da Tarefa [%d]", id), ex);
            throw new BadRequestInfoException(String.format("Problema ao atualizar status da Tarefa [%d]", id));
        }
        return ResponseEntity.ok().body(
                refTask
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> delete(@PathVariable String id){
        try{
            Optional<Task> task = taskService.findById(Long.parseLong(id));

            if (task.isPresent()) {
                taskService.delete(task.get());
            }else{
                logger.warn(String.format("Não foi identificado a tarefa [%d]", id));
                throw new BadRequestInfoException(String.format("Não foi identificado a tarefa [%d]", id));
            }
        }catch (Exception ex){
            logger.error(String.format("Problema para deletar a Tarefa [%d]", id), ex);
            throw new BadRequestInfoException(String.format("Problema para deletar a Tarefa [%d]", id));
        }
        return ResponseEntity.ok().build();
    }
}
