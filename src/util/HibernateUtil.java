package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil{

    private static SessionFactory sessionFactory;

    static{
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        System.out.println("✅SessionFactory demarree");
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void resetSessionFactory(){
        if (sessionFactory != null){
            sessionFactory.close();
        }
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        System.out.println("🔄SessionFactory reinitialisee");
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("👋SessionFactory fermee");
        }
    }
}
