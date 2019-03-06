package taco;

import taco.Ingredient.Type;
import lombok.Data;
import lombok.RequiredArgsConstructor;

	@Data
	@RequiredArgsConstructor
	public class Ingredient {

		private final String id;
		private final String name;
		private final Type type;
		
		public Ingredient(String id, String name, Type type){
		this.id = id;
		this.name = name;
		this.type = type;
		}
		

		
		
		public static enum Type {
			WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
		}
}
