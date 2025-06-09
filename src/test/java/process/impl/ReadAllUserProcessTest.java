package process.impl;


import java.util.ArrayList;
import dao.UserDaoService;

import org.mockito.InOrder;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ReadAllUserProcessTest
{
    private UserDaoService userDaoService;

    @BeforeEach
    public void setup()
    {
        userDaoService = Mockito.mock(UserDaoService.class);
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
}
