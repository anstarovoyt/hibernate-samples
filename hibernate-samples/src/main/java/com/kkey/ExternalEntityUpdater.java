package com.kkey;

import java.util.concurrent.CountDownLatch;

import org.hibernate.Session;

import com.kkey.entity.EntityObjectSub;

/**
 * @author astarovoyt
 *
 */
public class ExternalEntityUpdater
{

    public Thread startNewThreadUpdate(final Long[] ids, final CountDownLatch endFlag)
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (Long id : ids)
                {
                    doAction(id);
                }

                endFlag.countDown();
            }

        });
        thread.start();

        return thread;
    }

    private void doAction(final long id)
    {
        HibernateUtils.doInTransaction(new Runnable()
        {
            @Override
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

        loaded.setSomeValue(String.valueOf(App.rnd.nextInt()));
        loaded.setSomeValue2(String.valueOf(App.rnd.nextInt()));
        currentSession.saveOrUpdate(loaded);
        currentSession.flush();
    }
}
