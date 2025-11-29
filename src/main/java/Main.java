import database.DataBaseConnectionFactory;
import model.Book;
import model.Order;
import model.builder.BookBuilder;
import model.builder.OrderBuilder;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySql;
import repository.book.Cache;
import repository.order.OrderRepository;
import repository.order.OrderRepositoryMySql;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.order.OrderService;
import service.order.OrderServiceImpl;
import service.user.AuthentificationService;
import service.user.AuthentificationServiceImpl;

import java.sql.Connection;
import java.time.LocalDate;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class Main {
    public static void main(String[] args) {
//        Book book = new BookBuilder()
//                .setTitle("Ion")
//                .setAuthor("Liviu Rebreanu")
//                .setPublishDate(LocalDate.of(1910, 10, 20))
//                .build();
//
        Connection connection = DataBaseConnectionFactory.getConnectionWrapper(false).getConnection();
//        BookRepository bookRepository = new BookRepositoryCacheDecorator(new BookRepositoryMySql(connection), new Cache<>());
//        BookService bookService = new BookServiceImpl(bookRepository);
//
//        bookService.save(book);
//        System.out.println(bookService.findAll());
//        System.out.println(bookService.findAll());
//        System.out.println(bookService.findAll());

//        Book bookMoaraCuNoroc = new BookBuilder()
//                .setAuthor("Ioan Slavici")
//                .setTitle("Moara cu noroc")
//                .setPublishDate(LocalDate.of(1950, 02, 10))
//                .build();
        //bookMoaraCuNoroc.setAuthor("', '', null); DROP TABLE book; -- ");
        //bookMoaraCuNoroc.setAuthor("', '', null); SET FOREIGN_KEY_CHECKS = 0; SET GROUP_CONCAT_MAX_LEN=32768; SET @tables = NULL; SELECT GROUP_CONCAT('', table_name, '') INTO @tables FROM information_schema.tables WHERE table_schema = (SELECT DATABASE()); SELECT IFNULL(@tables,'dummy') INTO @tables; SET @tables = CONCAT('DROP TABLE IF EXISTS ', @tables); PREPARE stmt FROM @tables; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET FOREIGN_KEY_CHECKS = 1; --");
//        bookService.save(bookMoaraCuNoroc);
//        try {
//            System.out.println(bookService.findById(9L));
//        }catch (Exception e){
//            e.printStackTrace();
//        }

//        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
//        UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
//        AuthentificationService authentificationService = new AuthentificationServiceImpl(userRepository, rightsRolesRepository);
//
//        authentificationService.register("Ion", "parola123!");

//        OrderRepository orderRepository = new OrderRepositoryMySql(connection);
//        OrderService orderService = new OrderServiceImpl(orderRepository);

//        orderService.orderBook(1L, 3L, 2L, 3L, LocalDate.of(1910, 10, 20));

    }
}
