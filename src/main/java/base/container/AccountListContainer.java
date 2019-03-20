package base.container;

import java.util.List;

import base.Account;
import lombok.Data;

@Data
public class AccountListContainer {

		public List<Account> accounts;
		
	    public AccountListContainer(List<Account> accounts) {
		this.accounts = accounts;
	}
	}

