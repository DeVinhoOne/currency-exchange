package org.example.currencyexchange.controller;

import org.example.currencyexchange.rest.controller.CurrencyExchangeController;
import org.example.currencyexchange.service.CurrencyExchangeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CurrencyExchangeController.class)
class ExchangeControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CurrencyExchangeService currencyExchangeService;

    @Test
    void shouldFailValidationWhenAmountIsLessThanZero() throws Exception {
        //Given
        String invalidRequest = """
                    {
                      "accountId": 1,
                      "from": {
                        "currency": "PLN"
                      },
                      "to": {
                        "currency": "USD"
                      },
                      "amount": -100.00
                    }
                """;

        // When & Then
        mockMvc.perform(post("/api/exchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFailValidationWhenUnsupportedCurrencyIsPassed() throws Exception {
        //Given
        String invalidRequest = """
                    {
                      "accountId": 1,
                      "from": {
                        "currency": "PLN"
                      },
                      "to": {
                        "currency": "EUR"
                      },
                      "amount": 100.00
                    }
                """;

        // When & Then
        mockMvc.perform(post("/api/exchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

}
