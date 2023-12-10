package org.molodowitch.cs90.todos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.molodowitch.cs90.todos.BaseEntity;

import java.util.Objects;

@Entity
@Table(name = "TodoItem")
@AttributeOverride(name = "id", column = @Column(name = "TodoItemId"))
public class TodoItem extends BaseEntity {

    @JsonBackReference("items")
    @ManyToOne(optional = false)
    @JoinColumn(name = "TodoListId", nullable = false)
    private TodoList list;

    @Column(name = "Item", nullable = false, length = 1024)
    private String item;

    @Column(name = "Completed", nullable = false)
    private boolean completed;

    public TodoList getList() {
        return list;
    }

    public void setList(TodoList list) {
        this.list = list;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoItem todoItem = (TodoItem) o;
        return Objects.equals(list, todoItem.list) && Objects.equals(item, todoItem.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, item);
    }
}
