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
				return "redirect:/medewerkers"; // Naar medewerker pagina
			}

			if (accountFromDB.getAccountType() == AccountType.Customer) {
				model.addAttribute("account", accountFromDB);
				return "redirect:/klanten"; // naar klanten
			}
		}

		/// Onderstaande code print input en returned account naar browser
		String incorrectLogin = "Het email of wachtwoord is incorrect.";
		String inputAccount = "Ingevoerde account = " + account.toString();
		String isNull = "Opgehaalde account = null";
		model.addAttribute("incorrectLogin", incorrectLogin);
		model.addAttribute("inputAccount", inputAccount);
		if (accountFromDB != null) {
			String returnAccount = accountFromDB.toString();
			model.addAttribute("Opgehaalde account =", returnAccount);
		}
		
		else {
		model.addAttribute("returnAccount", isNull);
		}
		
// 		Read By ID geeft hier ook een nullpointer terug
		
///		String defaultAccount = "Default Account =" + accountRepository.findById(1).toString();
///		model.addAttribute("defaultAccount", defaultAccount);
		return "login";

	}
}