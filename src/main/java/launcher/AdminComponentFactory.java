package launcher;

import controller.AdminController;
import database.DataBaseConnectionFactory;
import javafx.stage.Stage;
import mapper.UserMapper;
import repository.order.OrderRepository;
import repository.order.OrderRepositoryMySql;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.admin.AdminService;
import service.admin.AdminServiceImpl;
import service.order.OrderService;
import service.order.OrderServiceImpl;
import view.AdminView;
import view.model.UserDTO;

import java.sql.Connection;
import java.util.List;

public class AdminComponentFactory {

    private final AdminView adminView;
    private final AdminController adminController;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final AdminService adminService;
    private final OrderRepository orderRepository;


    private static volatile AdminComponentFactory instance;

    public static AdminComponentFactory getInstance(Boolean componentsForTests, Stage stage) {
        if (instance == null) {
            synchronized (AdminComponentFactory.class) {
                if (instance == null) {
                    instance = new AdminComponentFactory(componentsForTests, stage);
                }
            }
        }
        return instance;
    }

    public AdminComponentFactory(Boolean componentsForTest, Stage primaryStage) {
        Connection connection = DataBaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.orderRepository = new OrderRepositoryMySql(connection);

        this.adminService = new AdminServiceImpl(userRepository, rightsRolesRepository, orderRepository);

        List<UserDTO> usersDTOs = UserMapper.convertUserListToUserDTOList(adminService.findAll());
        this.adminView = new AdminView(primaryStage, usersDTOs);
        this.adminController = new AdminController(adminView, adminService);

    }

}

