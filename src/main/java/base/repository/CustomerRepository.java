package base.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import base.Account;
import base.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{
}
