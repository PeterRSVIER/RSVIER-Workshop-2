package base.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import base.Account;
import base.Account.AccountType;
import base.Customer;
import base.container.AccountListContainer;
import base.repository.AccountRepository;
import base.repository.CustomerRepository;
import base.utility.Services;

@RequestMapping("/account")
@Controller
public class AccountController {

	AccountRepository accountRepository;
	CustomerController customerController;
	Services services;

	@Autowired
	public AccountController(AccountRepository accountRepository, CustomerController customerController, Services services) {
		this.accountRepository = accountRepository;
		this.customerController = customerController;
		this.services = services;
	}

	@ModelAttribute(name = "accountList")
	public List<Account> accountList() {
		List<Account> accountList = new ArrayList<>();
		accountRepository.findAll().iterator().forEachRemaining(accountList::add);
		return accountList;
	}

	@ModelAttribute(name = "account")
	public Account account() {
		return new Account();
	}

	@ModelAttribute(name = "accountTypeList")
	public List<AccountType> accountTypeList() {
		List<AccountType> accountTypeList = new ArrayList<>();
		accountTypeList = Arrays.asList(Account.AccountType.values());
		return accountTypeList;
	}

	@ModelAttribute(name = "customerListWithoutAccount")
	public List<Customer> customerListWithoutAccount() {
		
		List<Customer> customerList = customerController.customerList();
		List<Account> accountList = (List<Account>) accountRepository.findAll();
		Set<Customer> customersInAccountList = accountList.stream().map(Account::getCustomer)
				.collect(Collectors.toSet());
		return customerList.stream().filter(c -> !customersInAccountList.contains(c)).collect(Collectors.toList());
	}

	@ModelAttribute(name = "customer")
	public Customer customer() {
		return new Customer();
	}

	@GetMapping()
	public String showEditForm(Model model) {
		return "account/account";
	}

	@GetMapping(value = "/create")
	public String createAccountForm(Model model) {
		return "account/createAccount";
	}

	@PostMapping(value = "/create")
	public String createAccount(@Valid Account account, Errors errors, Model model) {
		if (errors.hasErrors()) {
		}
		
		account.setPassword(services.createHash(account.getPassword()));

		try {
			accountRepository.save(account);
		} catch (DataIntegrityViolationException e) {
			return "redirect:/login";
		}
		return ("redirect:/medewerkers");

	}

	@GetMapping(value = "/delete/{id}")
	public String deleteAccount(@PathVariable("id") int id, Model model) {
		Optional<Account> optionalAccount = accountRepository.findById(id);
		Account account = optionalAccount.get();
		// Try block?
		accountRepository.delete(account);
		return ("redirect:/medewerkers");
	}

	@PostMapping(value = "/save")
	// AccountListContainer hier evt. nog wegwerken, krijg hem niet aan de praat met List<Account>
	public String saveAccounts(AccountListContainer accountListContainer, Model model) {
		List<Account> list = (List<Account>) accountRepository.findAll();
		List<Account> newList = accountListContainer.getAccounts();
		System.out.println("oldList = " + list);
		System.out.println("newList = " + newList);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != newList.get(i)) {
				Account account = list.get(i);
				account.setEmail(newList.get(i).getEmail());
				account.setAccountType(newList.get(i).getAccountType());
				list.set(i, account);
				accountRepository.save(account);
			}
		}
		return "medewerkers";
	}
}
