package org.example.currencyexchange.controller;

import org.example.currencyexchange.rest.controller.AccountController;
import org.example.currencyexchange.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;

    @Test
    void shouldFailValidationWhenFieldsAreMissing() throws Exception {
        //Given
        String invalidRequest = """
                    {
                        "firstName": "John"
                        // "lastName" and "initBalancePLN" are missing
                    }
                """;

        // When & Then
        mockMvc.perform(post("/api/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

}
