package c2.Bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import c2.Bookstore.domain.Book;
import c2.Bookstore.domain.BookRepository;
import c2.Bookstore.domain.Category;
import c2.Bookstore.domain.CategoryRepository;

@SpringBootApplication
public class BookstoreApplication {

	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	//testidatan luonti H2-testitietokantaan aina sovelluksen käynnistyessä
	@Bean
	public CommandLineRunner bookDemo(BookRepository bookRepository, CategoryRepository categoryRepo) { 
		return (args) -> {
			log.info("save few books");
			categoryRepo.save(new Category("Lasten- ja nuortenkirjat"));
			categoryRepo.save(new Category("Kaunokirjallisuus"));
			categoryRepo.save(new Category("Tietokirjat"));
			
			bookRepository.save(new Book("Muumipappa merellä", "Tove Jansson", "1234-A", 1998, 23, categoryRepo.findByName("Lasten- ja nuortenkirjat").get(0)));
			bookRepository.save(new Book("Seitsemän veljestä", "Aleksis Kivi", "4321-B", 1932, 18, categoryRepo.findByName("Kaunokirjallisuus").get(0)));
			
			log.info("fetch all books");
			for (Book book : bookRepository.findAll()) {
				log.info(book.toString());
			}

		};
	}
}
