package base.data;

import org.springframework.data.repository.CrudRepository;

import base.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>{

}
