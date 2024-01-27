package org.molodowitch.cs90.todos.services;

import org.molodowitch.cs90.todos.model.BaseEntity;
import org.molodowitch.cs90.todos.model.TodoItem;
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
    public List<TodoList> getAllLists() {
        LOG.debug("Retrieving all to-do lists");
        return BaseEntity.convertIterableToList(listRepository.findAll());
    }

    @Override
    public Optional<TodoList> getList(Long listId) {
        LOG.debug("Retrieving to-do list {}", listId);
        return listRepository.findById(listId);
    }

    @Override
    public TodoList saveList(TodoList list) {
        LOG.debug("Saving to-do list {} with name {}", list.getId(), list.getName());
        return listRepository.save(list);
    }

    @Override
    public TodoItem editItem(TodoItem item) {
        LOG.debug("Updating to-do item {}", item.getId());
        return itemRepository.save(item);
    }

    @Override
    public boolean deleteItem(Long itemId) {
        LOG.info("Deleting to-do item {}", itemId);
        if (itemRepository.existsById(itemId)) {
            LOG.debug("To-do item {} exists", itemId);
            itemRepository.deleteById(itemId);
            return true;
        }
        else {
            LOG.warn("Attempted to delete non-existent to-do item {}", itemId);
            return false;
        }
    }
}
