package process.impl;

import entity.User;
import dao.UserDaoService;
import process.Process;
import util.Util;

public class DeleteUserProcess  implements Process
{
    private final UserDaoService userDaoService;

    public DeleteUserProcess(UserDaoService userDaoService)
    {
        this.userDaoService = userDaoService;
    }

    @Override
    public void execute()
    {
        System.out.println("Запущен процесс удаления пользователя.");
        System.out.println("Введите id.");
        Integer id = Util.getInputNumber();
        User user = userDaoService.getById(id);
        if (user != null)
        {
            userDaoService.deleteById(user.getId());
            System.out.println("Пользователь удален успешно.");
        }
        else
        {
            System.out.println("Нет пользователя, которого Вы хотите удалить.");
        }
    }
}
