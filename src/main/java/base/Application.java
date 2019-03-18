package base;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import base.data.AccountRepository;
import base.data.AccountTypeRepository;
import base.data.CustomerRepository;

@SpringBootApplication
public class Application implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
  public CommandLineRunner dataLoader (CustomerRepository customerRepository, AccountTypeRepository accountTypeRepository, AccountRepository accountRepository ) {
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
    	  
    	Customer customer = new Customer();
  		customer.setFirstname("peter");
  		customer.setMiddlename("de");
  		customer.setSurname("Graaf");
	  	customerRepository.save(customer);
	  	
    	AccountType accountType1 = new AccountType();
    	accountType1.setDescription("Customer");
    		  		
    	AccountType accountType2 = new AccountType();
    	accountType2.setDescription("Worker");
    		  		
    	AccountType accountType3 = new AccountType();
    	accountType3.setDescription("Admin");
    		  		
    	accountTypeRepository.save(accountType1);
    	accountTypeRepository.save(accountType2);
    	accountTypeRepository.save(accountType3);
 
	    Account account = new Account();
	  	account.setEmail("AdminAccount@email.com");
	  	account.setHash("sha1:64000:18:IAYKe6SfpC4B7SoMryabWYhtAjMjNG9x:M1sKuTZ01MaoxTx8vOHayGc2");
	  	account.setCustomer(customer);
	  	account.setAccountType(accountType3);
	  	accountRepository.save(account);
	  }};}

}