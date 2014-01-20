package com.kkey.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author astarovoyt
 *
 */
@javax.persistence.Entity
@Table(name = "entity")
public class EntityObject
{
    @Id
    @GeneratedValue
    private long id;

    private String someValue;

    public long getId()
    {
        return id;
    }

    public String getSomeValue()
    {
        return someValue;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setSomeValue(String someValue)
    {
        this.someValue = someValue;
    }
}
