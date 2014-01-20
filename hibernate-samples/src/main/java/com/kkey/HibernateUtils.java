package com.kkey;

import java.util.concurrent.Callable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

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
            Configuration configuration = new Configuration();
            configuration.configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

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