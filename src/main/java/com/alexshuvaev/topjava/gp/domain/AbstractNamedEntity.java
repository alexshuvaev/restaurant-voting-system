package com.alexshuvaev.topjava.gp.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Simple JavaBean domain object adds a name property to <code>AbstractBaseEntity</code>. Used as a base class for objects
 * needing these properties.
 *
 */
@MappedSuperclass
public abstract class AbstractNamedEntity extends AbstractBaseEntity{
    @Column(name = "name", nullable = false)
    private String name;

    protected AbstractNamedEntity() {
    }

    protected AbstractNamedEntity(Integer id, String name) {
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
        return super.toString() + ", name=" + name;
    }
}
