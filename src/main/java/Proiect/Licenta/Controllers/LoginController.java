package Proiect.Licenta.Controllers;

import Proiect.Licenta.Models.Account;
import Proiect.Licenta.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@CrossOrigin()
@RequestMapping(value = "/login")
public class LoginController {

    private final AccountService accountService;

    @Autowired
    public LoginController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping()
    public ResponseEntity<String> login(@RequestBody @Valid Account accountDTO) {
        System.out.println("username="+accountDTO.getUsername() + accountDTO.getPassword());
        String token = " { \"token\": \"" + accountService.login(accountDTO.getUsername(), accountDTO.getPassword()) + "\"} ";

        if(token != null)
            return new ResponseEntity<>(token, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


@PostMapping("/signup")
public ResponseEntity<String> signup(@RequestBody @Valid Account accountDto){
        String response;
        System.out.println("Suntem in create account");
    if(accountService.getAccountByName(accountDto.getUsername())==null){
   accountService.addAccount(accountDto.getUsername(), accountDto.getPassword(),"USER");
   response="\"OK\"" ;
   return new ResponseEntity<>(response,HttpStatus.OK);
    }
    else{
        response="\"Exist\"" ;
        return new ResponseEntity<>(response,HttpStatus.OK);}


}



}

