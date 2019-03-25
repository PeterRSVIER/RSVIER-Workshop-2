package base.web;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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

	@ModelAttribute
	public List<Customer> getCustomerList() {
		return customerList();
	}

	@ModelAttribute
	public Customer customer() {
		return new Customer();
	}

	@GetMapping
	public String showCustomers() {
		return "customer/customer";
	}

	@GetMapping(value = "create")
	public String createCustomerForm(Model model) {
		return "customer/createCustomer";
	}

	@PostMapping(value = "create")
	public String createCustomer(Customer customer, Model model) {
		customerRepository.save(customer);
		return ("redirect:/medewerkers");
	}

	@GetMapping(value = "update/{id}")
	public String updateCustomer(@PathVariable("id") int id, Model model) {
		// Try block nodig?
		Customer customer = customerRepository.findById(id).get();
		model.addAttribute("customerToUpdate", customer);
		System.out.println("Customer in GetMapping delete/{id} =" + customer);
		return "customer/updateCustomer";
	}

	@PostMapping(value = "update")
	public String confirmUpdateCustomer(Customer customer, Model model) {
		System.out.println("updating customer:" + customer);
		customerRepository.save(customer);
		return ("redirect:/medewerkers");
	}

	@GetMapping(value = "delete/{id}")
	public String deleteCustomer(@PathVariable("id") int id, Model model) {
		// Try block nodig?
		Customer customer = customerRepository.findById(id).get();
		model.addAttribute("customerToDelete", customer);
		System.out.println("Customer in GetMapping delete/{id} =" + customer);
		return "customer/deleteCustomer";
	}

	@PostMapping(value = "delete/{id}")
	public String confirmDeleteCustomer(Customer customer, Model model) {
		try {
		customerRepository.delete(customer);
		}
		catch(DataIntegrityViolationException  e) {
			customer = customerRepository.findById(customer.getId()).get();
			model.addAttribute("customerToDelete", customer);
			model.addAttribute("deleteCustomerError", new String("Cannot delete this person. Delete the account of this person and try again."));
			return ("customer/deleteCustomer");
		}
		return ("redirect:/medewerkers");
	}

	public List<Customer> customerList() {
		List<Customer> customerList = new ArrayList<>();
		customerRepository.findAll().iterator().forEachRemaining(customerList::add);
		return customerList;
	}

}
