package dao;

import entity.User;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Assert;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.PersistenceException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;

@Testcontainers
class UserDaoServiceTest {
    @Container
    private static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:13");
    private static UserDaoService userDaoService;

    private static SessionFactory factory;


    @BeforeAll
    public static void init() {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", postgres.getUsername());
        configuration.setProperty("hibernate.connection.password", postgres.getPassword());
        factory = configuration.buildSessionFactory();
        DBService dbService = new DBService(factory);
        UserDao userDao = new UserDao(dbService);
        userDaoService = new UserDaoService(userDao, dbService);
    }

    @BeforeEach
    public void updateDatabase() {
        Transaction transaction = factory.getCurrentSession().getTransaction();
        try (Connection conn = getConnection()) {

            transaction.begin();

            String sql = "TRUNCATE TABLE users";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.execute();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
    }

    @Test
    public void getById_whenExists() {
        User user = User.builder()
                .id(1231)
                .name("test")
                .email("test_1@test.tt")
                .age(11)
                .createdAt(LocalDateTime.now())
                .build();
        userDaoService.createUser(user);
        Optional<User> fromDb = userDaoService.getById(user.getId());
        Assertions.assertEquals(fromDb.get(), user);
    }

    @Test
    public void getById_whenNotExist() {
        Optional<User> fromDb = userDaoService.getById(1);
        Assertions.assertFalse(fromDb.isPresent());
    }

    @Test
    public void createUser_whenNotExist() {
        User user = User.builder()
                .id(123)
                .name("testA")
                .email("test_1@test.tt")
                .age(11)
                .createdAt(LocalDateTime.now())
                .build();
        Optional<User> toDb = userDaoService.createUser(user);
        Optional<User> fromDb = userDaoService.getById(user.getId());
        Assertions.assertEquals(toDb.get(), fromDb.get());
    }

    @Test
    public void createUser_whenExist() {

        User user = User.builder()
                .id(1)
                .name("testA")
                .email("test_1@test.tt")
                .age(11)
                .createdAt(LocalDateTime.now())
                .build();
        userDaoService.createUser(user);
        String expectedOutput = String.format("Запись %d с уникальным ключем существует в базе данных. Операция отменена.\n", user.getId());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        userDaoService.createUser(user);

         Assert.assertTrue(outContent.toString().contains(expectedOutput));
    }

    @Test
    public void updateUser_whenExist() {
        User oldUser = User.builder()
                .id(1)
                .name("testA")
                .email("test_1@test.tt")
                .age(11)
                .createdAt(LocalDateTime.now())
                .build();
        userDaoService.createUser(oldUser);
        User newUser = User.builder()
                .id(1)
                .name("testC")
                .email("test_3@test.tt")
                .age(33)
                .createdAt(LocalDateTime.now())
                .build();

        userDaoService.updateUser(newUser, oldUser.getId());
        Optional<User> fromDb = userDaoService.getById(oldUser.getId());

        Assertions.assertEquals(newUser, fromDb.get());
    }

    @Test
    public void updateUser_whenNotExist() {
        User inDb = User.builder()
                .id(124)
                .name("testB")
                .email("test_2@test.tt")
                .age(22)
                .createdAt(LocalDateTime.now())
                .build();
        userDaoService.updateUser(inDb, 124);
        Optional<User> fromDb = userDaoService.getById(124);
        Assertions.assertFalse(fromDb.isPresent());
    }

    @Test
    public void updateUser_whenExistWithSameId() {
        User oldUser = User.builder()
                .id(1)
                .name("testA")
                .email("test_1@test.tt")
                .age(11)
                .createdAt(LocalDateTime.now())
                .build();
        User user = User.builder()
                .id(2)
                .name("testB")
                .email("test_2@test.tt")
                .age(22)
                .createdAt(LocalDateTime.now())
                .build();
        userDaoService.createUser(oldUser);
        userDaoService.createUser(user);
        User newUser = User.builder()
                .id(user.getId())
                .name("testC")
                .email("test_3@test.tt")
                .age(33)
                .createdAt(LocalDateTime.now())
                .build();

        userDaoService.updateUser(newUser, oldUser.getId());
        Optional<User> fromDb = userDaoService.getById(oldUser.getId());
        System.out.println(fromDb.get());
        Assertions.assertEquals(oldUser, fromDb.get());
    }

    @Test
    public void deleteById_whenExist() {
        User user = User.builder()
                .id(1)
                .name("testA")
                .email("test_1@test.tt")
                .age(11)
                .createdAt(LocalDateTime.now())
                .build();
        userDaoService.createUser(user);
        boolean isDelete = userDaoService.deleteById(1);
        Optional<User> fromDb = userDaoService.getById(user.getId());
        Assertions.assertTrue(isDelete);
        Assertions.assertFalse(fromDb.isPresent());
    }

    @Test
    public void deleteById_whenNotExist() {
        User user = User.builder()
                .id(1)
                .name("testA")
                .email("test_1@test.tt")
                .age(11)
                .createdAt(LocalDateTime.now())
                .build();
        boolean isDelete = userDaoService.deleteById(1);
        Optional<User> fromDb = userDaoService.getById(user.getId());
        Assertions.assertFalse(isDelete);
        Assertions.assertFalse(fromDb.isPresent());
    }

    @Test
    public void deleteById_whenExist_correctSumOfElementsAfter() {
        User userOne = User.builder()
                .id(1)
                .name("testA")
                .email("test_1@test.tt")
                .age(11)
                .createdAt(LocalDateTime.now())
                .build();
        User userTwo = User.builder()
                .id(2)
                .name("testB")
                .email("test_2@test.tt")
                .age(22)
                .createdAt(LocalDateTime.now())
                .build();
        userDaoService.createUser(userOne);
        userDaoService.createUser(userTwo);
        boolean isDelete = userDaoService.deleteById(1);
        Assertions.assertTrue(isDelete);
        Assertions.assertTrue(userDaoService.findAll().size() == 1);
    }

    @Test
    public void findAll_whenExists() {
        User userOne = User.builder()
                .id(1)
                .name("testA")
                .email("test_1@test.tt")
                .age(11)
                .createdAt(LocalDateTime.now())
                .build();
        User userTwo = User.builder()
                .id(2)
                .name("testB")
                .email("test_2@test.tt")
                .age(22)
                .createdAt(LocalDateTime.now())
                .build();
        User userThree = User.builder()
                .id(3)
                .name("testC")
                .email("test_3@test.tt")
                .age(33)
                .createdAt(LocalDateTime.now())
                .build();
        userDaoService.createUser(userOne);
        userDaoService.createUser(userTwo);
        userDaoService.createUser(userThree);
        List<User> users = userDaoService.findAll();
        users.sort((user_1, user_2) -> user_1.getId() - user_2.getId());
        Assertions.assertTrue(users.size() == 3);
        Assertions.assertEquals(userOne, users.get(0));
        Assertions.assertEquals(userTwo, users.get(1));
        Assertions.assertEquals(userThree, users.get(2));
    }

}
