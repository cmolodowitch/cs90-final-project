package org.molodowitch.cs90.todos.repositories;

import org.molodowitch.cs90.todos.model.TodoList;
import org.springframework.data.repository.CrudRepository;

public interface TodoListRepository extends CrudRepository<TodoList, Long> {
}
