package process.impl;


import dao.UserDaoService;
import entity.User;
import org.mockito.*;
import util.Util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;


public class DeleteUserProcessTest
{
    @Mock
    private Util util;
    @Mock
    private UserDaoService userDaoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute_whenDeleteOk() {
        int id = 1;
        String expectedOutput = "Пользователь удален успешно.";
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        User user = User.builder()
                .id(1)
                .name("testC")
                .email("test_3@test.tt")
                .age(33)
                .createdAt(LocalDateTime.now())
                .build();
        ArgumentCaptor<Integer> captureId = ArgumentCaptor.forClass(Integer.class);

        Mockito.doReturn(user.getId()).when(util).getInputNumber();
        Mockito.doReturn(Optional.ofNullable(user)).when(userDaoService).getById(id);
        Mockito.doReturn(true).when(userDaoService).deleteById(id);

        DeleteUserProcess process = new DeleteUserProcess(userDaoService, util);
        process.execute();

        InOrder inOrder = Mockito.inOrder(util, userDaoService);
        inOrder.verify(util).getInputNumber();
        inOrder.verify(userDaoService).getById(id);
        inOrder.verify(userDaoService).deleteById(captureId.capture());

        Integer idArgument = captureId.getValue();

        Assertions.assertNotNull(idArgument);
        Assert.assertTrue(idArgument == id);
        Assert.assertTrue(outContent.toString().contains(expectedOutput));
    }

    @Test
    public void execute_whenNotExist() {
        int id = 3;
        String expectedOutput = "Нет пользователя, которого Вы хотите удалить.";
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Mockito.doReturn(id).when(util).getInputNumber();
        Mockito.doReturn(Optional.empty()).when(userDaoService).getById(id);

        DeleteUserProcess process = new DeleteUserProcess(userDaoService, util);
        process.execute();

        Mockito.verify(userDaoService, never()).deleteById(anyInt());

        Assert.assertTrue(outContent.toString().contains(expectedOutput));
    }
}
