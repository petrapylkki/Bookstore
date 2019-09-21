package c2.Bookstore.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import c2.Bookstore.domain.Book;
import c2.Bookstore.domain.BookRepository;
import c2.Bookstore.domain.Category;
import c2.Bookstore.domain.CategoryRepository;

@Controller
public class BookstoreController {
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	CategoryRepository categoryRepo;
	
	@RequestMapping("*")
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
}
