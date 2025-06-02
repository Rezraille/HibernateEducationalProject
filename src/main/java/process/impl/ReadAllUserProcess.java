package process.impl;

import entity.User;
import dao.UserDaoService;
import process.Process;

import java.util.List;

public class ReadAllUserProcess  implements Process
{
    private final UserDaoService userDaoService;

    public ReadAllUserProcess(UserDaoService userDaoService)
    {
        this.userDaoService = userDaoService;
    }

    @Override
    public void execute()
    {
        System.out.println("Запущен процесс чтения всех пользователей.");
        List<User> users = userDaoService.findAll();
        if (users.size() != 0)
        {
            System.out.println("\nСписок пользователей:");
            for (User user : users)
            {
                System.out.println(user);
            }
        } else
        {
            System.out.println("Список пользователей пуст.");
        }
    }
}
