package base.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import base.Account;
import base.Account.AccountType;
import base.container.AccountListContainer;
import base.data.AccountRepository;

@RequestMapping("/editAccounts")
@Controller
public class AccountController {

	AccountRepository accountRepository;

	@Autowired
	public AccountController(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@GetMapping
	public String showEditForm(Model model) {
		List<Account> accountList = new ArrayList<>();
		accountRepository.findAll().iterator().forEachRemaining(accountList::add);
		model.addAttribute(accountList);
		List<AccountType> accountTypeList = new ArrayList<>();
		accountTypeList = Arrays.asList(Account.AccountType.values());
        model.addAttribute(accountTypeList);
		return "editAccounts";
	}
	
	// Is het mogelijk om 1 element uit een object op te slaan, dus zonder een heel nieuw account aan te maken?
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
