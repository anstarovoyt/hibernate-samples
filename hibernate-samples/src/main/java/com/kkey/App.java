package com.kkey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public static final Random rnd = new Random();

    public static void main(String[] args) throws IOException
    {
        start();
    }

    public static void start()
    {
        List<Long> list = getEntityIds();

        List<Long> list2 = getEntityIds();

        try
        {
            System.out.println("before update");
            Thread.sleep(10000);
            System.out.println("start update");
        }
        catch (InterruptedException e)
        {
        }
        Thread thread = new ExternalThreadUpdater().startNewThreadUpdate(list.toArray(new Long[0]));
        Thread thread2 = new ExternalThreadUpdater().startNewThreadUpdate(list2.toArray(new Long[0]));

        for (int i = 0; i < ENTITY_COUNT; i++)
        {
            list.add(EntityDao.createEntity());
        }

        try
        {
            thread.join();
            thread2.join();
        }
        catch (InterruptedException e)
        {
            Throwables.propagate(e);
        }

        System.out.println("end");
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
