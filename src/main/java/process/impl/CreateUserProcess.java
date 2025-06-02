package process.impl;

import entity.User;
import dao.UserDaoService;
import process.Process;
import util.Util;

import java.time.LocalDateTime;


public class CreateUserProcess implements Process
{
    private final UserDaoService userDaoService;

    public CreateUserProcess(UserDaoService userDaoService)
    {
        this.userDaoService = userDaoService;
    }

    @Override
    public void execute()
    {
        System.out.println("Запущен процесс добавления пользователя.");
        User user = new User();
        setId(user);
        setName(user);
        setEmail(user);
        setAge(user);
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

    private void setId(User user)
    {
        System.out.println("Введите id");
        user.setId(Util.getInputNumber());
    }

    private void setName(User user)
    {
        System.out.println("Введите новое имя");
        user.setName(Util.getInputName());
    }

    private void setEmail(User user)
    {
        System.out.println("Введите новый email");
        user.setEmail(Util.getInputEmail());
    }

    private void setAge(User user)
    {
        System.out.println("Введите новый возраст");
        user.setAge(Util.getInputNumber());
    }
}
