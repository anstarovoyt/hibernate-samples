package com.kkey.entity;

import java.util.concurrent.Callable;

import org.hibernate.Query;
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
            @Override
            public Long call() throws Exception
            {
                Session session = HibernateUtils.getCurrentSession();
                EntityObjectSub object = new EntityObjectSub("Our very first event!");
                session.save(object);

                return object.getId();
            }
        });
    }

    public static String getEntity(final long id)
    {
        return HibernateUtils.doCallInTransaction(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                Session currentSession = HibernateUtils.getCurrentSession();
                EntityObject loaded = (EntityObject)currentSession.load(EntityObjectSub.class, id);

                return loaded.toString();
            }
        });
    }

    public static long getMaxId()
    {
        return HibernateUtils.doCallInTransaction(new Callable<Long>()
        {
            @Override
            public Long call() throws Exception
            {
                Session currentSession = HibernateUtils.getCurrentSession();

                Query query = currentSession.createQuery("select id from " + EntityObjectSub.class.getName() + "  order by id desc");
                query.setMaxResults(1);

                return (Long)query.uniqueResult();
            }
        });
    }

    public static long updateEntity(final long id)
    {
        return HibernateUtils.doCallInTransaction(new Callable<Long>()
        {
            @Override
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
