package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public abstract class DBService
{
    private static final  SessionFactory sessionFactory = initializeSessionFactory();

    public static Transaction getCurrentTransaction()
    {

        Session session = DBService.getCurrentSession();
        Transaction transaction = session.getTransaction();
        if (transaction.isActive())
        {
            transaction.rollback();
        }
        return session.beginTransaction();
    }

    public static void transactionRollback(Transaction transaction)
    {
        if (transaction.getStatus() == TransactionStatus.ACTIVE
                || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK)
        {
            transaction.rollback();
        }
    }

    public static Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    private static SessionFactory initializeSessionFactory()
    {
        Configuration configuration = new Configuration();
        configuration.configure();
        return configuration.buildSessionFactory();
    }
    public static void closeSessionFactory()
    {
        sessionFactory.close();
    }
}
