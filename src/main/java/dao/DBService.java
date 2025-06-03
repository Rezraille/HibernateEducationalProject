package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class DBService
{
    private final  SessionFactory sessionFactory = initializeSessionFactory();


    public Transaction getTransaction()
    {

        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        if (transaction.isActive())
        {
            transaction.rollback();
        }
        return session.beginTransaction();
    }

    public void transactionRollback(Transaction transaction)
    {
        if (transaction.getStatus() == TransactionStatus.ACTIVE
                || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK)
        {
            transaction.rollback();
        }
    }

    public  Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    private static SessionFactory initializeSessionFactory()
    {
        Configuration configuration = new Configuration();
        configuration.configure();
        return configuration.buildSessionFactory();
    }
    public  void closeSessionFactory()
    {
        sessionFactory.close();
    }
}
