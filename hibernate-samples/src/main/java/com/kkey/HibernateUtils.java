package com.kkey;

import java.util.concurrent.Callable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import com.google.common.base.Throwables;

/**
 * @author astarovoyt
 *
 */
public class HibernateUtils
{

    private static final SessionFactory sessionFactory;

    static
    {
        try
        {
            Configuration configuration = new AnnotationConfiguration();
            configuration.configure();
            sessionFactory = configuration.buildSessionFactory();

        }
        catch (Throwable ex)
        {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static <T> T doCallInTransaction(Callable<T> callable)
    {
        Session session = HibernateUtils.getCurrentSession();
        T result = null;
        try
        {
            Transaction transaction = session.beginTransaction();

            result = callable.call();

            transaction.commit();
        }
        catch (Exception e)
        {
            Throwables.propagate(e);
        }

        return result;
    }

    public static void doInTransaction(Runnable runnable)
    {
        Session session = HibernateUtils.getCurrentSession();

        Transaction beginTransaction = session.beginTransaction();

        runnable.run();

        beginTransaction.commit();
    }

    public static Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    public static Session getNewSession()
    {
        return sessionFactory.openSession();
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

}