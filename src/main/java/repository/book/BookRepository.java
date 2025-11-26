package repository.book;

import model.Book;
import model.validator.Notification;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Notification<Book> save(Book book);

    boolean delete(Book book);

    void removeAll();

    boolean decrementQuantity(Long id, Long quantity);
}
