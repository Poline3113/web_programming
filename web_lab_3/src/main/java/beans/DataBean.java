    package beans;

    import dao.Point;
    import jakarta.ejb.Stateless;
    import jakarta.persistence.EntityManager;
    import jakarta.persistence.PersistenceContext;
    import jakarta.persistence.TypedQuery;

    import java.util.List;

    @Stateless
    public class DataBean  {
        @PersistenceContext(unitName = "pu")
        private EntityManager entityManager;

        public void savePoint(String x, String y, String r, Boolean hit, String message) {
            Point point = new Point(x, y, r, hit, message);
            entityManager.getTransaction().begin();
            entityManager.persist(point);
            entityManager.getTransaction().commit();
        }

        public List<Point> loadPoints() {
            TypedQuery<Point> query = entityManager.createQuery("SELECT p FROM Point p", Point.class);
            return query.getResultList();
        }
    }