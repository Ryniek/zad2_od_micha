package pl.rynski.lab_2_aplikacje_internetowe.service;

import pl.rynski.lab_2_aplikacje_internetowe.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getBooks();
    Book getBookById(Long id);
    List<Book> getBooksWithStringInTitle(String text);
    boolean addBook(Book book);
    boolean modifyBook(Book book);
    boolean modifyField(Long id, Book book);
    boolean deleteBook(Long id);
}
