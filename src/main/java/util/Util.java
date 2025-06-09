package util;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Util
{
    private Scanner scanner = new Scanner(System.in);

    public Integer getInputNumber()
    {
        Integer number = null;
        while (true)
        {
            try
            {
                number = Integer.parseInt(scanner.nextLine());
                if (number >= 0)
                {
                    return number;
                }
                else
                {
                    System.out.println("Не верный формат числа. Попробуйте снова.");
                }

            } catch (NumberFormatException exception)
            {
                System.out.println("Не верный формат числа. Попробуйте снова.");
            } catch (Exception exception)
            {
                System.out.println("Ошибка. Попробуйте снова");
            }
        }
    }

    public String getInputEmail()
    {
        String regex = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+";
        Pattern pattern = Pattern.compile(regex);
        String input = null;
        while (true)
        {
            try
            {
                input = scanner.nextLine().trim();
                if (pattern.matcher(input).matches())
                {
                    return input;
                }
                else
                {
                    System.out.println("Не верный формат почты. Попробуйте снова.");
                }
            } catch (Exception exception)
            {
                System.out.println("Ошибка. Попробуйте снова.");
            }
        }
    }

    public String getInputName()
    {
        String regex = "[\\p{L}]+";
        Pattern pattern = Pattern.compile(regex);
        String input = null;
        while (true)
        {
            try
            {
                input = scanner.nextLine().trim();
                if (pattern.matcher(input).matches())
                {
                    return input;
                }
                else
                {
                    System.out.println("Не верный формат имени. Попробуйте снова.");
                }

            }
            catch (Exception exception)
            {
                System.out.println("Ошибка. Попробуйте снова.");
            }
        }
    }
    void setScanner(Scanner scanner)
    {
        this.scanner = scanner;
    }
}
