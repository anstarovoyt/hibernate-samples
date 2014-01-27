package com.kkey;

import java.io.IOException;
import java.util.Random;

import com.kkey.entity.EntityDao;

/**
 * Hello world!
 *
 */
public class App
{
    public static final Random RND = new Random();


    public static void main(String[] args) throws IOException
    {
        long entityId = EntityDao.createEntity();
        EntityDao.updateEntity(entityId);

    }

}
