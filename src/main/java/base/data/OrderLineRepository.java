package base.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import base.OrderLine;

public interface OrderLineRepository extends CrudRepository<OrderLine, Integer>{

	
	@Query("SELECT o FROM OrderLine o WHERE o.order_id = ?1")
	public List<OrderLine> findAllByOrderId(int id);
}
