package base.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import base.Account;
import base.repository.AccountRepository;

@Service("userService")
public class Services {

    private AccountRepository accountRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public Services(AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.accountRepository = accountRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
}

    public Account getAccountByEmail(String email) {
        Account account = accountRepository.getAccountByEmail(email);
        if (account!= null) {
        	return account;
        }
        throw new UsernameNotFoundException("No account with email '" + email + "has been found");
    }

    public String getHash(String password) {
		return bCryptPasswordEncoder.encode(password);
        
    }

}
