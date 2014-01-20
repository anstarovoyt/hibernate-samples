package com.kkey.entity;

import javax.persistence.DiscriminatorValue;
/**
 * @author astarovoyt
 *
 */
@javax.persistence.Entity
@DiscriminatorValue("S2")
public class EntityObjectSubSub extends EntityObjectSub
{
    public EntityObjectSubSub()
    {

    }

    public EntityObjectSubSub(String value)
    {
        super(value);
    }
}
