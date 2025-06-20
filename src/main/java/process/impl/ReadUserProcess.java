package process.impl;

import entity.User;
import dao.UserDaoService;
import process.Process;
import util.Util;

import java.util.Optional;

public class ReadUserProcess implements Process
{
    private final UserDaoService userDaoService;
    private final Util util;

    public ReadUserProcess(UserDaoService userDaoService, Util util)
    {
        this.userDaoService = userDaoService;
        this.util = util;
    }

    @Override
    public void execute()
    {
        System.out.println("Запущен процесс чтения пользователя.");
        System.out.println("Введите id.");
        Integer id = util.getInputNumber();
        Optional <User> optionalUser = userDaoService.getById(id);
        if (optionalUser.isPresent())
        {
            System.out.println("\nПользователь успешно найден.");
            System.out.println(optionalUser.get());
        }
        else
        {
            System.out.println("Пользователь для чтения не найден.");
        }
    }
}
