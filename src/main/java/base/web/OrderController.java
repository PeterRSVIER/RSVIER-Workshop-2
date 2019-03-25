package base.web;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import base.Customer;
import base.Order;
import base.OrderLine;
import base.data.OrderLineRepository;
import base.data.OrderRepository;

@RequestMapping("/order")
@Controller
public class OrderController {

CustomerController customerController;
OrderRepository orderRepository;
OrderLineRepository orderLineRepository;
	
	
OrderController (CustomerController customerController, OrderRepository orderRepository, OrderLineRepository orderLineRepository){
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

public List<Order> getOrderListOfCustomer(int id){
	return orderRepository.findAllByCustomer_id(id);
}

@GetMapping
public String selectCustomer() {
	return "order/selectCustomer";
}

@PostMapping
public String selectCustomerPost(Customer customer, Model model) {
	model.addAttribute("orderListOfCustomer", getOrderListOfCustomer(customer.getId()));
	System.out.println(customer);
	return "order/order";
}

@GetMapping (value = "order")
public String orderForm() {
	return "order/order";
}

@GetMapping(value = "create")
public String createOrderForm(Model model) {
	return "order/createorder";
}

@PostMapping(value = "create")
public String createOrder(Order order, Model model) {
	orderRepository.save(order);
	return ("redirect:/medewerkers");
}

@GetMapping(value = "update/{id}")
public String updateOrder(@PathVariable("id") int id, Model model) {
	// Try block nodig?
	Order order = orderRepository.findById(id).get();
	model.addAttribute("orderToUpdate", order);
	return "order/updateOrder";
}

@PostMapping(value = "update")
public String confirmUpdateOrder(Order order, Model model) {
	orderRepository.save(order);
	return ("redirect:/medewerkers");
}

@GetMapping(value = "delete/{id}")
public String deleteOrder(@PathVariable("id") int id, Model model) {
	Order order = orderRepository.findById(id).get();
	model.addAttribute("orderToDelete", order);
	System.out.println("Order in GetMapping delete/{id} =" + order);
	return "order/deleteOrder";
}

@PostMapping(value = "delete")
public String confirmDeleteOrder(Order order, Model model) {
	System.out.println("order = " + order);
	int id = order.getId();
	try {
	System.out.println("FindAllByOrder = " + orderLineRepository.findAllByOrderId(id));
	}
	catch (Exception e) {
		System.out.println("Error in findAllOrderLiesOfOrder(id)");
	}
	try {
	orderLineRepository.deleteAll(orderLineRepository.findAllByOrderId(order.getId()));
	orderRepository.delete(order);
	}
	catch(DataIntegrityViolationException  e) {
		order = orderRepository.findById(order.getId()).get();
		model.addAttribute("orderToDelete", order);
		model.addAttribute("deleteOrderError", new String("Cannot delete this order. Delete the OrderLines of this order and try again."));
		return ("order/deleteOrder");
	}
	return ("redirect:/medewerkers");
}
}
