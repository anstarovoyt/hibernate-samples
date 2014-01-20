package com.kkey;


/**
 * Hello world!
 *
 */
public class App
{

    public static void main(String[] args)
    {
        try
        {
            HibernateUtils.getSessionFactory();
        }
        catch (Throwable ex)
        {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
