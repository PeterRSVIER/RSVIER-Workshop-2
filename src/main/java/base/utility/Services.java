package base.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import base.Account;
import base.repository.AccountRepository;

@Service("userService")
public class Services {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public Services(BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
}

    public String createHash(String password) {
		return bCryptPasswordEncoder.encode(password);
        
    }

}
