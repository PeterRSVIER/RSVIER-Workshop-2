package base;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import base.Account.AccountType;
import base.repository.AccountRepository;
import base.repository.CustomerRepository;
import base.repository.OrderLineRepository;
import base.repository.OrderRepository;
import base.repository.ProductRepository;
import base.utility.Services;


@SpringBootApplication
@EnableWebSecurity
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
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
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
	  	Services services = new Services(accountRepository, new BCryptPasswordEncoder());
	  	account.setPassword(services.getHash("AdminPassword"));
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
		
	  	Product product3 = new Product();
	  	product3.setName("Legends of Andor");
	  	product3.setStock(20);
	  	product3.setPrice(new BigDecimal("44.95"));
		productRepository.save(product3);
		
	  	Product product4 = new Product();
	  	product4.setName("Ticket To Ride: Europe");
	  	product4.setStock(20);
	  	product4.setPrice(new BigDecimal("49.95"));
		productRepository.save(product4);
		
	  	Product product5 = new Product();
	  	product5.setName("Monopoly");
	  	product5.setStock(20);
	  	product5.setPrice(new BigDecimal("30.00"));
		productRepository.save(product5);
		
	  	Product product6 = new Product();
	  	product6.setName("Through The Ages");
	  	product6.setStock(20);
	  	product6.setPrice(new BigDecimal("49.95"));
		productRepository.save(product6);
		
	  	Product product7 = new Product();
	  	product7.setName("Scythe");
	  	product7.setStock(20);
	  	product7.setPrice(new BigDecimal("68.50"));
		productRepository.save(product7);
		
	  	Product product8 = new Product();
	  	product8.setName("Mage Knight");
	  	product8.setStock(20);
	  	product8.setPrice(new BigDecimal("74.95"));
		productRepository.save(product8);
		
	  	Product product9 = new Product();
	  	product9.setName("Dominant Species");
	  	product9.setStock(20);
	  	product9.setPrice(new BigDecimal("99.90"));
		productRepository.save(product9);
		
	  	Product product10 = new Product();
	  	product10.setName("Stef Stuntpiloot");
	  	product10.setStock(20);
	  	product10.setPrice(new BigDecimal("20.00"));
		productRepository.save(product10);
		
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

