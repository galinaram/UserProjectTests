package dao;

import org.example.util.HibernateUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class BaseDatabaseTest {

    @Container
    protected static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @BeforeAll
    static void setupDatabase() {
        if (!postgresqlContainer.isRunning()) {
            postgresqlContainer.start();
        }

        System.setProperty("hibernate.connection.url", postgresqlContainer.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgresqlContainer.getUsername());
        System.setProperty("hibernate.connection.password", postgresqlContainer.getPassword());

        HibernateUtil.recreateSessionFactory();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @BeforeEach
    void clearDatabase() {
    }

    @AfterAll
    static void tearDown() {
        HibernateUtil.shutdown();
        if (postgresqlContainer.isRunning()) {
            postgresqlContainer.stop();
        }
    }
}