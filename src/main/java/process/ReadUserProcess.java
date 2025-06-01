package process;

import entity.User;
import dao.UserDaoService;
import util.Util;

public class ReadUserProcess
{
    private final UserDaoService userDaoService;

    public ReadUserProcess(UserDaoService userDaoService)
    {
        this.userDaoService = userDaoService;
    }

    public void execute()
    {
        System.out.println("Запущен процесс чтения пользователя.");
        System.out.println("Введите id.");
        Integer id = Util.getInputNumber();
        User user = userDaoService.getById(id);
        if (user != null)
        {
            System.out.println("\nПользователь успешно найден.");
            System.out.println(user);
        }
        else
        {
            System.out.println("Пользователь для чтения не найден.");
        }
    }
}
