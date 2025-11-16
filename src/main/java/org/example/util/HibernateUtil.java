package org.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            buildSessionFactory();
        }
        return sessionFactory;
    }

    private static void buildSessionFactory() {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure()
                    .applySetting("hibernate.connection.url",
                            System.getProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/mydatabase"))
                    .applySetting("hibernate.connection.username",
                            System.getProperty("hibernate.connection.username", "myser"))
                    .applySetting("hibernate.connection.password",
                            System.getProperty("hibernate.connection.password", "mysecretpassword"))
                    .applySetting("hibernate.hbm2ddl.auto",
                            System.getProperty("hibernate.connection.url") != null ? "create-drop" : "update")
                    .build();

            MetadataSources metadataSources = new MetadataSources(standardRegistry);
            metadataSources.addAnnotatedClass(org.example.entity.User.class);

            Metadata metadata = metadataSources.getMetadataBuilder().build();

            sessionFactory = metadata.getSessionFactoryBuilder().build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Failed to create SessionFactory: " + e.getMessage());
        }
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }

    public static void recreateSessionFactory() {
        shutdown();
        buildSessionFactory();
    }
}