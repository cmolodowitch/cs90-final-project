package org.molodowitch.cs90.todos.services;

import org.molodowitch.cs90.todos.model.TodoItem;
import org.molodowitch.cs90.todos.model.TodoList;

import java.util.List;
import java.util.Optional;

public interface TodoService {

    List<TodoList> getAllLists();

    Optional<TodoList> getList(Long listId);

    TodoList saveList(TodoList list);

    TodoItem editItem(TodoItem item);

    boolean deleteItem(Long itemId);
}
