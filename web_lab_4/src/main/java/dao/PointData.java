package dao;

import beans.Point;
import beans.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Stateless
@Transactional
public class PointData {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Point point) {
        entityManager.persist(point);
        entityManager.flush();
    }

    public void clearByUsername(String username) {
        entityManager.createNamedQuery("Point.deleteByUsername")
                .setParameter("username", username)
                .executeUpdate();
    }

    public List<Point> findAllByOwner(String username) {
        return entityManager.createNamedQuery("Point.findByUsername", Point.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Point> findAllPoints() {
        return entityManager.createNamedQuery("Point.findAll", Point.class)
                .getResultList();
    }
}