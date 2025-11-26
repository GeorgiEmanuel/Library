package repository.book;

import model.Book;
import model.validator.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock implements BookRepository{

    private final List<Book> books;

    public BookRepositoryMock(){
        this.books = new ArrayList<>();
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public Notification<Book> save(Book book) {
        Notification<Book> saveBookNotification = new Notification<>();

        if (books.add(book)) {
            saveBookNotification.setResult(book);
        } else {
            saveBookNotification.addError("Something is wrong with the repository");
        }

        return saveBookNotification;
    }

    @Override
    public boolean delete(Book book) {
        return books.remove(book);
    }

    @Override
    public void removeAll() {
        books.clear();
    }

    @Override
    public boolean decrementQuantity(Long id, Long quantity){
      Optional<Book> optionalBook = books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
      if (optionalBook.isEmpty()){
          return false;
      }else {
          Book book = optionalBook.get();
          book.setQuantity(book.getQuantity() - 1);
          return true;
      }
    }
}
