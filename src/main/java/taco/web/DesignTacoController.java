// tag::head[]

package taco.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import taco.Ingredient;
import taco.Ingredient.Type;
import taco.Order;
import taco.Taco;
import taco.data.IngredientRepository;
import taco.data.TacoRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

	private final IngredientRepository ingredientRepository;
	private TacoRepository designRepository;

	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository designRepository) {
		this.ingredientRepository = ingredientRepository;
		this.designRepository = designRepository;
	}

	@ModelAttribute(name = "order")
	public Order oder() {
		return new Order();
	}

	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}

	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		List<Ingredient> ingredients = Arrays.asList(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
				new Ingredient("COTO", "Corn Tortilla", Type.WRAP), new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
				new Ingredient("CARN", "Carnitas", Type.PROTEIN),
				new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES), new Ingredient("LETC", "Lettuce", Type.VEGGIES),
				new Ingredient("CHED", "Cheddar", Type.CHEESE), new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
				new Ingredient("SLSA", "Salsa", Type.SAUCE), new Ingredient("SRCR", "Sour Cream", Type.SAUCE));

		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
	}

	/*
	 * @GetMapping public String showDesignForm(Model model) {
	 * model.addAttribute("design", new Taco()); return "design"; }
	 */

	/*
	 * Deze methode draait niet goed; geeft:
	 * 
	 * Browser:An error happened during template parsing (template:
	 * "class path resource [templates/design.html]")
	 * 
	 * Log: Error during execution of processor
	 * 'org.thymeleaf.spring5.processor.SpringInputGeneralFieldTagProcessor'
	 * (template: "design" - line 65, col 25) Caused by:
	 * java.lang.IllegalStateException: Neither BindingResult nor plain target
	 * object for bean name 'design' available as request attribute
	 * 
	 */
	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepository.findAll().forEach(i -> ingredients.add(i));

		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
		return "design";
	}

	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
		if (errors.hasErrors()) {
			log.info("ErrorMessage is: " + errors);
			return "design";
		}

		Taco saved = designRepository.save(design);
		order.addDesign(saved);
		return "redirect:/orders/current";
	}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
	}
}
