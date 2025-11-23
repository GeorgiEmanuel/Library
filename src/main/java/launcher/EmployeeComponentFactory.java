package launcher;

import controller.BookController;
import database.DataBaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import model.User;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySql;
import repository.book.Cache;
import repository.order.OrderRepository;
import repository.order.OrderRepositoryMySql;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.order.OrderService;
import service.order.OrderServiceImpl;
import view.BookView;
import view.model.BookDTO;

import java.sql.Connection;
import java.util.List;

public class EmployeeComponentFactory {

    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    private static volatile EmployeeComponentFactory instance;

    public static EmployeeComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage, User user){
        if (instance == null){
            synchronized(EmployeeComponentFactory.class) {
                if (instance == null) {
                    instance = new EmployeeComponentFactory(componentsForTest, primaryStage, user);
                }
            }
        }
        return instance;
    }
    public EmployeeComponentFactory(Boolean componentsForTest, Stage primaryStage, User user){
        Connection connection = DataBaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryCacheDecorator(new BookRepositoryMySql(connection), new Cache<>());
        this.bookService = new BookServiceImpl(bookRepository);
        this.orderRepository = new OrderRepositoryMySql(connection);
        this.orderService = new OrderServiceImpl(orderRepository);



        List<BookDTO> booksDTOs = BookMapper.convertBookListToBookDTOList(bookService.findAll());
        this.bookView = new BookView(primaryStage, booksDTOs);
        this.bookController = new BookController(bookView, bookService, orderService, user);

    }

    public BookView getBookView() {
        return bookView;
    }

    public BookController getBookController() {
        return bookController;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }

}
