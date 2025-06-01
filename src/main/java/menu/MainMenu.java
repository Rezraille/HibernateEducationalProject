package menu;

import process.*;
import dao.UserDaoService;
import dao.UserDao;
import util.Util;

public class MainMenu
{
    private final UserDaoService userDaoService = new UserDaoService(new UserDao());

    public void run()
    {
        while (true)
        {
            showMenu();
            int choice = Util.getInputNumber();
            switch (choice)
            {
                case 0:
                    System.out.println("Выход из программы.");
                    return;
                case 1:
                    new CreateUserProcess(userDaoService).execute();
                    break;
                case 2:
                    new ReadUserProcess(userDaoService).execute();
                    break;
                case 3:
                    new UpdateUserProcess(userDaoService).execute();
                    break;
                case 4:
                    new DeleteUserProcess(userDaoService).execute();
                    break;
                case 5:
                    new ReadAllUserProcess(userDaoService).execute();
                    break;
                default:
                    System.out.println("Не верный выбор. Попробуйте еще раз.");
            }
        }
    }

    private void showMenu()
    {
        System.out.println("\nМеню пользователя");
        System.out.println("0. Выход.");//todo что будет значить этот выход?
        System.out.println("1. Добавить пользователя.");
        System.out.println("2. Найти пользователя по ID.");
        System.out.println("3. Обновить пользователя по ID.");
        System.out.println("4. Удалить пользователя по ID.");
        System.out.println("5. Показать всех пользователей.");
        System.out.print("Введите номер пункта.\n");
    }
}