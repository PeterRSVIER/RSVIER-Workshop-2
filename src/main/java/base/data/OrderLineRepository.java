package base.data;

import org.springframework.data.repository.CrudRepository;

import base.OrderLine;

public interface OrderLineRepository extends CrudRepository<OrderLine, Integer>{

}
