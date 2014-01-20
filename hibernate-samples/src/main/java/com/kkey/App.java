package com.kkey;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;

import org.hibernate.Session;

import com.kkey.entity.EntityObjectSub;

/**
 * Hello world!
 *
 */
public class App
{
    public static final Random RND = new Random();

    public static long addEntity()
    {
        Session session = HibernateUtils.getCurrentSession();
        EntityObjectSub object = new EntityObjectSub("Our very first event!");
        session.save(object);

        return object.getId();
    }

    public static void main(String[] args) throws IOException
    {
        final long id = HibernateUtils.doCallInTransaction(new Callable<Long>()
        {
            public Long call()
            {
                return addEntity();
            }
        });

        HibernateUtils.doInTransaction(new Runnable()
        {
            public void run()
            {
                System.out.println("start");
                Session currentSession = HibernateUtils.getCurrentSession();
                EntityObjectSub loaded = (EntityObjectSub)currentSession.load(EntityObjectSub.class, id);

                updateEntity(loaded);

                new MultiThreadTestCase().startNewThreadUpdate(id);

                System.out.println("end");

            }
        });

    }

    public static void updateEntity(EntityObjectSub loaded)
    {
        Session currentSession = HibernateUtils.getCurrentSession();

        loaded.setSomeValue(String.valueOf(RND.nextInt()));
        currentSession.saveOrUpdate(loaded);
        currentSession.flush();
    }

    public static void updateEntity(final long id)
    {
        Session currentSession = HibernateUtils.getCurrentSession();
        EntityObjectSub loaded = (EntityObjectSub)currentSession.load(EntityObjectSub.class, id);

        loaded.setSomeValue(String.valueOf(RND.nextInt()));
        currentSession.saveOrUpdate(loaded);
        currentSession.flush();
    }

}
