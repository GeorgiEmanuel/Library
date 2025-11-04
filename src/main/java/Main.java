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
        Book book = new BookBuilder()
                .setTitle("Ion")
                .setAuthor("Liviu Rebreanu")
                .setPublishDate(LocalDate.of(1910, 10, 20))
                .build();

        Connection connection = DataBaseConnectionFactory.getConnectionWrapper(false).getConnection();
        BookRepository bookRepository = new BookRepositoryMySql(connection);
        BookService bookService = new BookServiceImpl(bookRepository);
        System.out.println(bookService.findAll());

        Book bookMoaraCuNoroc = new BookBuilder()
                .setAuthor("Ioan Slavici")
                .setTitle("Moara cu noroc")
                .setPublishDate(LocalDate.of(1950, 02, 10))
                .build();
        //bookMoaraCuNoroc.setAuthor("', '', null); DROP TABLE book; -- ");
        //bookMoaraCuNoroc.setAuthor("', '', null); SET FOREIGN_KEY_CHECKS = 0; SET GROUP_CONCAT_MAX_LEN=32768; SET @tables = NULL; SELECT GROUP_CONCAT('', table_name, '') INTO @tables FROM information_schema.tables WHERE table_schema = (SELECT DATABASE()); SELECT IFNULL(@tables,'dummy') INTO @tables; SET @tables = CONCAT('DROP TABLE IF EXISTS ', @tables); PREPARE stmt FROM @tables; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET FOREIGN_KEY_CHECKS = 1; --");
        bookService.save(bookMoaraCuNoroc);
        try {
            System.out.println(bookService.findById(9L));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
