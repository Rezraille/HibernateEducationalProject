package process.impl;


import dao.UserDaoService;
import entity.User;
import org.mockito.*;
import util.Util;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;

public class ReadUserProcessTest
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
    public void execute_whenReadOk()
    {
        int id = 5;
        User user = User.builder()
                .id(5)
                .name("testC")
                .email("test_3@test.tt")
                .age(33)
                .createdAt(LocalDateTime.now())
                .build();
        ArgumentCaptor<Integer> captureId = ArgumentCaptor.forClass(Integer.class);

        Mockito.doReturn(id).when(util).getInputNumber();
        Mockito.doReturn(Optional.ofNullable(user)).when(userDaoService).getById(captureId.capture());

        ReadUserProcess process = new ReadUserProcess(userDaoService, util);
        process.execute();

        InOrder inOrder = Mockito.inOrder(util, userDaoService);
        inOrder.verify(util).getInputNumber();
        inOrder.verify(userDaoService).getById(id);

        Integer idArgument = captureId.getValue();

        Assertions.assertNotNull(idArgument);
        Assert.assertTrue(idArgument == id);
    }

    @Test
    public void execute_whenNotExist()
    {
        int id = 3;
        String expectedOutput = "Пользователь для чтения не найден.";

        Mockito.doReturn(id).when(util).getInputNumber();
        Mockito.doReturn(Optional.empty()).when(userDaoService).getById(id);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ReadUserProcess process = new ReadUserProcess(userDaoService, util);
        process.execute();


        Assert.assertTrue(outContent.toString().contains(expectedOutput));
    }
}
