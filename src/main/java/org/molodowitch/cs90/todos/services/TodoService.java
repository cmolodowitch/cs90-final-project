package org.molodowitch.cs90.todos.services;

import org.molodowitch.cs90.todos.model.TodoItem;
import org.molodowitch.cs90.todos.model.TodoList;

import java.util.List;
import java.util.Optional;

public interface TodoService {

    /**
     * Retrieves all saved to-do lists with their items.
     *
     * @return List containing all saved to-do lists with their items,
     *         or an empty List if there are no saved to-do lists
     */
    List<TodoList> getAllLists();

    /**
     * Retrieves the to-do list with the specified listId persistence index, if any.
     *
     * @param listId persistence index of the to-do list to retrieve
     * @return Optional containing the to-do list with the specified persistence index,
     *         or an empty Optional if there is no such entry
     */
    Optional<TodoList> getList(Long listId);

    /**
     * Saves the specified to-do list, returning the saved entity.
     *
     * @param list TodoList containing data to be saved.
     * @return saved TodoList entity
     */
    TodoList saveList(TodoList list);

    /**
     * Saves the changes to the specified to-do item, returning the saved entity.
     *
     * @param item TodoItem containing changes to be made.
     * @return saved TodoItem entity
     */
    TodoItem editItem(TodoItem item);

    /**
     * Deletes the to-do item with the specified persistence index.
     * Does nothing if there's no such entry.
     *
     * @param itemId persistence index of the to-do item to delete
     * @return true if a to-do item was deleted, false if there's no item with the
     *         specified persistence index
     */
    boolean deleteItem(Long itemId);
}
