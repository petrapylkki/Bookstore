package c2.Bookstore.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import c2.Bookstore.domain.Book;
import c2.Bookstore.domain.BookRepository;
import c2.Bookstore.domain.CategoryRepository;

@Controller
public class BookstoreController {
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	CategoryRepository categoryRepo;
	
	@RequestMapping("/")
	public String frontPage() {
		return "redirect:/booklist";
	}
		
	@GetMapping(value = "/booklist")
	public String getBooks(Model model) {
		List<Book> books =  (List<Book>) bookRepository.findAll();
		model.addAttribute("books", books);
		return "booklist"; 
	}
	
	@GetMapping(value = "/deletebook/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteBook(@PathVariable("id") Long id) {
		bookRepository.deleteById(id);
		return "redirect:../booklist";
	}
	
	@GetMapping(value="/editbook/{id}")
	public String addBook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("book", bookRepository.findById(id));
		return "editbook";
	}
	
	@GetMapping(value = "/addbook")
	public String addBook(Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("category", categoryRepo.findAll());
		return "addbook";
	}
	
	@PostMapping(value = "/addbook")
	public String saveBook(@ModelAttribute Book book) {
		bookRepository.save(book);
		return "redirect:/booklist";
	}

	//REST-metodi, jotka palauttaa JSON-muodossa kaikki listatut kirjat kirjaluokasta
	@GetMapping(value="/books")
	public @ResponseBody List<Book> bookListRest() {
		return(List<Book>) bookRepository.findAll();
	}
	
	//REST-metodi, joka palauttaa kirjaluokasta id:n perusteella yhden kirjan
	@GetMapping(value="/book/{id}")
	public @ResponseBody Optional<Book> findBookRest(@PathVariable("id")Long id) {
		return bookRepository.findById(id);
	}
	@RequestMapping(value="/login")
    public String login() {	
        return "login";
    }
}
