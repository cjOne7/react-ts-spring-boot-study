package eu.profinit.todo.controllers;

import eu.profinit.todo.models.Todo;
import eu.profinit.todo.repository.TodoRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class TodoController {
    private final TodoRepo todoRepo;
    private final Logger log = LoggerFactory.getLogger(TodoController.class);

    public TodoController(TodoRepo todoRepo) {
        this.todoRepo = todoRepo;
    }

    @ResponseBody
    @GetMapping("/todos")
    public List<Todo> todos() {
        return todoRepo.findAll();
    }

    @ResponseBody
    @PostMapping("/todo")
    public ResponseEntity<Todo> addTodo(@Valid @RequestBody Todo todo) {
        log.info("Request to create todo: {}", todo);
        try {
            Todo result = todoRepo.save(todo);
            return ResponseEntity.created(new URI("/todo" + result.getId())).body(result);
        } catch (URISyntaxException e) {
            log.error("URI error while todo creation: {}\n {}", todo, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ResponseBody
    @CrossOrigin
    @DeleteMapping("/todo/{id}")
    public ResponseEntity<Todo> deleteTodo(@PathVariable Long id) {
        log.info("Request to delete todo with id: {}", id);
        todoRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @CrossOrigin
    @PutMapping("/todo/{id}")
    public ResponseEntity<Todo> updateTodo(@Valid @PathVariable Todo todo) {
        log.info("Request to update todo: {}", todo);
        Todo result = todoRepo.save(todo);
        return ResponseEntity.ok().body(result);
    }
}
