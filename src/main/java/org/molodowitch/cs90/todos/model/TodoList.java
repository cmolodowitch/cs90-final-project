package org.molodowitch.cs90.todos.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.molodowitch.cs90.todos.BaseEntity;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TodoList")
@AttributeOverride(name = "id", column = @Column(name = "TodoListId"))
public class TodoList extends BaseEntity {

    @Column(name = "Name")
    private String name;

    @JsonManagedReference("items")
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
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
