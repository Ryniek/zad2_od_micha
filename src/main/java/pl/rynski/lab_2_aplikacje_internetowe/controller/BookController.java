package pl.rynski.lab_2_aplikacje_internetowe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.rynski.lab_2_aplikacje_internetowe.model.Book;
import pl.rynski.lab_2_aplikacje_internetowe.service.BookService;

import java.util.List;

@Controller
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String getBooks(@ModelAttribute("book") Book book, Model model) {
        List<Book> bookList = bookService.getBooks();
        model.addAttribute("books", bookList);
        model.addAttribute("newBook", new Book());
        model.addAttribute("newBookToAdd", new Book());
        model.addAttribute("book", book);
        return "index";
    }

    @PostMapping("byId")
    public String findCarById(@ModelAttribute Book book, RedirectAttributes attributes) {
        Book first = bookService.getBookById(book.getBookId());
        attributes.addFlashAttribute("book", first);
        return "redirect:/";
    }

    @PostMapping
    public String addBook(@ModelAttribute Book book) {
        bookService.addBook(book);
        return "redirect:/";
    }

    @PostMapping("/update/{index}")
    public String updateCar(@PathVariable int index, @ModelAttribute Book book) {
        if(!book.getIsbn().isEmpty())
            bookService.getBooks().get(index).setIsbn(book.getIsbn());
        if(!book.getTitle().isEmpty())
            bookService.getBooks().get(index).setTitle(book.getTitle());
        if(!book.getAuthor().isEmpty())
            bookService.getBooks().get(index).setAuthor(book.getAuthor());
        return "redirect:/";
    }

    @GetMapping("/delete/{index}")
    public String deleteCar(@PathVariable int index) {
        bookService.getBooks().remove(index);
        return "redirect:/";
    }
}
