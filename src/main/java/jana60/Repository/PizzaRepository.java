package jana60.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jana60.Model.Ingredienti;
import jana60.Model.Pizza;



@Repository
public interface PizzaRepository extends CrudRepository<Pizza, Integer> {

	public Integer countByName(String name);
	public List<Pizza> findAllByOrderByName();
	public List<Pizza> findByNameContainingIgnoreCase(String queryName);
	public List<Pizza> findByPriceBetweenOrderByPrice(Double queryMin, Double queryMax);
	//public List<Pizza> findByIngredienti(List<Ingredienti> queryIngrediente);
}
