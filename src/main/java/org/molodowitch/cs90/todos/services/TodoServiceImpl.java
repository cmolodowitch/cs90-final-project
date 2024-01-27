package org.molodowitch.cs90.todos.services;

import org.molodowitch.cs90.todos.model.BaseEntity;
import org.molodowitch.cs90.todos.model.TodoList;
import org.molodowitch.cs90.todos.repositories.TodoItemRepository;
import org.molodowitch.cs90.todos.repositories.TodoListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private static final Logger LOG = LoggerFactory.getLogger(TodoServiceImpl.class);

    private final TodoListRepository listRepository;
    private final TodoItemRepository itemRepository;

    @Autowired
    public TodoServiceImpl(TodoListRepository listRepository, TodoItemRepository itemRepository) {
        this.listRepository = listRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public boolean doesListExist(long listId) {
        LOG.debug("Checking if list {} exists", listId);
        return listRepository.existsById(listId);
    }

    @Override
    public List<TodoList> getAllLists() {
        LOG.debug("Retrieving all to-do lists");
        return BaseEntity.convertIterableToList(listRepository.findAll());
    }

    @Override
    public Optional<TodoList> getList(long listId) {
        LOG.debug("Retrieving to-do list {}", listId);
        return listRepository.findById(listId);
    }

    @Override
    public TodoList saveList(TodoList list) {
        LOG.debug("Saving to-do list {} with name {}", list.getId(), list.getName());
        return listRepository.save(list);
    }

    @Override
    public void deleteList(long listId) {
        LOG.info("Deleting to-do list {}", listId);
        listRepository.deleteById(listId);
    }

    @Override
    public boolean doesItemExist(long itemId) {
        LOG.debug("Checking if item {} exists", itemId);
        return itemRepository.existsById(itemId);
    }

    @Override
    public void deleteItem(long itemId) {
        LOG.info("Deleting to-do item {}", itemId);
        itemRepository.deleteById(itemId);
    }
}
