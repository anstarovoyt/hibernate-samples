package com.kkey;

import org.hibernate.Session;

import com.google.common.base.Throwables;
import com.kkey.entity.EntityObjectSub;

/**
 * @author astarovoyt
 *
 */
public class MultiThreadTestCase
{

    public void startNewThreadUpdate(final long id)
    {
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                doAction(id);
            }

        });
        thread.start();

        try
        {
            thread.join();
        }
        catch (InterruptedException e)
        {
            Throwables.propagate(e);
        }
    }

    private void doAction(final long id)
    {
        HibernateUtils.doInTransaction(new Runnable()
        {
            public void run()
            {
                updateEntity(id);
            }

        });
    }

    private void updateEntity(final long id)
    {
        Session currentSession = HibernateUtils.getCurrentSession();
        EntityObjectSub loaded = (EntityObjectSub)currentSession.load(EntityObjectSub.class, id);

        loaded.setSomeValue(String.valueOf(App.RND.nextInt()));
        loaded.setSomeValue2(String.valueOf(App.RND.nextInt()));
        currentSession.saveOrUpdate(loaded);
        currentSession.flush();
    }
}
