package org.molodowitch.cs90.todos.services;

import org.molodowitch.cs90.todos.BaseEntity;
import org.molodowitch.cs90.todos.model.TodoItem;
import org.molodowitch.cs90.todos.model.TodoList;
import org.molodowitch.cs90.todos.repositories.TodoItemRepository;
import org.molodowitch.cs90.todos.repositories.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoListRepository listRepository;
    private final TodoItemRepository itemRepository;

    @Autowired
    public TodoServiceImpl(TodoListRepository listRepository, TodoItemRepository itemRepository) {
        this.listRepository = listRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public List<TodoList> getAllLists() {
        return BaseEntity.convertIterableToList(listRepository.findAll());
    }

    @Override
    public Optional<TodoList> getList(Long listId) {
        return listRepository.findById(listId);
    }

    @Override
    public TodoList saveList(TodoList list) {
        return listRepository.save(list);
    }

    @Override
    public TodoItem editItem(TodoItem item) {
        return itemRepository.save(item);
    }

    @Override
    public boolean deleteItem(Long itemId) {
        if (itemRepository.existsById(itemId)) {
            itemRepository.deleteById(itemId);
            return true;
        }
        else {
            return false;
        }
    }
}
