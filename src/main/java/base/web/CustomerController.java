package base.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import base.Customer;
import base.data.CustomerRepository;

@RequestMapping("/customer")
@Controller
public class CustomerController {

	
	CustomerRepository customerRepository;
	
	@Autowired
	public CustomerController(CustomerRepository customerRepository) {
	this.customerRepository = customerRepository;
}

	public List<Customer> customerList(){
	List<Customer> customerList = new ArrayList<>();
	customerRepository.findAll().iterator().forEachRemaining(customerList::add);
	return customerList;
	}

}
