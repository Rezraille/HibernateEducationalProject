package process.impl;

import dao.UserDaoService;
import entity.User;
import org.mockito.*;
import util.Util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class UpdateUserProcessTest {
    @Mock
    private Util util;
    @Mock
    private UserDaoService userDaoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute_whenUpdateOk() {
        int oldId = 3;
        int choiceYes = 1;
        int newId = 2;
        int newAge = 27;
        String newName = "updatedUser";
        String newEmail = "updated_user@gmail.com";

        List<Integer> responses = Arrays.asList(oldId, choiceYes, newId, choiceYes, choiceYes, choiceYes, newAge);
        ArgumentCaptor<User> captureUser = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<Integer> captureId = ArgumentCaptor.forClass(Integer.class);

        Mockito.when(util.getInputNumber()).thenAnswer(AdditionalAnswers.returnsElementsOf(responses));
        Mockito.doReturn(Optional.ofNullable(new User())).when(userDaoService).getById(3);
        Mockito.doReturn(newName).when(util).getInputName();
        Mockito.doReturn(newEmail).when(util).getInputEmail();

        UpdateUserProcess process = new UpdateUserProcess(userDaoService, util);
        process.execute();

        InOrder inOrder = Mockito.inOrder(util, userDaoService);
        inOrder.verify(util).getInputNumber();
        inOrder.verify(userDaoService).getById(oldId);
        inOrder.verify(util, times(3)).getInputNumber();
        inOrder.verify(util).getInputName();
        inOrder.verify(util).getInputNumber();
        inOrder.verify(util).getInputEmail();
        inOrder.verify(util, times(newId)).getInputNumber();
        inOrder.verify(userDaoService).updateUser(captureUser.capture(), captureId.capture());

        User userUpdateArgument = captureUser.getValue();
        Integer oldIdArgument = captureId.getValue();

        Assertions.assertNotNull(userUpdateArgument);
        Assert.assertTrue(oldIdArgument == oldId);
        Assert.assertTrue(userUpdateArgument.getId() == newId);
        Assert.assertEquals(userUpdateArgument.getName(), newName);
        Assert.assertEquals(userUpdateArgument.getEmail(), newEmail);
        Assert.assertTrue(userUpdateArgument.getAge() == newAge);
    }

    @Test
    public void execute_whenNotExist() {
        int id = 3;
        String expectedOutput = "Пользователь для обновления не найден.";
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Mockito.doReturn(id).when(util).getInputNumber();
        Mockito.doReturn(Optional.empty()).when(userDaoService).getById(id);

        UpdateUserProcess process = new UpdateUserProcess(userDaoService, util);
        process.execute();

        Mockito.verify(userDaoService, never()).updateUser(any(User.class), anyInt());

        Assert.assertTrue(outContent.toString().contains(expectedOutput));
    }
}
