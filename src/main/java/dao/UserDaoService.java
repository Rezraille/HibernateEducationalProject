package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.User;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import org.hibernate.exception.ConstraintViolationException;
import javax.persistence.PersistenceException;


public class UserDaoService
{
    private static final Logger logger  = Logger.getLogger(UserDaoService.class);
    private final UserDao userDao;
    private final DBService serviceBase;

    public UserDaoService(final UserDao userDao,DBService serviceBase)
    {
        this.userDao = userDao;
        this.serviceBase = serviceBase;
    }

    public Optional<User> getById(final Integer id)
    {
        logger.trace("getById() id = " + id);
        Transaction transaction = serviceBase.getTransaction();
        User user = null;
        try
        {
            user = userDao.getById(id);
            transaction.commit();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e)
        {
            serviceBase.transactionRollback(transaction);
            logger.error("Ошибка чтения из базы данных. Операция отменена.",e);
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> createUser(final User user)
    {
        logger.trace("createUser() = " + user);
        Transaction transaction = serviceBase.getTransaction();
        try
        {
            User entity = userDao.create(user);

            transaction.commit();
            return Optional.ofNullable(entity);
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e)
        {
            if (e.getCause() instanceof ConstraintViolationException)
            {
                System.out.printf("Запись %d с уникальным ключем существует в базе данных. Операция отменена.\n", user.getId());
            }
            serviceBase.transactionRollback(transaction);
            logger.error("Ошибка добавления в базу данных. Операция отменена.",e);
        }
        return Optional.ofNullable(user);
    }

    public void updateUser(final User user, int oldUserId)
    {
        logger.trace("updateUser() id = " + oldUserId + " with " + user);
        Transaction transaction = serviceBase.getTransaction();
        try
        {
            userDao.update(user, oldUserId);

            transaction.commit();

        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e)
        {
            serviceBase.transactionRollback(transaction);
            logger.error("Ошибка обновления базы данных. Операция отменена.",e);
        }
    }

    public boolean deleteById(final Integer id)
    {
        logger.trace("deleteById() id = " + id);
        Transaction transaction = serviceBase.getTransaction();
        try
        {
            userDao.deleteById(id);
            transaction.commit();
            return true;
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e)
        {
            serviceBase.transactionRollback(transaction);
            logger.error("Ошибка удаления из базы данных. Операция отменена.",e);
            return false;
        }
    }

    public List<User> findAll()
    {
        logger.trace("findAll()");
        Transaction transaction = serviceBase.getTransaction();
        List<User> users = new ArrayList<>();
        try
        {
            users = userDao.findAll();
            transaction.commit();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e)
        {
            serviceBase.transactionRollback(transaction);
            logger.error("Ошибка вывода из базы данных. Операция отменена.",e);
        }
        return users;
    }
}
