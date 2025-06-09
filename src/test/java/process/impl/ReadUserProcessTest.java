package process.impl;


import dao.UserDaoService;
import entity.User;
import util.Util;

import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.Optional;

public class ReadUserProcessTest
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
}
