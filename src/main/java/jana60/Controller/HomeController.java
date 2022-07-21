package jana60.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jana60.Repository.PizzaRepository;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private PizzaRepository repo;
	
	@GetMapping
	public String home (Model model)
	{
		return "home";
	}

}
