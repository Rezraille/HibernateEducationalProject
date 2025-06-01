import menu.MainMenu;
import dao.DBService;

public class Main
{
    public static void main(String[] args)
    {
        MainMenu menu = new MainMenu();
        menu.run();
        DBService.closeSessionFactory();
    }
}
