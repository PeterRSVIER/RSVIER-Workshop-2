package base.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import base.Account;
import base.Account.AccountType;
import base.Customer;
import base.container.AccountListContainer;
import base.data.AccountRepository;
import base.data.CustomerRepository;

@RequestMapping("/account")
@Controller
public class AccountController {

	AccountRepository accountRepository;
	CustomerController customerController;

	@Autowired
	public AccountController(AccountRepository accountRepository, CustomerController customerController) {
		this.accountRepository = accountRepository;
		this.customerController = customerController;
	}

	@ModelAttribute (name = "accountList")
	public List<Account> accountList(){
	List<Account> accountList = new ArrayList<>();
	accountRepository.findAll().iterator().forEachRemaining(accountList::add);
	return accountList;
	}
	
	@ModelAttribute (name = "account")
	public Account account() {
		return new Account();
	}
	
	@ModelAttribute (name = "accountTypeList")
	public List<AccountType> accountTypeList() {
		List<AccountType> accountTypeList = new ArrayList<>();
		accountTypeList = Arrays.asList(Account.AccountType.values());
		return accountTypeList;
	}
	
	@ModelAttribute (name = "customerList")
	public List<Customer> customerList(){
	List<Customer> customerList = customerController.customerList();
	return customerList;
	}
	
	@ModelAttribute (name = "customer")
	public Customer customer() {
	return new Customer();
	}
	
	@GetMapping()
	public String showEditForm(Model model) {
		return "account";
	}
	
	@GetMapping(value = "/create")
	public String createAccountForm(Model model) {
		return "createAccount";
	}

	@PostMapping(value = "/create")
	public String createAccount(Account account, Model model) {
		try {
			accountRepository.save(account);
		} catch (DataIntegrityViolationException e) {
			return "/createError";
		}
		return ("redirect:/medewerkers");

	}

	@GetMapping(value = "/delete/{id}")
	public String deleteAccount(@PathVariable("id") int id, Model model) {
		Optional<Account> optionalAccount = accountRepository.findById(id);
		Account account = optionalAccount.get();
		account.getCustomer().setAccount(null);
		// Try block?
		accountRepository.delete(account);
		return ("redirect:/medewerkers");
	}

	@PostMapping(value = "/save")
	public String saveAccounts(@ModelAttribute AccountListContainer form, Model model) {
		List<Account> list = (List<Account>) accountRepository.findAll();
		List<Account> newlist = form.getAccounts();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != newlist.get(i)) {
				Account account = list.get(i);
				account.setEmail(newlist.get(i).getEmail());
				account.setAccountType(newlist.get(i).getAccountType());
				list.set(i, account);
				accountRepository.save(account);
			}
		}
		return "medewerkers";
	}

	/*
	 * @RequestMapping(value = "/editAccounts", method = RequestMethod.GET) public
	 * String getAccounts(Model model) throws Exception { List<Account> accounts =
	 * getListOfAccounts(); AccountListContainer accountList = new
	 * AccountListContainer(); accountList.setAccounts(accounts);
	 * model.addAttribute("accounts", accountList); return "editAccounts"; }
	 */

	/*
	 * @RequestMapping(value = "/editAccounts", method = RequestMethod.GET) public
	 * String getAccounts(Model model) throws Exception {
	 * model.addAttribute("accounts", getListOfAccounts()); return "editAccounts"; }
	 * 
	 * // Dummy method for adding List of Users private List<Account>
	 * getListOfAccounts() { List<Account> accounts = new ArrayList<>();
	 * accountRepository.findAll().forEach(accounts::add); return accounts; }
	 */
}
