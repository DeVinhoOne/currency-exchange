package org.example.currencyexchange.rest.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/exchnage")
public class ExchangeController {

    @PostMapping
    public void exchange() {}

}
