package com.kkey.entity;

import java.util.concurrent.Callable;

import org.hibernate.Session;

import com.kkey.App;
import com.kkey.HibernateUtils;

/**
 * @author astarovoyt
 *
 */
public final class EntityDao
{
    public static long createEntity()
    {
        return HibernateUtils.doCallInTransaction(new Callable<Long>()
        {
            public Long call() throws Exception
            {
                Session session = HibernateUtils.getCurrentSession();
                EntityObjectSub object = new EntityObjectSub("Our very first event!");
                session.save(object);

                return object.getId();
            }
        });
    }

    public static long updateEntity(final long id)
    {
        return HibernateUtils.doCallInTransaction(new Callable<Long>()
        {
            public Long call() throws Exception
            {
                Session currentSession = HibernateUtils.getCurrentSession();
                EntityObject loaded = (EntityObject)currentSession.load(EntityObjectSub.class, id);

                loaded.setSomeValue(String.valueOf(App.rnd.nextInt()));
                currentSession.saveOrUpdate(loaded);
                currentSession.flush();

                return loaded.getId();
            }
        });
    }
}
