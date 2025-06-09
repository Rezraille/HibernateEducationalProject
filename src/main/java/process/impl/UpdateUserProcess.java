package process.impl;

import entity.User;
import dao.UserDaoService;
import process.Process;
import util.Util;

import java.util.Optional;

public class UpdateUserProcess  implements Process
{
    private final UserDaoService userDaoService;
    private final Util util;

    public UpdateUserProcess(UserDaoService userDaoService, Util util)
    {
        this.userDaoService = userDaoService;
        this.util = util;
    }

    @Override
    public void execute()
    {
        System.out.println("Запущен процесс изменения пользователя.");
        System.out.println("Введите id.");
        Integer oldId = util.getInputNumber();
        Optional<User> optionalUser = userDaoService.getById(oldId);
        if (optionalUser.isPresent())
        {
            changeId(optionalUser.get());
            changeName(optionalUser.get());
            changeEmail(optionalUser.get());
            changeAge(optionalUser.get());
            userDaoService.updateUser(optionalUser.get(),oldId);
            System.out.println("Пользователь успешно изменен.");
        } else
        {
            System.out.println("Пользователь для обновления не найден.");
        }

    }

    private void changeId(User user)
    {
        System.out.println("Чтобы изменить: id, Введите \"1\" - Да; \"2\" - Нет.");
        int choice = util.getInputNumber();
        if (choice == 1)
        {
            System.out.println("Введите новый id");
            user.setId(util.getInputNumber());
        }
    }

    private void changeName(User user)
    {
        System.out.println("Чтобы изменить: name, Введите \"1\" - Да; \"2\" - Нет.");
        if (util.getInputNumber() == 1)
        {
            System.out.println("Введите новое имя");
            user.setName(util.getInputName());
        }
    }

    private void changeEmail(User user)
    {
        System.out.println("Чтобы изменить: email, Введите \"1\" - Да; \"2\" - Нет.");
        if (util.getInputNumber() == 1)
        {
            System.out.println("Введите новый email");
            user.setEmail(util.getInputEmail());
        }
    }

    private void changeAge(User user)
    {
        System.out.println("Чтобы изменить: age, Введите \"1\" - Да; \"2\" - Нет.");
        if (util.getInputNumber() == 1)
        {
            System.out.println("Введите новый возраст");
            user.setAge(util.getInputNumber());
        }
    }

}
