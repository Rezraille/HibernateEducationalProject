package process.impl;

import entity.User;
import dao.UserDaoService;
import process.Process;
import util.Util;

import java.time.LocalDateTime;
import java.util.Optional;


public class CreateUserProcess implements Process
{
    private final UserDaoService userDaoService;
    private final Util util;

    public CreateUserProcess(UserDaoService userDaoService, Util util)
    {
        this.userDaoService = userDaoService;
        this.util = util;
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
            Optional<User> optionalNewUser = userDaoService.createUser(user);
            if (!optionalNewUser.isPresent())
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
        user.setId(util.getInputNumber());
    }

    private void setName(User user)
    {
        System.out.println("Введите новое имя");
        user.setName(util.getInputName());
    }

    private void setEmail(User user)
    {
        System.out.println("Введите новый email");
        user.setEmail(util.getInputEmail());
    }

    private void setAge(User user)
    {
        System.out.println("Введите новый возраст");
        user.setAge(util.getInputNumber());
    }
}
