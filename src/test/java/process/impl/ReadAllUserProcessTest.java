package process.impl;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.UserDaoService;

import entity.User;
import org.junit.Assert;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import util.Util;


public class ReadAllUserProcessTest
{
    @Mock
    private UserDaoService userDaoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute_whenReadOk()
    {
        Mockito.when(userDaoService.findAll()).thenReturn(new ArrayList<>());

        ReadAllUserProcess process = new ReadAllUserProcess(userDaoService);
        process.execute();

        InOrder inOrder = Mockito.inOrder(userDaoService);
        inOrder.verify(userDaoService).findAll();
    }

    @Test
    public void execute_whenExist_OutUsersToConsole()
    {

        User userOne = User.builder().
                id(1).
                name("Test").
                email("test_1@test.test").
                age(11).
                createdAt(LocalDateTime.now()).
                build();
        User userTwo = User.builder().
                id(2).
                name("Юзер").
                email("test_2@test.test").
                age(22).
                createdAt(LocalDateTime.now()).
                build();
        List<User> users = new ArrayList<>();
        users.add(userOne);
        users.add(userTwo);

        String userOneStr = userOne.toString();
        String userTwoStr = userTwo.toString();
        String expectedOutput ="Запущен процесс чтения всех пользователей.\r\n" +
                "\nСписок пользователей:\r\n" +
                userOneStr + "\r\n" +
                userTwoStr + "\r\n";

        Mockito.when(userDaoService.findAll()).thenReturn(users);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ReadAllUserProcess process = new ReadAllUserProcess(userDaoService);
        process.execute();
        System.out.println(outContent.toString().contains(expectedOutput));
        Assert.assertTrue(outContent.toString().contains(expectedOutput));
    }

}
