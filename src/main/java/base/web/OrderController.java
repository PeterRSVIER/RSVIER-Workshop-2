package base.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import base.Customer;
import base.data.OrderRepository;

@RequestMapping("/order")
@Controller
public class OrderController {

CustomerController customerController;
OrderRepository orderRepository;
	
	
OrderController (CustomerController customerController, OrderRepository orderRepository){
	this.customerController = customerController;
	this.orderRepository = orderRepository;
}
	
@ModelAttribute
public List<Customer> getCustomerList(){
	return customerController.customerList();
}

@ModelAttribute
public Customer customer() {
	 return new Customer();
}

@GetMapping
public String selectCustomer() {
	return "order/selectCustomer";
}

@PostMapping
public String selectCustomerPost(Customer customer) {
	System.out.println(customer);
	return "order/order";
	
}
@GetMapping (value = "order")
public String orderForm() {
	return "order/order";
}

}
