package jana60.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jana60.Model.Ingredienti;

@Repository
public interface IngredientiRepository extends CrudRepository<Ingredienti, Integer> {
	
	public Integer countByName(String name); 
	public List<Ingredienti> findAllByOrderByName();

}
