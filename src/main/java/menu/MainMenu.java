package menu;

import dao.UserDao;
import dao.UserDaoService;
import process.Process;
import process.impl.*;
import util.Util;

import java.util.HashMap;
import java.util.Map;

public class MainMenu
{
    private static final Process UNKNOWN_CHOICE_PROCESS = () -> System.out.println("Не верный выбор. Попробуйте еще раз.");

    private final Map<Integer, Process> menuChoiceToProcess = new HashMap<>();

    private final Util util;


    public MainMenu(UserDaoService userDaoService, Util util)
    {
        this.util = util;
        menuChoiceToProcess.put(1, new CreateUserProcess(userDaoService,util));
        menuChoiceToProcess.put(2, new ReadUserProcess(userDaoService,util));
        menuChoiceToProcess.put(3, new UpdateUserProcess(userDaoService,util));
        menuChoiceToProcess.put(4, new DeleteUserProcess(userDaoService,util));
        menuChoiceToProcess.put(5, new ReadAllUserProcess(userDaoService));
    }

    public void run()
    {
        while (true)
        {
            showMenu();
            int choice = util.getInputNumber();
            if (choice == 0)
            {
                System.out.println("Выход из программы.");
                return;
            }

            Process process = menuChoiceToProcess.getOrDefault(choice, UNKNOWN_CHOICE_PROCESS);
            process.execute();
        }
    }

    private void showMenu()
    {
        System.out.println("\nМеню пользователя");
        System.out.println("0. Выход.");
        System.out.println("1. Добавить пользователя.");
        System.out.println("2. Найти пользователя по ID.");
        System.out.println("3. Обновить пользователя по ID.");
        System.out.println("4. Удалить пользователя по ID.");
        System.out.println("5. Показать всех пользователей.");
        System.out.print("Введите номер пункта.\n");
    }
}