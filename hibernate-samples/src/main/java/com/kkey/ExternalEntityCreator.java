package com.kkey;

import java.util.concurrent.CountDownLatch;

import com.kkey.entity.EntityDao;

/**
 * @author astarovoyt
 *
 */
public class ExternalEntityCreator
{

    public Thread startNewThreadUpdate(final int count, final CountDownLatch endFlag)
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 0; i < count; i++)
                {
                    doAction();
                }
                endFlag.countDown();
            }

        });
        thread.start();

        return thread;
    }

    private void doAction()
    {
        EntityDao.createEntity();
    }

}
