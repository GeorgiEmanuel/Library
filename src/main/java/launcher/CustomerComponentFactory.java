package launcher;

import controller.CustomerController;
import database.DataBaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import mapper.UserMapper;
import model.User;
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
import service.employee.EmployeeService;
import service.employee.EmployeeServiceImpl;
import service.order.OrderService;
import service.order.OrderServiceImpl;
import view.CustomerView;
import view.model.BookDTO;
import view.model.UserDTO;

import java.sql.Connection;
import java.util.List;

public class CustomerComponentFactory {


    private final CustomerView customerView;
    private final CustomerController customerController;
    private final BookService bookService;
    private final BookRepository bookRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final EmployeeService employeeService;
    private static Boolean componentsForTest;
    private static Stage stage;

    private static volatile CustomerComponentFactory instance;

    public static CustomerComponentFactory getInstance(Boolean componentsFortTest, Stage primaryStage, User user){
        if (instance == null){
            synchronized (CustomerComponentFactory.class){
                if (instance == null){
                    instance = new CustomerComponentFactory(componentsFortTest, primaryStage, user);
                }
            }

        }
        return instance;
    }
    public static void resetInstance(){
        instance = null;
    }
    public CustomerComponentFactory(Boolean componentsForTest, Stage primaryStage, User user){
        Connection connection = DataBaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryCacheDecorator(new BookRepositoryMySql(connection), new Cache<>());
        this.bookService = new BookServiceImpl(bookRepository);
        this.orderRepository = new OrderRepositoryMySql(connection);
        this.orderService = new OrderServiceImpl(orderRepository);
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.employeeService = new EmployeeServiceImpl(userRepository, rightsRolesRepository);

        List<BookDTO> booksDTOs = BookMapper.convertBookListToBookDTOList(bookService.findAll());
        List<UserDTO> employeesDTOs = UserMapper.convertUserListToUserDTOList(employeeService.findAllEmployees());

        this.customerView = new CustomerView(primaryStage, booksDTOs, employeesDTOs);
        this.customerController = new CustomerController(customerView, orderService, bookService, user);
    }

    public static Stage getStage() {
        return stage;
    }

    public static Boolean getComponentsForTest() {
        return componentsForTest;
    }
}
