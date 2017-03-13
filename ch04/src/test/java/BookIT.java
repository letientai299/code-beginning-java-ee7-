import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import ltt.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author john
 */
public class BookIT {

  private static EntityManagerFactory emf =
      Persistence.createEntityManagerFactory("ch04Test");
  private EntityManager em;
  private EntityTransaction tx;

  @Before public void setup() {
    em = emf.createEntityManager();
    tx = em.getTransaction();
  }

  @After public void teardown() {
    if (em != null) {
      em.close();
    }
  }

  @Test
  public void listAll() throws Exception {
    List<Book> results = em
        .createQuery("SELECT b FROM Book b", Book.class)
        .getResultList();
    System.out.println(results);
  }

  @Test
  public void shouldFindJavaEE7Book() throws Exception {
    Book book = em.find(Book.class, 1001L);
    assertEquals("Beginning Java EE 7", book.getTitle());
  }

  @Test
  public void shouldCreatedH2G2Book() throws Exception {
    // Creates an instance of book
    Book book = new Book("H2G2", "The Hitchhiker's Guide to the Galaxy",
        12.5F, "1-84023-742-2", 354, false);
    // Persists the book to the database
    tx.begin();
    em.persist(book);
    tx.commit();
    assertNotNull("ID should not be null", book.getId());
    // Retrieves all the books from the database
    book = em.createNamedQuery("findBookH2G2", Book.class).getSingleResult();
    assertEquals("The Hitchhiker's Guide to the Galaxy", book.getDescription());
  }

  @Test
  public void shouldRaiseConstraintViolationCauseNullTitle() throws Exception {
    Book book =
        new Book(null, "Null title, should fail", 12.5f, "3123123123", 354,
            false);
    em.persist(book);
  }
}
