import database.DataBaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import repository.BookRepository;
import repository.BookRepositoryMock;
import repository.BookRepositoryMySql;
import service.BookService;
import service.BookServiceImpl;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Hello World");


        Book book = new BookBuilder()
                .setTitle("Ion")
                .setAuthor("Liviu Rebreanu")
                .setPublishDate(LocalDate.of(1910, 10, 20))
                .build();

        Connection connection = DataBaseConnectionFactory.getConnectionWrapper(false).getConnection();
        BookRepository bookRepository = new BookRepositoryMySql(connection);
        BookService bookService = new BookServiceImpl(bookRepository);

        bookService.save(book);
        System.out.println(bookService.findAll());

        Book bookMoaraCuNoroc = new BookBuilder()
                .setAuthor("Ioan Slavici")
                .setTitle("Moara cu noroc")
                .setPublishDate(LocalDate.of(1950, 02, 10))
                .build();

        bookService.save(bookMoaraCuNoroc);

        System.out.println(bookService.findAll());

        bookService.delete(bookMoaraCuNoroc);
        //bookService.save(book);
        System.out.println(bookService.findAll());
    }
}
