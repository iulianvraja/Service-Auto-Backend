package Proiect.Licenta.Services;


import Proiect.Licenta.Models.Account;
import Proiect.Licenta.Repositories.AccountRepository;
import Proiect.Licenta.security.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class AccountService {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtProvider jwtProvider;
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository repo;

    @Autowired
    public AccountService(AccountRepository accountRepository, AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.repo = accountRepository;
        this.authenticationManager = authenticationManager;

        this.passwordEncoder = passwordEncoder;

        this.jwtProvider = jwtProvider;

    }

    public List<Account> getall() {
        return repo.findAll();
    }

    public String login(String username, String password) {

        String token = "";
        Account user = repo.findByUsername(username);
        System.out.println("usernmae=" + user.getRole());

        if (user != null) {
            try {

                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                System.out.println("provide token");
                token = jwtProvider.createToken(user.getAccount_id(), user.getRole());
            } catch (Exception e) {
                System.out.println("can t provide token");
                LOGGER.info("Log in failed for user {}", username);
            }
        }
        System.out.println("token=" + token);
        return token;
    }



    //delete
    public void delete(Integer id) {
        repo.deleteById(id);
    }
    // update
    public Account update(String username, Account account) {
        LOGGER.info("User want to update his account!");
        Account updatedAccount = repo.findByUsername(username);
        if (repo.findByUsername(username) != null) {
            updatedAccount.setPassword(passwordEncoder.encode(account.getPassword()));
            updatedAccount.setRole(account.getRole());
        }
        return updatedAccount;
    }
    public Account addAccount(String username, String password, String role) {
        LOGGER.info("New user attempting to sign in");
        Optional<Account> user = Optional.empty();
        if (repo.findByUsername(username) == null) {
            return repo.save(new Account(username,
                    passwordEncoder.encode(password),
                    role));
        }
        else
            return null;
    }

    public void updateaccount(Account x){
        Account y= repo.findById(x.getAccount_id()).get();
        if(y.getPassword().equals(x.getPassword())){
         repo.save(x);
        }
        else
        {
            x.setPassword(passwordEncoder.encode(x.getPassword()));
            repo.save(x);
        }
    }
    public Account getAccountByName(String name) {
        return repo.findByUsername(name);

    }

    public Account getaccbyid(int id){
        return  repo.findById(id).get();
    }

}
