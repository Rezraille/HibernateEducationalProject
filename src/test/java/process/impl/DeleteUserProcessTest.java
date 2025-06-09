package process.impl;


import dao.UserDaoService;
import entity.User;
import util.Util;

import java.time.LocalDateTime;
import java.util.Optional;

import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class DeleteUserProcessTest
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
    public void execute_whenDeleteOk()
    {
        int id = 1;
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
    }
}
