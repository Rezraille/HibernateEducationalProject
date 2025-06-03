import dao.UserDao;
import dao.UserDaoService;
import menu.MainMenu;
import dao.DBService;

public class Main
{
    public static void main(String[] args)
    {
        DBService dbService = new DBService();
        UserDao userDao = new UserDao(dbService);
        UserDaoService userDaoService = new UserDaoService(userDao,dbService);
        MainMenu menu = new MainMenu(userDaoService);
        menu.run();
        dbService.closeSessionFactory();
    }
}
