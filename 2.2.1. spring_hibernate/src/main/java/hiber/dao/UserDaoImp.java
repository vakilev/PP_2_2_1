package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        return sessionFactory.getCurrentSession().createQuery("from User").getResultList();
    }

    @Override
    public User getOwner(String model, int series) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Car where model = :model and series = :series", Car.class)
                .setParameter("model", model)
                .setParameter("series", series)
                .list()
                .get(0)
                .getOwner();


    }
}
