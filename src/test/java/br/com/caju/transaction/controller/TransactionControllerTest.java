package br.com.caju.transaction.controller;

import br.com.caju.transaction.controller.resources.TransactionRequest;
import br.com.caju.transaction.controller.resources.TransactionResponse;
import br.com.caju.transaction.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Autowired
    private ObjectMapper objectMapper;

    private TransactionRequest transactionRequest;

    @BeforeEach
    public void setup() {
        transactionRequest = TransactionRequest.builder()
                .accountId(1L)
                .amount(BigDecimal.valueOf(100))
                .mcc("5811")
                .merchant("PADARIA DO ZE               SAO PAULO BR")
                .build();
    }

    @Test
    public void createTransaction_ReturnsApproved() throws Exception {
        when(transactionService.process(transactionRequest)).thenReturn(TransactionResponse.approved());

        mockMvc.perform(post("/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("00"));
    }

    @Test
    public void createTransaction_ReturnsRejected() throws Exception {
        when(transactionService.process(transactionRequest)).thenReturn(TransactionResponse.rejected());

        mockMvc.perform(post("/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("51"));
    }

    @Test
    public void createTransaction_ReturnsGenericError() throws Exception {
        when(transactionService.process(transactionRequest)).thenReturn(TransactionResponse.genericError());

        mockMvc.perform(post("/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("07"));
    }

}