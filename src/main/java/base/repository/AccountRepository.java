package base.repository;

import org.springframework.data.repository.CrudRepository;

import base.Account;

public interface AccountRepository extends CrudRepository<Account, Integer>{
	 public Account getAccountByEmail(String email);
}
