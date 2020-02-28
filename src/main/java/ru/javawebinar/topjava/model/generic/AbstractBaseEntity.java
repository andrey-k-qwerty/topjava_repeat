package ru.javawebinar.topjava.model.generic;

import java.util.Objects;

public abstract class AbstractBaseEntity<I extends Number> {
    public static final int START_SEQ = 100000;

    protected I id;

    public AbstractBaseEntity() {
    }

    protected AbstractBaseEntity(I id) {
        this.id = id;
    }

    public void setId(I id) {
        this.id = id;
    }

    public I getId() {
        return id;
    }

    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBaseEntity<?> that = (AbstractBaseEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}