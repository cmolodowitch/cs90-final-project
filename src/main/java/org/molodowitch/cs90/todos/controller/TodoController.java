package org.molodowitch.cs90.todos.controller;

import org.molodowitch.cs90.todos.model.TodoList;
import org.molodowitch.cs90.todos.services.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller providing endpoints for a To-do application back end.
 * Note that this was built as a demo to investigate AWS Serverless.  It's not
 * intended for true production use, as it skips some potential error modes
 * and doesn't provide the full API that would be required for an actual
 * To-do application.
 */
@RestController
@EnableWebMvc
public class TodoController {

    private static final Logger LOG = LoggerFactory.getLogger(TodoController.class);

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * Ping endpoint created by Maven archetype, left in to serve as database-independent
     * test endpoint.
     * Does no actual logic, simply returns a response when reached.
     *
     * @return Map matching "pong" to "Hello, World!"
     */
    @GetMapping(path = "/ping")
    public Map<String, String> ping() {
        Map<String, String> pong = new HashMap<>();
        pong.put("pong", "Hello, World!");
        return pong;
    }

    /**
     * Gets all saved to-do lists, including the items on the lists.
     *
     * @return List of TodoList objects, each with all to-do items loaded
     */
    @GetMapping(path = "/lists")
    public List<TodoList> getTodoLists() {
        return todoService.getAllLists();
    }

    /**
     * Gets the to-do list with the specified persistence index, if any.
     *
     * @param listId persistence index of the to-do list to retrieve
     * @return Optional containing the TodoList with loaded to-do items for the list with the
     *         specified persistence index, or an empty Optional if there's no such list
     */
    @GetMapping(path = "/lists/{id}")
    public ResponseEntity<TodoList> getTodoList(@PathVariable("id") long listId) {
        return ResponseEntity.of(todoService.getList(listId));
    }

    /**
     * Creates a new to-do list entry based on the specified data.
     *
     * @param list TodoList object parsed from the request containing the new list information
     * @return saved TodoList data, including persistence index
     */
    @PostMapping(path = "/lists")
    public TodoList createNewList(@RequestBody TodoList list) {
        return todoService.saveList(list);
    }

    /**
     * Modifies the specified to-do list entry, updating it with the data in the TodoList
     * parsed from the request.  Note that this will overwrite any previous values, including
     * to-do list items.  Use this method to modify existing lists, including adding or
     * editing to-do items.
     *
     * @param list   TodoList object containing to-do list data parsed from the request body
     * @param listId persistence index of the to-do list to update
     * @return updated saved TodoList data
     */
    @PutMapping(path = "/lists/{id}")
    public ResponseEntity<TodoList> updateList(@RequestBody TodoList list, @PathVariable("id") long listId) {
        if (todoService.doesListExist(listId)) {
            list.setId(listId);
            return ResponseEntity.ok(todoService.saveList(list));
        }
        else {
            LOG.error("No list found to update with index {}", listId);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes the specified to-do list, along with any items on the list.
     *
     * @param listId persistence index of the to-do list to delete
     * @return 204 No Content response if list is successfully deleted,
     *         or 404 Not Found if there's no to-do list with the specified listId
     */
    @DeleteMapping(path = "/lists/{listId}")
    public ResponseEntity<String> deleteList(@PathVariable("listId") long listId) {
        if (todoService.doesListExist(listId)) {
            todoService.deleteList(listId);
            return ResponseEntity.noContent().build();
        }
        else {
            String message = "Couldn't delete to-do list " + listId + ", list not found";
            LOG.error(message);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        }
    }
}
