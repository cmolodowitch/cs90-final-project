package org.molodowitch.cs90.todos.services;

import org.molodowitch.cs90.todos.model.TodoList;

import java.util.List;
import java.util.Optional;

public interface TodoService {

    /**
     * Checks if there's a saved to-do list with the specified persistence index.
     *
     * @param listId persistence index for a to-do list
     * @return true if there's a saved to-do list with the specified persistence index,
     *         false if not
     */
    boolean doesListExist(long listId);

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
    Optional<TodoList> getList(long listId);

    /**
     * Saves the specified to-do list, returning the saved entity.
     *
     * @param list TodoList containing data to be saved.
     * @return saved TodoList entity
     */
    TodoList saveList(TodoList list);

    /**
     * Deletes the to-do list with the specified persistence index, along with any items
     * on the list.
     * Does nothing if there's no such entry.
     *
     * @param listId persistence index of the to-do list to delete
     */
    void deleteList(long listId);

    /**
     * Checks if there's a saved to-do item with the specified persistence index.
     *
     * @param itemId persistence index for a to-do item
     * @return true if there's a saved to-do item with the specified persistence index,
     *         false if not
     */
    boolean doesItemExist(long itemId);

    /**
     * Deletes the to-do item with the specified persistence index.
     * Does nothing if there's no such entry.
     *
     * @param itemId persistence index of the to-do item to delete
     * @return true if a to-do item was deleted, false if there's no item with the
     *         specified persistence index
     */
    void deleteItem(long itemId);
}
