package pl.rynski.lab_2_aplikacje_internetowe.service;

import org.springframework.stereotype.Service;
import pl.rynski.lab_2_aplikacje_internetowe.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImp implements BookService {

    private List<Book> bookList;

    public BookServiceImp() {
        this.bookList = new ArrayList<>();
        bookList.add(new Book(1L, "12345678901234", "Blabla", "Autor1 Autor"));
        bookList.add(new Book(2L, "23156489798456", "Babasbsa", "Michu Rynski"));
        bookList.add(new Book(3L, "32654894598798", "Dada czesc 1", "Zygmunt Jakistam"));
        bookList.add(new Book(4L, "48945616518949", "Kasdasd", "Blablabla bblblbba"));
        bookList.add(new Book(5L, "59594894954912", "Qweqqw", "Nnasnsa Sdsads"));
        bookList.add(new Book(6L, "62626265189848", "Lfsafsafsa", "Autor2 2Autor"));
        bookList.add(new Book(7L, "79889489498489", "Dada czesc 2", "Pewqewqs Fasasa"));
        bookList.add(new Book(8L, "81292928928928", "Fasfsafw", "Mario Jakis"));
        bookList.add(new Book(9L, "92928928928921", "Loremmmmm", "Lalalala laslsala"));
        bookList.add(new Book(10L, "Z1231421143243", "Mwqdqwwqf", "Rotau Mamama"));
    }

    @Override
    public List<Book> getBooks() {
        if(!bookList.isEmpty()) {
            return bookList;
        } else {
            return null;
        }
    }

    @Override
    public Book getBookById(Long id) {
        Optional<Book> first = bookList.stream().filter(book -> book.getId() == id).findFirst();
        if(first.isPresent()) {
            return first.get();
        } else {
            return null;
        }
    }

    public List<Book> getBooksByTitle(String text) {
        List<Book> books = bookList.stream().filter(book -> book.getTitle().contains(text)).collect(Collectors.toList());
        if(!books.isEmpty()) {
            return books;
        } else {
            return null;
        }
    }

    public boolean addBook(Book book) {
        boolean add = bookList.add(book);
        return add;
    }

    public boolean modifyBook(Book newBook) {
        Optional<Book> toModify = bookList.stream().filter(book -> book.getId() == newBook.getId()).findFirst();
        if(!toModify.isPresent()) {
            return false;
        }
        toModify.get().setIsbn(newBook.getIsbn());
        toModify.get().setTitle(newBook.getTitle());
        toModify.get().setAuthor(newBook.getAuthor());
        return true;
    }

    public boolean modifyField(Long id, Map<String, Object> updates) {
        Optional<Book> toModify = bookList.stream().filter(book -> book.getId() == id).findFirst();
        if(!toModify.isPresent()) {
            return false;
        }
        if (updates.containsKey("isbn")) {
            toModify.get().setIsbn((String) updates.get("isbn"));
        }
        if (updates.containsKey("title")) {
            toModify.get().setTitle((String) updates.get("title"));
        }
        if (updates.containsKey("author")) {
            toModify.get().setAuthor((String) updates.get("author"));
        }
        return true;
    }

    public boolean deleteBook(Long id) {
        Optional<Book> toDelete = bookList.stream().filter(book -> book.getId() == id).findFirst();
        boolean delete = bookList.remove(toDelete.get());
        return delete;
    }
}
