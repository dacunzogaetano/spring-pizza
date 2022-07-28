package jana60.Model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;

@Entity
public class Ingredienti {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "Deve esserci almeno un ingrediente")
	@Column(nullable = false)
	private String name;

	@ManyToMany(mappedBy = "ingredienti")
	private List<Pizza> pizze;

	// get e set

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Pizza> getPizze() {
		return pizze;
	}

	public void setPizza(List<Pizza> pizze) {
		this.pizze = pizze;
	}

}
