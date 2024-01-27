package org.molodowitch.cs90.todos.repositories;

import org.molodowitch.cs90.todos.model.TodoItem;
import org.springframework.data.repository.CrudRepository;

public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {
}
