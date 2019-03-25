package base.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import base.OrderLine;

public interface OrderLineRepository extends CrudRepository<OrderLine, Integer>{
}
