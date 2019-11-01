package pl.rynski.lab_2_aplikacje_internetowe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.rynski.lab_2_aplikacje_internetowe.model.Book;
import pl.rynski.lab_2_aplikacje_internetowe.service.BookService;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/books")
public class BookApi {

    private BookService bookService;

    @Autowired
    public BookApi(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Resources<Book>> getBooks() {
        List<Book> bookList = bookService.getBooks();
        bookList.forEach(book -> book.add(linkTo(BookApi.class).slash(book.getBookId()).withSelfRel()));
        Link link = linkTo(BookApi.class).withSelfRel();
        Resources<Book> bookResources = new Resources<>(bookList, link);
        if(bookList != null) {
            return new ResponseEntity<>(bookResources, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        book.add(linkTo(BookApi.class).slash(id).withSelfRel());
        if(book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/title/{text}")
    public ResponseEntity<Resources<Book>> getBooksByTitle(@PathVariable String text) {
        List<Book> bookList = bookService.getBooksByTitle(text);
        bookList.forEach(book -> book.add(linkTo(BookApi.class).slash(book.getBookId()).withSelfRel()));
        bookList.forEach(book -> book.add(linkTo(BookApi.class).withRel("All titles")));
        Link link = linkTo(BookApi.class).withSelfRel();
        Resources<Book> bookResources = new Resources<>(bookList, link);
        if(bookList != null) {
            return new ResponseEntity<>(bookResources, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity addBook(@RequestBody Book book) {
        if(bookService.addBook(book)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity modifyBook(@RequestBody Book book) {
        boolean modify = bookService.modifyBook(book);
        if(modify) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity modifyBookField(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        boolean modify = bookService.modifyField(id, updates);
        if(modify) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable Long id) {
        if(bookService.deleteBook(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
