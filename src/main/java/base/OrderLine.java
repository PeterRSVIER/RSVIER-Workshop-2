package base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "orderline")
@Data
public class OrderLine {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int amount;
	
	@OneToOne
	@JoinColumn(name="product_id", nullable=false)
	private Product product;

	@ManyToOne
	@JoinColumn(name="order_id", nullable=false)
	private Order order;
}
