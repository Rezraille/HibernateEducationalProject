package process.impl;

import entity.User;
import dao.UserDaoService;
import process.Process;
import util.Util;

public class ReadUserProcess implements Process
{
    private final UserDaoService userDaoService;

    public ReadUserProcess(UserDaoService userDaoService)
    {
        this.userDaoService = userDaoService;
    }

    @Override
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
