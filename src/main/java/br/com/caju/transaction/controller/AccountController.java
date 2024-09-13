package br.com.caju.transaction.controller;

import br.com.caju.transaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping("/test")
    public ResponseEntity<String> createTestAccount() {
        Long accountId = service.createTestAccount();
        return ResponseEntity.ok("accountId=" + accountId.toString());
    }
}
