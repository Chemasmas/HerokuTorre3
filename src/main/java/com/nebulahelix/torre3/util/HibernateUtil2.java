package com.nebulahelix.torre3.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Chemasmas
 */
public class HibernateUtil2 {
    private static final SessionFactory sessionFactory=buildSession();
    
    private static SessionFactory buildSession()
    {
            return new Configuration().configure().buildSessionFactory();
    }
    
    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }
    
    public static void shutdown()
    {
        getSessionFactory().close();
    }
}
