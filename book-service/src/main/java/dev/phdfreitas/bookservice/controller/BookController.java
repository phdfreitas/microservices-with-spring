package dev.phdfreitas.bookservice.controller;

import dev.phdfreitas.bookservice.model.Book;
import dev.phdfreitas.bookservice.repository.BookRepository;
import dev.phdfreitas.bookservice.response.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("book-service")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private Environment environment;

    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency){

        var book = bookRepository.findById(id);
        if (book == null)
            throw new RuntimeException("Book not found");

        HashMap<String, String> params = new HashMap<>();
        params.put("amount", book.get().getPrice().toString());
        params.put("from", "USD");
        params.put("to", currency);

        var response = new RestTemplate().getForEntity("http://localhost:8000/exchange-service/{amount}/{from}/{to}",
                Exchange.class, params);

        var exchange = response.getBody();

        var port = environment.getProperty("local.server.port");
        book.get().setEnviroment(port);
        book.get().setCurrency(currency);
        book.get().setPrice(exchange.getConvertedValue());

        return book.get();
    }
}
