package jana60.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jana60.Model.Pizza;
import jana60.Repository.IngredientiRepository;
import jana60.Repository.PizzaRepository;

@Controller
@RequestMapping("/")
public class PizzaController {

	@Autowired
	private PizzaRepository repo;

	@Autowired
	private IngredientiRepository ingredientiRepo;

	@GetMapping
	public String home(Model model) {
		return "/pizza/home";
	}

	@GetMapping("/list")
	public String pizzeList(Model model) {
		model.addAttribute("pizze", repo.findAll());
		return "/pizza/list";
	}

	@GetMapping("/add")
	public String pizzeForm(Model model) {
		model.addAttribute("pizza", new Pizza());
		model.addAttribute("ingredientiList", ingredientiRepo.findAllByOrderByName());
		return "/pizza/edit";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult br, Model model) {
		// testo se ci sono errori di validazione
		boolean hasErrors = br.hasErrors();
		boolean validateName = true;
		if (formPizza.getId() != null) { // sono in edit non in create
			Pizza pizzaBeforeUpdate = repo.findById(formPizza.getId()).get();
			if (pizzaBeforeUpdate.getName().equalsIgnoreCase(formPizza.getName())) {
				validateName = false;
			}
		}
		// testo se nome è univoco
		if (validateName && repo.countByName(formPizza.getName()) > 0) {
			br.addError(new FieldError("pizza", "name", "Non possono esserci pizze con lo stesso nome"));
			hasErrors = true;
		}

		if (hasErrors) {
			// se ci sono errori non salvo la pizza su database ma ritorno alla form
			// precaricata
			model.addAttribute("ingredientiList", ingredientiRepo.findAllByOrderByName());
			return "/pizza/edit";
		} else {
			// se non ci sono errori salvo la pizza che arriva dalla form
			try {
				repo.save(formPizza);
			} catch (Exception e) { // gestisco eventuali eccezioni sql
				model.addAttribute("errorMessage", "Non é possibile salvare questa pizza");
				model.addAttribute("ingredientiList", ingredientiRepo.findAllByOrderByName());
				return "/pizza/edit";
			}
			return "redirect:/list"; // non cercare un template, ma fai la HTTP redirect a quel path
		}
	}

	

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer pizzaId, RedirectAttributes ra) {
		Optional<Pizza> result = repo.findById(pizzaId);
		if (result.isPresent()) {
			// repo.deleteById(pizzaId);
			repo.delete(result.get());
			ra.addFlashAttribute("successMessage", "La pizza " + result.get().getName()
					+ " é stata cancellata! Mi auguro tu abbia avuto un buon motivo... ");
			return "redirect:/list";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La pizza non é presente nel Database");
		}

	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer pizzaId, Model model) {
		Optional<Pizza> result = repo.findById(pizzaId);
		if (result.isPresent()) {
			// preparo il template con al form passandogli la pizza trovato su db
			model.addAttribute("pizza", result.get());
			model.addAttribute("ingredientiList", ingredientiRepo.findAllByOrderByName());
			return "/pizza/edit";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La pizza non é presente nel Database");
		}

	}
}