package base;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import base.Account.AccountType;
import base.data.AccountRepository;
import base.data.CustomerRepository;
import base.data.OrderLineRepository;
import base.data.OrderRepository;
import base.data.ProductRepository;

@SpringBootApplication
public class Application implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/medewerkers").setViewName("medewerkers");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
  public CommandLineRunner dataLoader (CustomerRepository customerRepository, AccountRepository accountRepository, ProductRepository productRepository, OrderRepository orderRepository, OrderLineRepository orderLineRepository) {
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
    	  
    	Customer customer = new Customer();
  		customer.setFirstname("Peter");
  		customer.setMiddlename("de");
  		customer.setSurname("Graaf");
	  	customerRepository.save(customer);
	  	
    	Customer customer2 = new Customer();
  		customer2.setFirstname("Mathijs");
  		customer2.setMiddlename("de");
  		customer2.setSurname("Graaf");
	  	customerRepository.save(customer2);

    	Customer customer3 = new Customer();
  		customer3.setFirstname("Tom");
  		customer3.setMiddlename("de");
  		customer3.setSurname("Vos");
	  	customerRepository.save(customer3);
 
	    Account account = new Account();
	  	account.setEmail("AdminAccount@email.com");
	  	account.setPassword("AdminPassword");
	  	account.setCustomer(customer);
	  	account.setAccountType(AccountType.Admin);
	  	
	    Account account2 = new Account();
	  	account2.setEmail("CustomerAccount@email.com");
	  	account2.setPassword("CustomerPassword");
	  	account2.setCustomer(customer2);
	  	account2.setAccountType(AccountType.Customer);
	  	
	  	accountRepository.save(account);
	  	accountRepository.save(account2);
	  	
	  	Product product1 = new Product();
	  	product1.setName("Gloomhaven");
	  	product1.setStock(10);
	  	product1.setPrice(new BigDecimal("119.95"));
		productRepository.save(product1);
	  	
	  	Product product2 = new Product();
	  	product2.setName("Spirit Island");
	  	product2.setStock(20);
	  	product2.setPrice(new BigDecimal("69.95"));
		productRepository.save(product2);
		
		Order order1 = new Order();
		order1.setCustomer(customer);
		order1.setDate(LocalDateTime.now());
		order1.setTotalCost(new BigDecimal(100));
		orderRepository.save(order1);
		
		OrderLine orderLine1 = new OrderLine();
		orderLine1.setAmount(50);
		orderLine1.setProduct(product1);
		orderLine1.setOrder(order1);
		orderLineRepository.save(orderLine1);
		
	  }};}
}

