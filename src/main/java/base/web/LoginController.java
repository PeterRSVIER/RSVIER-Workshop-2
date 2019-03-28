package base.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import base.Account;
import base.Account.AccountType;
import base.data.AccountRepository;
import base.data.CustomerRepository;

@Controller
@RequestMapping("/login")
public class LoginController {

	AccountRepository accountRepository;
	CustomerRepository customerRepository;

	@Autowired
	public LoginController(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@GetMapping
	public String login() {
		return "login";
	}

	@ModelAttribute("account")
	public Account getAccount() {
		return new Account();
	}

	@PostMapping
	public String CheckLoginInput(Account account, Model model) {

		Account accountFromDB = accountRepository.getAccountByEmail(account.getEmail());
		if (accountFromDB != null && accountFromDB.getPassword().equals(account.getPassword())) {

			if (accountFromDB.getAccountType() == AccountType.Worker
					|| accountFromDB.getAccountType() == AccountType.Admin) {
				model.addAttribute("account", accountFromDB);
				return "/medewerkers"; // Naar medewerker pagina
			}

			else {
				model.addAttribute("account", accountFromDB);
				return "/klanten"; // naar klanten
			}
		}

		model.addAttribute("incorrectLogin", new String ("De combinatie van het ingevoerde email en wachtwoord is incorrect"));

		return "login";

	}
}