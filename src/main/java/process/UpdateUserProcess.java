package process;

import entity.User;
import dao.UserDaoService;
import util.Util;

public class UpdateUserProcess
{
    private final UserDaoService userDaoService;

    public UpdateUserProcess(UserDaoService userDaoService)
    {
        this.userDaoService = userDaoService;
    }

    public void execute()
    {
        System.out.println("Запущен процесс изменения пользователя.");
        System.out.println("Введите id.");
        Integer oldId = Util.getInputNumber();
        User user = userDaoService.getById(oldId);
        if (user != null)
        {
            changeId(user);
            chahgeName(user);
            chahgeEmail(user);
            chahgeAge(user);
            userDaoService.updateUser(user,oldId);
            System.out.println("Пользователь успешно изменен.");
        } else
        {
            System.out.println("Пользователь для обновления не найден.");
        }

    }

    private void changeId(User user)
    {
        System.out.println("Чтобы изменить: id, Введите \"1\" - Да; \"2\" - Нет.");
        int choice = Util.getInputNumber();
        if (choice == 1)
        {
            System.out.println("Введите новый id");
            user.setId(Util.getInputNumber());
        }
    }

    private void chahgeName(User user)
    {
        System.out.println("Чтобы изменить: name, Введите \"1\" - Да; \"2\" - Нет.");
        if (Util.getInputNumber() == 1)
        {
            System.out.println("Введите новое имя");
            user.setName(Util.getInputName());
        }
    }

    private void chahgeEmail(User user)
    {
        System.out.println("Чтобы изменить: email, Введите \"1\" - Да; \"2\" - Нет.");
        if (Util.getInputNumber() == 1)
        {
            System.out.println("Введите новый email");
            user.setEmail(Util.getInputEmail());
        }
    }

    private void chahgeAge(User user)
    {
        System.out.println("Чтобы изменить: age, Введите \"1\" - Да; \"2\" - Нет.");
        if (Util.getInputNumber() == 1)
        {
            System.out.println("Введите новый возраст");
            user.setAge(Util.getInputNumber());
        }
    }

}
