package br.com.apollo.controllers;

import br.com.apollo.models.Book;
import br.com.apollo.proxy.CambioProxy;
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

import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("book-service-feign")
public class BookControllerFeign {

    private final Environment environment;

    private final BookRepository bookRepository;

    private final CambioProxy cambioProxy;

    public BookControllerFeign(Environment environment,
                               BookRepository bookRepository,
                               CambioProxy cambioProxy) {
        this.environment = environment;
        this.bookRepository = bookRepository;
        this.cambioProxy = cambioProxy;
    }

    /*http://localhost:8000/book-service/1/BRL*/
    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id,
                         @PathVariable("currency") String currency) {

        var book = bookRepository.findById(id).get();

        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not found");
        }

        var cambio = cambioProxy.getCambio(book.getPrice(), "USD", currency);
        var port = environment.getProperty("local.server.port");
        book.setEnvironment("Book Port: " + port + " Cambio Port" + cambio.getEnvironment());
        book.setPrice(cambio.getConvertedValue());
        return book;
    }
}