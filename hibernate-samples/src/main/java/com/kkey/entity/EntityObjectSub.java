package com.kkey.entity;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author astarovoyt
 *
 */
@javax.persistence.Entity
@Table(name = "entity")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@DiscriminatorValue("S1")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discr", discriminatorType = DiscriminatorType.STRING)
public class EntityObjectSub extends EntityObject
{
    public EntityObjectSub()
    {

    }

    public EntityObjectSub(String value)
    {
        super(value);
    }
}
