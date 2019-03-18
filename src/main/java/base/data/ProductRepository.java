package base.data;

import org.springframework.data.repository.CrudRepository;

import base.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>{

}
