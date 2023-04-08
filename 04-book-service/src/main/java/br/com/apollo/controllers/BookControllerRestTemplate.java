package br.com.apollo.controllers;

import br.com.apollo.models.Book;
import br.com.apollo.repositories.BookRepository;
import br.com.apollo.response.Cambio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestController
@RequestMapping("book-service-rest-template")
public class BookControllerRestTemplate {

    private final Environment environment;
    private final BookRepository bookRepository;

    public BookControllerRestTemplate(Environment environment, BookRepository bookRepository) {
        this.environment = environment;
        this.bookRepository = bookRepository;
    }

    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id,
                         @PathVariable("currency") String currency) {

        var book = bookRepository.findById(id).get();

        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not found");
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("amount", book.getPrice().toString());
        params.put("from", "USD");
        params.put("to", currency);

        var response = new RestTemplate().
                getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}", Cambio.class, params);

        var cambio = response.getBody();
        var port = environment.getProperty("local.server.port");
        book.setEnvironment(port);
        book.setPrice(cambio.getConvertedValue());
        return book;
    }
}