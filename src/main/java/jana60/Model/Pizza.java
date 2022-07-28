package jana60.Model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Pizza {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message = "La pizza deve avere un nome!")
	@Column(unique = true, nullable = false)
	private String name;
	
	@Lob
	private String description;
	
	@NotNull(message = "Purtroppo la pizza non é gratis")
	@Min(value = 0, message = "Un prezzo negativo, sei serio??")
	@Max(value = 50, message = "Le pizze cosi care stanno da Briatore")
	private Double price;
	
	@ManyToMany
	private List<Ingredienti> ingredienti;
	
	
	//get and set
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public List<Ingredienti> getIngredienti() {
		return ingredienti;
	}
	public void setIngredienti(List<Ingredienti> ingredienti) {
		this.ingredienti = ingredienti;
	}
	
	
	
	

}