package org.molodowitch.cs90.todos.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

/**
 * Holds a "to-do" list containing one or more items.
 * Each list is identified by a unique name.
 */
@SuppressWarnings("unused")
@Entity
@Table(name = "todo_list")
@AttributeOverride(name = "id", column = @Column(name = "todo_list_id"))
public class TodoList extends BaseEntity {

    /**
     * Name of the to-do list, must be unique.
     */
    @Column(name = "name")
    private String name;

    /**
     * Items on the to-do list.
     */
    @JsonManagedReference("items")
    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodoItem> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TodoItem> getItems() {
        return items;
    }

    public void setItems(List<TodoItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoList todoList = (TodoList) o;
        return Objects.equals(name, todoList.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}