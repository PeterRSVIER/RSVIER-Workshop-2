package base.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import base.Customer;
import base.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>{
	public List<Order> findAllByCustomer_id(int id);
}
