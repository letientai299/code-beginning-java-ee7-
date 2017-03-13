package ltt;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author john
 */
public class Main {
  public static void main(String[] args) {
    Book book = new Book("H2G2", "Galaxy", 12f,
        "13123213123123", 354, false);

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch04");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    tx.begin();
    em.persist(book);
    tx.commit();
    em.close();
    emf.close();
  }
}
