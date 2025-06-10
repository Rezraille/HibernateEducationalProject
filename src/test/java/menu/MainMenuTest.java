package menu;


import process.Process;
import util.Util;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import dao.UserDaoService;


import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class MainMenuTest {
    private MainMenu mainMenu;
    @Mock
    private Process someProcess;

    @Mock
    private UserDaoService userDaoService;

    @Mock
    private Util util;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        mainMenu = new MainMenu(userDaoService, util);
    }

    @Test
    public void run_WhenChoiceIsZero() {
        int choice = 0;
        String expectedOutput = "Выход из программы.";
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Mockito.doReturn(choice).when(util).getInputNumber();

        mainMenu.run();

        Mockito.verify(util).getInputNumber();
        Mockito.verifyNoMoreInteractions(someProcess);

        Assert.assertTrue(outContent.toString().contains(expectedOutput));
    }
}
