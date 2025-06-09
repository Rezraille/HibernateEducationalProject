package util;


import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class UtilTest
{

    private Util util = new Util();
    @AfterEach
    public void getScanner()
    {
       util.setScanner(new Scanner(System.in));
    }

    @Test
    public void getInputNumber_whenNegative()
    {
        Scanner scanner = getScanner("-5\n" +
                                            "5");
        util.setScanner(scanner);
        Integer num = util.getInputNumber();
        Assertions.assertEquals(num, 5);
    }

    @Test
    public void getInputNumber_whenFloat()
    {
        Scanner scanner = getScanner("5.5\n" +
                                            "5,5\n" +
                                            "5");
        util.setScanner(scanner);
        Integer num = util.getInputNumber();
        Assertions.assertEquals(num, 5);
    }

    @Test
    public void getInputNumber_whenNumberTooBig()
    {
        String input = String.valueOf(Long.MAX_VALUE) + "\n" +
                                                        "5";
        Scanner scanner = getScanner(input);
        util.setScanner(scanner);
        Integer num = util.getInputNumber();
        Assertions.assertEquals(num, 5);
    }

    @Test
    public void getInputNumber_whenEmpty()
    {
        Scanner scanner = getScanner("\n" +
                                            "5");
        util.setScanner(scanner);
        Integer num = util.getInputNumber();
        Assertions.assertEquals(num, 5);
    }

    @Test
    public void getInputNumber_whenNotNumber()
    {
        Scanner scanner = getScanner("test\n" +
                                            "&&&===@ \n" +
                                            "5");
        util.setScanner(scanner);
        Integer num = util.getInputNumber();
        Assertions.assertEquals(num, 5);
    }

    @Test
    public void getInputEmail_whenNotEmailPattern()
    {
        Scanner scanner = getScanner("test_1test.tt\n" +
                                            "123\n" +
                                            "@@@@@@\n" +
                                            "test$1@test.tt\n" +
                                            "test1@test$.tt\n" +
                                            "test_1-.@test-2-..tt");
        util.setScanner(scanner);
        String num = util.getInputEmail();
        Assertions.assertEquals(num, "test_1-.@test-2-..tt");
    }
    @Test
    public void getInputName_whenOnlyLetter()
    {
        Scanner scanner = getScanner("Тест");
        util.setScanner(scanner);
        String num = util.getInputName();
        Assertions.assertEquals(num, "Тест");
    }

    @Test
    public void getInputName_whenSpaceInStartAndInEnd()
    {
        Scanner scanner = getScanner("   Тест   ");
        util.setScanner(scanner);
        String num = util.getInputName();
        Assertions.assertEquals(num, "Тест");
    }
    @Test
    public void getInputName_whenOtherLanguage()
    {
        Scanner scanner = getScanner("測試");
        util.setScanner(scanner);
        String num = util.getInputName();
        Assertions.assertEquals(num, "測試");
    }

    @Test
    public void getInputName_whenDifferentSymbolsInsteadLetter()
    {
        Scanner scanner = getScanner("test_\n" +
                                            "@@@@@@\n" +
                                            "123\n" +
                                            "&&&test\n" +
                                            "test test\n" +
                                            "test");
        util.setScanner(scanner);
        String num = util.getInputName();
        Assertions.assertEquals(num, "test");
    }

    private Scanner getScanner(String input)
    {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        return new Scanner(inputStream);
    }
}
