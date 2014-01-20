package com.kkey.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author astarovoyt
 *
 */
@MappedSuperclass
public class EntityObject
{
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String someValue;

    @Column
    private String someValue2;

    public EntityObject()
    {

    }

    public EntityObject(String value)
    {
        someValue = value;
    }

    public long getId()
    {
        return id;
    }

    public String getSomeValue()
    {
        return someValue;
    }

    public String getSomeValue2()
    {
        return someValue2;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setSomeValue(String someValue)
    {
        this.someValue = someValue;
    }

    public void setSomeValue2(String someValue2)
    {
        this.someValue2 = someValue2;
    }
}
