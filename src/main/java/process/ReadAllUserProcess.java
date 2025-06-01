package process;

import entity.User;
import dao.UserDaoService;

import java.util.List;

public class ReadAllUserProcess
{
    private final UserDaoService userDaoService;

    public ReadAllUserProcess(UserDaoService userDaoService)
    {
        this.userDaoService = userDaoService;
    }

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
