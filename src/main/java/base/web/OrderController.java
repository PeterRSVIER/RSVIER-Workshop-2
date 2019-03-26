package base.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import base.Customer;
import base.Order;
import base.OrderLine;
import base.Product;
import base.data.OrderLineRepository;
import base.data.OrderRepository;

@RequestMapping("/order")
@Controller
@SessionAttributes({"selectedCustomer","orderLineList"})
public class OrderController {

CustomerController customerController;
OrderRepository orderRepository;
OrderLineRepository orderLineRepository;
ProductController productController;
	
	
OrderController (CustomerController customerController, OrderRepository orderRepository, OrderLineRepository orderLineRepository, ProductController productController){
	this.customerController = customerController;
	this.orderRepository = orderRepository;
	this.orderLineRepository = orderLineRepository;
	this.productController = productController;
}

@ModelAttribute 
public List<Customer> getCustomerList(){
	return customerController.customerList();
}

@ModelAttribute
public Customer customer() {
	 return new Customer();
}

@ModelAttribute (name = "orderLineList")
public List<OrderLine> orderLineList(){
	List<OrderLine> orderLineList = new ArrayList<>();
	return orderLineList;
}

@ModelAttribute (name = "selectedCustomer")
public Customer selectedCustomer(){
	return new Customer();
}

@ModelAttribute (name = "orderLine")
public OrderLine orderLine() {
	return new OrderLine();
}

@ModelAttribute (name = "productList")
public List<Product> productList() {
	return productController.productList();
}

@ModelAttribute
public Product product() {
	return productController.product();
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
	model.addAttribute("selectedCustomer", customer);
	System.out.println("selectedCustomer In PostMapping = " + customer);
	return "order/order";
}

@GetMapping (value = "order")
public String orderForm() {
	return "order/order";
}

@GetMapping(value = "create/")
public String createOrderForm(Model model){
	return "order/createOrder";
}

@PostMapping(value = "create/addOrderLine")
public String addOrderLineToList (OrderLine orderLine, @ModelAttribute("orderLineList") List<OrderLine> orderLineList, Model model) {
	orderLineList.add(orderLine);
	model.addAttribute("orderLineList", orderLineList);
	return "order/createOrder";
}

@PostMapping(value = "create/save")
public String createOrder(@ModelAttribute Customer selectedCustomer, @ModelAttribute List<OrderLine> orderLineList, Model model) {
	System.out.println("Entering create/save");
	System.out.println("selectedCustomer In create/save = " + selectedCustomer);
	System.out.println("orderLineList In create/save = " + orderLineList);
	Customer customer = customerController.customerRepository.findById(selectedCustomer.getId()).get();
	Order order = new Order();
	order.setCustomer(customer);
	order = orderRepository.save(order);
	for (int i = 0; i < orderLineList.size(); i++){
		OrderLine orderLine  = orderLineList.get(i);
		orderLine.setOrder(order);
		orderLineList.set(i, orderLine);
	}
	System.out.println("Saving List<OrderLine> =" + orderLineList);
	orderLineRepository.saveAll(orderLineList);
	return ("redirect:/medewerkers");
}

@PostMapping(value = "create/clear")
public String clearOrder(@ModelAttribute List<OrderLine> orderLineList, Model model) {
	orderLineList = new ArrayList<OrderLine>();
	model.addAttribute("orderLineList", orderLineList);
	return ("order/createOrder");
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
	return "order/deleteOrder";
}

@PostMapping(value = "delete")
public String confirmDeleteOrder(Order order, Model model) {
	try {
	orderLineRepository.deleteAll(orderLineRepository.findAllByOrderId(order.getId()));
	orderRepository.delete(order);
	}
	catch(DataIntegrityViolationException  e) {
		order = orderRepository.findById(order.getId()).get();
		model.addAttribute("orderToDelete", order);
		model.addAttribute("deleteOrderError", new String("Error: Cannot delete this order.)"));
		return ("order/deleteOrder");
	}
	return ("redirect:/medewerkers");
}
}
