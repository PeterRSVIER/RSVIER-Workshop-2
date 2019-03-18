package base.data;

import org.springframework.data.repository.CrudRepository;

import base.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

}
