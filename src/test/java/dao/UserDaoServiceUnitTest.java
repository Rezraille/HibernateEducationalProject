package dao;


import javax.persistence.PersistenceException;

import entity.User;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import static org.mockito.Mockito.doThrow;

public class UserDaoServiceUnitTest {

    private  UserDaoService userDaoService;
    @Mock
    private  UserDao userDao;
    @Mock
    private  DBService serviceBase;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userDaoService = new UserDaoService(userDao, serviceBase);
    }

    @Test
    public void getById_whenExceptionWithTransactionCommit() {
        int id = 3;
        Transaction transaction = Mockito.mock(Transaction.class);
        Mockito.doReturn(transaction).when(serviceBase).getTransaction();
        doThrow(new PersistenceException()).when(transaction).commit();
        Mockito.doNothing().when(serviceBase).transactionRollback(transaction);

        userDaoService.getById(id);

        Mockito.verify(serviceBase).transactionRollback(transaction);
    }


    @Test
    public void findAll_whenExceptionWithTransactionCommit() {
        Transaction transaction = Mockito.mock(Transaction.class);
        Mockito.doReturn(transaction).when(serviceBase).getTransaction();
        doThrow(new PersistenceException()).when(transaction).commit();
        Mockito.doNothing().when(serviceBase).transactionRollback(transaction);

        userDaoService.findAll();

        Mockito.verify(serviceBase).transactionRollback(transaction);
    }

    @Test
    public void createUser_whenNotConstraintViolationException() {
        Transaction transaction = Mockito.mock(Transaction.class);
        Mockito.doReturn(transaction).when(serviceBase).getTransaction();
        IllegalArgumentException constraintViolationException = Mockito.mock(IllegalArgumentException.class);
        doThrow(constraintViolationException).when(transaction).commit();
        Mockito.doNothing().when(serviceBase).transactionRollback(transaction);
        User user = Mockito.mock(User.class);
        userDaoService.createUser(user);

        Mockito.verify(serviceBase).transactionRollback(transaction);
    }
}
