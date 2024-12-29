package dev.phdfreitas.exchangeservice.controller;

import dev.phdfreitas.exchangeservice.model.Exchange;
import dev.phdfreitas.exchangeservice.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("exchange-service")
public class ExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @GetMapping(value = "/{amount}/{from}/{to}")
    public Exchange getExchange(@PathVariable("amount")BigDecimal amount,
                                @PathVariable("from") String from,
                                @PathVariable("to") String to){

        var exchange = exchangeRepository.findByFromAndTo(from, to);

        if(exchange==null)
            throw new RuntimeException("Currency not found");

        var port = environment.getProperty("local.server.port");

        BigDecimal conversionFactor = exchange.getConversionFactor();
        BigDecimal convertedValue = conversionFactor.multiply(amount);

        exchange.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
        exchange.setEnviroment(port);

        return exchange;
    }
}
