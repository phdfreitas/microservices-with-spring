package dev.phdfreitas.bookservice.controller;

import dev.phdfreitas.bookservice.model.Book;
import dev.phdfreitas.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        var port = environment.getProperty("local.server.port");
        book.get().setEnviroment(port);

        return book.get();
    }
}
