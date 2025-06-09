package dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class DBService
{
    private static final Logger logger  = Logger.getLogger(DBService.class);
    private final  SessionFactory sessionFactory;


    public DBService(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    public Transaction getTransaction()
    {
        logger.trace("getTransaction():");
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
        logger.trace("transactionRollback():");
        if (transaction.getStatus() == TransactionStatus.ACTIVE
                || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK)
        {
            transaction.rollback();
        }
    }

    public  Session getCurrentSession()
    {
        logger.trace("getCurrentSession()");
        return sessionFactory.getCurrentSession();
    }

   public static SessionFactory initializeSessionFactory()
    {
        logger.trace("initializeSessionFactory()");
        Configuration configuration = new Configuration();
        configuration.configure();
        return configuration.buildSessionFactory();
    }
    public  void closeSessionFactory()
    {
        logger.trace("closeSessionFactory()");
        sessionFactory.close();
    }
}
