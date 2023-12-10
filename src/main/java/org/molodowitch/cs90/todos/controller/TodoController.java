package org.molodowitch.cs90.todos.controller;

import org.molodowitch.cs90.todos.model.TodoItem;
import org.molodowitch.cs90.todos.model.TodoList;
import org.molodowitch.cs90.todos.services.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@EnableWebMvc
public class TodoController {

    private static final Logger LOG = LoggerFactory.getLogger(TodoController.class);

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping(path = "/ping")
    public Map<String, String> ping() {
        Map<String, String> pong = new HashMap<>();
        pong.put("pong", "Hello, World!");
        return pong;
    }

    @GetMapping(path = "/lists")
    public List<TodoList> getTodoLists() {
        return todoService.getAllLists();
    }

    @GetMapping(path = "/lists/{id}")
    public ResponseEntity<TodoList> getTodoList(@PathVariable("id") long listId) {
        return ResponseEntity.of(todoService.getList(listId));
    }

    @PostMapping(path = "/lists")
    public TodoList createNewList(TodoList list) {
        return todoService.saveList(list);
    }

    @PutMapping(path = "/lists/{id}")
    public TodoList updateList(@RequestBody TodoList list, @PathVariable("id") long listId) {
        return todoService.saveList(list);
    }

    @PutMapping(path = "/items/{itemId}")
    public TodoItem updateItem(@RequestBody TodoItem item, @PathVariable("itemId") long itemId) {
        return todoService.editItem(item);
    }

    @DeleteMapping(path = "/items/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") long itemId) {
        var exists = todoService.deleteItem(itemId);
        if (exists) {
            return ResponseEntity.ok().build();
        }
        else {
            String message = "Todo item " + itemId + " not found";
            LOG.error(message);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        }
    }
}
