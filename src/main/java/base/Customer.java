package base;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name = "customer")
@Data
public class Customer {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstname;
	private String middlename;
	private String surname;
	
	@OneToOne(cascade = {CascadeType.ALL}, mappedBy="customer")
	private Account account;

	@OneToMany(cascade = {CascadeType.ALL}, mappedBy="customer")
	private List<Order> orderList = new ArrayList<>();

}
