package org.molodowitch.cs90.todos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * Holds a single "to-do" item in a list.
 * Includes both the text of the item and the current completion status of the item.
 */
@SuppressWarnings("unused")
@Entity
@Table(name = "todo_item")
@AttributeOverride(name = "id", column = @Column(name = "todo_item_id"))
public class TodoItem extends BaseEntity {

    /**
     * Parent list containing this to-do item.
     */
    @JsonBackReference("items")
    @ManyToOne(optional = false)
    @JoinColumn(name = "todo_list_id", nullable = false)
    private TodoList list;

    /**
     * Text of the to-do item.
     */
    @Column(name = "task", nullable = false, length = 1024)
    private String task;

    /**
     * Flag indicating whether the item has been completed or not.
     */
    @Column(name = "completed", nullable = false)
    private boolean completed;

    public TodoList getList() {
        return list;
    }

    public void setList(TodoList list) {
        this.list = list;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String item) {
        this.task = item;
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
        return Objects.equals(list, todoItem.list) && Objects.equals(task, todoItem.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, task);
    }
}
