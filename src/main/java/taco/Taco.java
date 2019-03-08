package taco;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import taco.Ingredient;

@Data
public class Taco {

	private long id;
	private Date createdAt;
	
	@NotNull
	@Size(min=3, message="De naam moet minimaal 3 karakters lang zijn")
	private String name;
	
	@NotNull
	@Size(min=1, message="De Taco moet minimaal 1 ingredient bevatten")
	private List<Ingredient> ingredients;

}
