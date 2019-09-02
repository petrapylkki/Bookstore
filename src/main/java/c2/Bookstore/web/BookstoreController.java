package c2.Bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookstoreController {
	@GetMapping("/index")
	public String sayHello(@RequestParam(value="name")String name, @RequestParam(value="age")String age, Model model) {
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		return "bookstoreIndex";
	}
}
