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

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        return sessionFactory.getCurrentSession().createQuery("from User").getResultList();
    }

    @Override
    public User getOwner(String model, int series) {
        List<Car> findingCar =
                sessionFactory.getCurrentSession()
                        .createQuery("from Car where model = :model and series = :series", Car.class)
                        .setParameter("model", model)
                        .setParameter("series", series)
                        .getResultList();

        if (!findingCar.isEmpty()) {

            Car car = findingCar.get(0);

            return listUsers()
                    .stream()
                    .filter(user -> user.getCar().equals(car))
                    .findAny()
                    .orElse(null);
        }

        return null;
    }

}
