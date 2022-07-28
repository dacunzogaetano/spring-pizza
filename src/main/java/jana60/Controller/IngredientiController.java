package jana60.Controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jana60.Model.Ingredienti;
import jana60.Repository.IngredientiRepository;

@Controller
@RequestMapping("/ingredienti")
public class IngredientiController {
	
	@Autowired
	private IngredientiRepository repo;
	
	

	@GetMapping
	public String ingredientiList(Model model) {
		model.addAttribute("ingredienti", repo.findAllByOrderByName());
		model.addAttribute("newIngredienti", new Ingredienti());
		return "/ingredienti/list";
	}

	@PostMapping("/save")
	public String saveIngredienti(@Valid @ModelAttribute("newIngredienti") Ingredienti formIngredienti, BindingResult br,
			Model model) {
		if (br.hasErrors()) {
			model.addAttribute("ingredienti", repo.findAllByOrderByName());
			return "ingredienti/list";

		} else {
			repo.save(formIngredienti);
			return "redirect:/ingredienti";
		}

	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer ingredientiId, RedirectAttributes ra) {
		Optional<Ingredienti> result = repo.findById(ingredientiId);
		if (result.isPresent()) {
			// repo.deleteById(ingredientiId);
			repo.delete(result.get());
			ra.addFlashAttribute("successMessage", "La pizza " + result.get().getName() + " é stata cancellata! Mi auguro tu abbia avuto un buon motivo... ");
			return "redirect:/ingredienti";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La pizza non é presente nel Database");
		}

	}
}