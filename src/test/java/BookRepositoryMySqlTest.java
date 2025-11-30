import database.DataBaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import org.junit.jupiter.api.Test;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySql;
import service.book.BookService;
import service.book.BookServiceImpl;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryMySqlTest {
    Connection connection;
    BookRepository bookRepository;
    BookService bookService;

    private void setup() {
        this.connection = DataBaseConnectionFactory.getConnectionWrapper(false).getConnection();
        this.bookRepository = new BookRepositoryMySql(this.connection);
        this.bookService = new BookServiceImpl(this.bookRepository);
    }

    @Test
    public void findAll(){
        setup();
        List<Book> books = bookService.findAll();
        assertEquals(3 , books.size());
    }

    @Test
    public void findById(){
        setup();
        Optional<Book> book = Optional.empty();
        book = Optional.ofNullable(bookService.findById(1L));
        assertFalse(book.isEmpty());

    }

    @Test
    public void save(){
        setup();
        assertFalse(bookService.save(new BookBuilder().setTitle("Risipitorii").setAuthor("Marin Preda").setPublishDate(
                LocalDate.of(1950, 10, 2)).setQuantity(15L).setPrice(35L).build()).hasErrors());
    }

}
