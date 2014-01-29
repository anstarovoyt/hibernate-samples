package com.kkey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.kkey.entity.EntityDao;

/**
 * Hello world!
 *
 */
public class App
{
    private static final int ENTITY_COUNT = 10000;

    private static int COUNT_UPDATE = 4;
    private static int COUNT_CREATE = 4;

    private static boolean ASYNC_UPDATE = true;

    public static final Random rnd = new Random();

    public static void main(String[] args) throws IOException
    {
        if (args != null && args.length == 3)
        {
            COUNT_UPDATE = Integer.valueOf(args[0]);
            COUNT_CREATE = Integer.valueOf(args[1]);

            ASYNC_UPDATE = !"select".equals(args[2].toLowerCase());
        }

        start();
    }

    public static void start()
    {
        List<List<Long>> dataForUpdates = Lists.newArrayList();

        for (int i = 0; i < COUNT_UPDATE; i++)
        {
            dataForUpdates.add(getEntityIds());
        }

        long currentTimeMillis = System.currentTimeMillis();
        try
        {
            System.out.println("before update");
            Thread.sleep(1000);
            System.out.println("start update");
        }
        catch (InterruptedException e)
        {
            Throwables.propagate(e);
        }

        CountDownLatch latch = new CountDownLatch(COUNT_UPDATE + COUNT_CREATE);

        for (int i = 0; i < COUNT_CREATE; i++)
        {
            new ExternalEntityCreator().startNewThreadUpdate(ENTITY_COUNT, latch);
        }
        for (int i = 0; i < COUNT_UPDATE; i++)
        {
            new ExternalEntityUpdater().startNewThreadUpdate(dataForUpdates.get(i).toArray(new Long[0]), latch);
        }

        try
        {
            latch.await();
        }
        catch (InterruptedException e)
        {
            Throwables.propagate(e);
        }

        System.out.println("end:" + (System.currentTimeMillis() - currentTimeMillis));

        doAsync();
    }

    private static void doAsync()
    {
        new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (true)
                {
                    long maxId = EntityDao.getMaxId();
                    if (ASYNC_UPDATE)
                    {
                        System.out.println("before: " + EntityDao.getEntity(maxId));
                        EntityDao.updateEntity(maxId);
                        System.out.println("after: " + EntityDao.getEntity(maxId));
                    }
                    else
                    {
                        System.out.println(EntityDao.getEntity(maxId));
                    }

                    try
                    {
                        Thread.sleep(10000);
                    }
                    catch (InterruptedException e)
                    {
                        Throwables.propagate(e);
                    }
                }
            }
        }).start();
    }

    private static ArrayList<Long> getEntityIds()
    {
        ArrayList<Long> list = Lists.newArrayList();
        for (int i = 0; i < ENTITY_COUNT; i++)
        {
            list.add(EntityDao.createEntity());
        }
        return list;
    }

}
