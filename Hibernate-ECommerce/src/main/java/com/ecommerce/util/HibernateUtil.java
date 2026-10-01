package com.ecommerce.util;

import com.ecommerce.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {
    private static final Logger logger=LoggerFactory.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory;
    private static String activeConfigFile="hibernate.cfg.xml";
    static {
        try { sessionFactory=buildSessionFactory(activeConfigFile); }
        catch(Throwable ex){ logger.error("Initial SessionFactory creation failed.",ex); throw new ExceptionInInitializerError(ex); }
    }
    private static SessionFactory buildSessionFactory(String configFile) {
        try {
            Configuration configuration=new Configuration().configure(configFile);
            configuration.addAnnotatedClass(Category.class);
            configuration.addAnnotatedClass(Product.class);
            configuration.addAnnotatedClass(Users.class);
            configuration.addAnnotatedClass(Orders.class);
            configuration.addAnnotatedClass(OrderDetails.class);
            return configuration.buildSessionFactory();
        } catch(Exception ex) {
            logger.error("Failed to build SessionFactory for {}",configFile,ex);
            throw new RuntimeException("SessionFactory initialization error: "+ex.getMessage(),ex);
        }
    }
    public static SessionFactory getSessionFactory(){return sessionFactory;}
    public static synchronized void reconfigure(String configFile){
        if(sessionFactory!=null&&!sessionFactory.isClosed())sessionFactory.close();
        activeConfigFile=configFile; sessionFactory=buildSessionFactory(configFile);
    }
    public static void shutdown(){if(sessionFactory!=null&&!sessionFactory.isClosed())sessionFactory.close();}
}
