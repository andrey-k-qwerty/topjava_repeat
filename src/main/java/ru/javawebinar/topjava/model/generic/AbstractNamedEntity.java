package ru.javawebinar.topjava.model.generic;

import org.springframework.jdbc.core.RowCallbackHandler;

public abstract class AbstractNamedEntity<I extends Number> extends AbstractBaseEntity<I> {

    protected String name;
    public AbstractNamedEntity() {
    }
    protected AbstractNamedEntity(I id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return super.toString() + '(' + name + ')';
    }
}