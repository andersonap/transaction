package br.com.caju.transaction.controller;

import br.com.caju.transaction.controller.resources.TransactionRequest;
import br.com.caju.transaction.controller.resources.TransactionResponse;
import br.com.caju.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    @Autowired
    private final TransactionService service;

    public TransactionController(final TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@RequestBody final TransactionRequest request) {
        return ResponseEntity.ok(service.process(request));
    }
}
