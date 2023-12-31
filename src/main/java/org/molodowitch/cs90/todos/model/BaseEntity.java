package org.molodowitch.cs90.todos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all entities, using a Long value as the ID
 * and a generation strategy of IDENTITY.
 *
 * @author cm112
 */
@SuppressWarnings("unused")
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Transient
    @JsonIgnore
    public boolean isSaved() {
        return id != null;
    }

    public static <E> List<E> convertIterableToList(Iterable<E> iterable) {
        if (iterable != null) {
            var list = new ArrayList<E>();
            iterable.forEach(list::add);
            return list;
        }
        else {
            return null;
        }
    }
}
