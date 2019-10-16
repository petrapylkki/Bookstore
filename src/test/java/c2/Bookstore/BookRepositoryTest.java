package c2.Bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import c2.Bookstore.domain.Book;
import c2.Bookstore.domain.BookRepository;
import c2.Bookstore.domain.Category;

@RunWith(SpringRunner.class)
@DataJpaTest

public class BookRepositoryTest {
	@Autowired
	private BookRepository repository;
	
	@Autowired
    private TestEntityManager em;
	
	@Test
	public void createNewBook() {
		Book book = new Book("Testikirja", "Kirjailija", "isbn", 2018, 20, new Category("Tietokirjat"));
		repository.save(book);
		assertThat(book.getId()).isNotNull();
	}

	@Test
	public void findByTitle() {
		List<Book> books = repository.findByTitle("Muumipappa merellä");
		assertThat(books).hasSize(1);
		assertThat(books.get(0).getAuthor()).isEqualTo("Tove Jansson");
	}
	
	@Test
    public void deleteById() {
        Book book = new Book("Testikirja", "Kirjailija", "isbn", 2018, 20, new Category("Tietokirjat"));
        final Long id = em.persistAndGetId(book, Long.class);
        repository.deleteById(id);
        em.flush();
        Book after = em.find(Book.class, id);
        assertThat(after).isNull();
    }
}
