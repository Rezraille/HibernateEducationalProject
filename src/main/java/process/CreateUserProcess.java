package process;

import entity.User;
import dao.UserDaoService;
import util.Util;

import java.time.LocalDateTime;


public class CreateUserProcess
{
    private final UserDaoService userDaoService;

    public CreateUserProcess(UserDaoService userDaoService)
    {
        this.userDaoService = userDaoService;
    }

    public void execute()
    {
        System.out.println("Запущен процесс добавления пользователя.");
        User user = new User();
        createId(user);
        createName(user);
        createEmail(user);
        createAge(user);
        user.setCreatedAt(LocalDateTime.now());
        if (!user.isEmpty())
        {
            User newUser = userDaoService.createUser(user);
            if (newUser == null)
            {
                System.out.println("Ошибка, пользователь не создан.");
            }
            else
            {
                System.out.println("Пользователь создан успешно.");
            }
        }
    }

    private void createId(User user)
    {
        System.out.println("Введите id");
        user.setId(Util.getInputNumber());
    }

    private void createName(User user)
    {
        System.out.println("Введите новое имя");
        user.setName(Util.getInputName());
    }

    private void createEmail(User user)
    {
        System.out.println("Введите новый email");
        user.setEmail(Util.getInputEmail());
    }

    private void createAge(User user)
    {
        System.out.println("Введите новый возраст");
        user.setAge(Util.getInputNumber());
    }
}
