package dao;

import entity.User;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.PersistenceException;
import java.util.List;

public class UserDaoService
{
    private final UserDao userDao;

    public UserDaoService(final UserDao userDao)
    {
        this.userDao = userDao;
    }

    public User getById(final Integer id)
    {
        Transaction transaction = DBService.getCurrentTransaction();
        User user = null;
        try
        {
            user = userDao.getById(id);
            transaction.commit();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e)
        {
            DBService.transactionRollback(transaction);
            System.out.println("Ошибка чтения из базы данных. Операция отменена.");
        }
        return user;
    }

    public User createUser(final User user)
    {
        Transaction transaction = DBService.getCurrentTransaction();
        try
        {
            User entity = userDao.create(user);

            transaction.commit();

            return entity;
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e)
        {
            if (e.getCause() instanceof ConstraintViolationException)
            {
                System.out.printf("Запись с уникальным ключем существует в базе данных. Операция отменена.\n", user.getId());
            }
            System.out.println("Ошибка добавления в базу данных. Операция отменена.");
            DBService.transactionRollback(transaction);
        }
        return null;
    }

    public void updateUser(final User user, int oldUserId)
    {
        Transaction transaction = DBService.getCurrentTransaction();
        try
        {
            userDao.update(user, oldUserId);

            transaction.commit();

        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e)
        {
            DBService.transactionRollback(transaction);
            System.out.println("Ошибка обновления базы данных. Операция отменена.");
        }
    }

    public boolean deleteById(final Integer id)
    {
        Transaction transaction = DBService.getCurrentTransaction();
        try
        {
            userDao.deleteById(id);
            transaction.commit();
            return true;
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e)
        {
            DBService.transactionRollback(transaction);
            System.out.println("Ошибка удаления из базы данных. Операция отменена.");
            return false;
        }
    }

    public List<User> findAll()
    {
        Transaction transaction = DBService.getCurrentTransaction();
        try
        {
            List<User> users = userDao.findAll();
            transaction.commit();
            return users;
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e)
        {
            DBService.transactionRollback(transaction);
            System.out.println("Ошибка вывода из базы данных. Операция отменена.");
        }
        return null;
    }
}
