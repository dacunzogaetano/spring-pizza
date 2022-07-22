package jana60.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jana60.Model.Pizza;

@Repository
public interface PizzaRepository extends CrudRepository<Pizza, Integer> {
	
	public Integer countByName(String name);

}
