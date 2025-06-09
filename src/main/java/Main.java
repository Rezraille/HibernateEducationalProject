import dao.UserDao;
import dao.UserDaoService;
import menu.MainMenu;
import dao.DBService;
import util.Util;

public class Main
{
    public static void main(String[] args)
    {
        DBService dbService = new DBService(DBService.initializeSessionFactory());
        UserDao userDao = new UserDao(dbService);
        UserDaoService userDaoService = new UserDaoService(userDao,dbService);
        Util util = new Util();
        MainMenu menu = new MainMenu(userDaoService, util);
        menu.run();
        dbService.closeSessionFactory();
    }
}
