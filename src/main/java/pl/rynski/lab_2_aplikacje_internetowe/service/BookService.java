package pl.rynski.lab_2_aplikacje_internetowe.service;

import pl.rynski.lab_2_aplikacje_internetowe.model.Book;

import java.util.List;
import java.util.Map;

public interface BookService {

    List<Book> getBooks();
    Book getBookById(Long id);
    List<Book> getBooksByTitle(String text);
    boolean addBook(Book book);
    boolean modifyBook(Book book);
    boolean modifyField(Long id, Map<String, Object> updates);
    boolean deleteBook(Long id);
}
