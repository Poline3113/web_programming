package dao;

import beans.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.Optional;

@Stateless
@Transactional
public class UserData {
    @PersistenceContext
    private EntityManager entityManager;

    public void saveUser(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }

    public Optional<User> findByUsername(String username) {
        User user = entityManager.find(User.class, username);
        return Optional.ofNullable(user);
    }

    public boolean checkUserIfExists(String username) {
        User user = entityManager.find(User.class, username);
        return user != null;
    }
}
