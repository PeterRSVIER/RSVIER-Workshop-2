package base;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import base.Account.AccountType;
import base.data.AccountRepository;
import base.data.CustomerRepository;

@SpringBootApplication
public class Application implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/login").setViewName("login");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
  public CommandLineRunner dataLoader (CustomerRepository customerRepository, AccountRepository accountRepository ) {
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
    	  
    	Customer customer = new Customer();
  		customer.setFirstname("Peter");
  		customer.setMiddlename("de");
  		customer.setSurname("Graaf");
	  	customerRepository.save(customer);
	  	
    	Customer customer2 = new Customer();
  		customer.setFirstname("Mathijs");
  		customer.setMiddlename("de");
  		customer.setSurname("Graaf");
	  	customerRepository.save(customer2);
 
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
	  }};}

}