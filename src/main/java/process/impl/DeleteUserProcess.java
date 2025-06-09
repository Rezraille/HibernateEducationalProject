package process.impl;

import entity.User;
import dao.UserDaoService;
import process.Process;
import util.Util;

import java.util.Optional;

public class DeleteUserProcess  implements Process
{
    private final UserDaoService userDaoService;
    private final Util util;

    public DeleteUserProcess(UserDaoService userDaoService, Util util)
    {
        this.userDaoService = userDaoService;
        this.util = util;
    }

    @Override
    public void execute()
    {
        System.out.println("Запущен процесс удаления пользователя.");
        System.out.println("Введите id.");
        Integer id = util.getInputNumber();
        Optional<User> optionalUser = userDaoService.getById(id);
        if (optionalUser.isPresent())
        {
            userDaoService.deleteById(optionalUser.get().getId());
            System.out.println("Пользователь удален успешно.");
        }
        else
        {
            System.out.println("Нет пользователя, которого Вы хотите удалить.");
        }
    }
}
