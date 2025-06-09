package process.impl;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import util.Util;
import entity.User;
import dao.UserDaoService;

import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateUserProcessTest
{

    private Util util;
    private UserDaoService userDaoService;

    @BeforeEach
    public void setup()
    {
        util = Mockito.mock(Util.class);
        userDaoService = Mockito.mock(UserDaoService.class);
    }


    @Test
    public void execute_whenCreateOk()
    {
        int id = 1;
        int age = 11;
        String name = "createName";
        String email = "createEmail_1@test.ru";

        List<Integer> responses = Arrays.asList(id, age);
        ArgumentCaptor<User> captureUser = ArgumentCaptor.forClass(User.class);

        Mockito.when(util.getInputNumber()).thenAnswer(AdditionalAnswers.returnsElementsOf(responses));
        Mockito.doReturn(name).when(util).getInputName();
        Mockito.doReturn(email).when(util).getInputEmail();
        Mockito.when(userDaoService.createUser(Mockito.any(User.class))).thenReturn(Optional.ofNullable(new User()));

        CreateUserProcess process = new CreateUserProcess(userDaoService, util);
        process.execute();

        InOrder inOrder = Mockito.inOrder(util, userDaoService);
        inOrder.verify(util).getInputNumber();
        inOrder.verify(util).getInputName();
        inOrder.verify(util).getInputEmail();
        inOrder.verify(util).getInputNumber();
        inOrder.verify(userDaoService).createUser(captureUser.capture());

        User userCreate = captureUser.getValue();

        Assertions.assertNotNull(userCreate);
        Assert.assertTrue(userCreate.getId() == id);
        Assert.assertEquals(userCreate.getName(), name);
        Assert.assertEquals(userCreate.getEmail(), email);
        Assert.assertTrue(userCreate.getAge() == age);
    }

}
