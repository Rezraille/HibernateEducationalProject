package dao;

import entity.User;
import org.apache.log4j.Logger;

import javax.persistence.Query;
import java.util.List;

public class UserDao
{
    private static final Logger logger  = Logger.getLogger(UserDaoService.class);
    private final DBService serviceBase;

    public UserDao(DBService serviceBase)
    {
        logger.trace("UserDao()");
        this.serviceBase = serviceBase;
    }

    protected User getById(final Integer id)
    {
        logger.trace("getById() id = " + id);
        return serviceBase.getCurrentSession().get(User.class, id);
    }
    protected User create(final User entity)
    {
        logger.trace("create() user = " + entity);
        serviceBase.getCurrentSession().save(entity);
        return entity;
    }
    protected void update(final User entity, final int  oldUserId)
    {
        logger.trace("update() update = " + entity + ", id = " + oldUserId);
        String sql = "UPDATE users SET id = '%d', name = '%s', age = '%d', email = '%s', created_at = '%s' WHERE id = '%d'";
        Query query = serviceBase.getCurrentSession().createNativeQuery(
                String.format(sql, entity.getId(),entity.getName(),entity.getAge(),entity.getEmail(),entity.getCreatedAt(),oldUserId));
        query.executeUpdate();
        serviceBase.getCurrentSession().flush();
    }
    protected void deleteById(final Integer entityId)
    {
        logger.trace("deleteById() id = " + entityId);
        final User entity = getById(entityId);
        delete(entity);
    }
    protected List<User> findAll()
    {
        logger.trace("findAll()");
        return serviceBase.getCurrentSession().createQuery("from " + User.class.getSimpleName(), User.class).list();
    }
    private void delete(final User entity)
    {
        logger.trace("delete() user = " + entity);
        serviceBase.getCurrentSession().delete(entity);
    }
}
