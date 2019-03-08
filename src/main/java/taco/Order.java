package taco;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;
import taco.Taco;

@Data
public class Order {

	private long id;
	private Date placedAt;
	
	@NotBlank(message="Een naam is nodig")
	private String name;
	
	@NotBlank(message="Een straat is nodig")
	private String street;
	
	@NotBlank(message="Een stad is nodig")
	private String city;
	
	@NotBlank(message="Een provincie is nodig")
	private String state;
	
	@NotBlank(message="Een postcode is nodig")
	private String zip;
	
	@CreditCardNumber(message="Een creditcard nummer is nodig")
	private String ccNumber;
	
	@Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
	           message="Vul MM/YY in")
	private String ccExpiration;
	
	@Digits(integer=3, fraction=0, message= "een gelidige CVV is nodig")
	private String ccCVV;
	
	  private List<Taco> tacos = new ArrayList<>();
	  
	  public void addDesign(Taco design) {
	    this.tacos.add(design);
	  }
}
