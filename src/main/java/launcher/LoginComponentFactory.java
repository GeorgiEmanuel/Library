package launcher;

import controller.LoginController;
import database.DataBaseConnectionFactory;
import javafx.stage.Stage;
import model.validator.UserValidator;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySql;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthentificationService;
import service.user.AuthentificationServiceImpl;
import view.LoginView;

import java.sql.Connection;

public class LoginComponentFactory {
    private final LoginView loginView;
    private final LoginController loginController;
    private final AuthentificationService authentificationService;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final BookRepositoryMySql bookRepository;
    private static LoginComponentFactory instance;
    private static Boolean componentsForTests;
    private static Stage stage;

    public static LoginComponentFactory getInstance(Boolean aComponentsForTests, Stage aStage){
        if (instance == null){
            synchronized(LoginComponentFactory.class){
                if (instance == null){
                    componentsForTests = aComponentsForTests;
                    stage = aStage;
                    instance = new LoginComponentFactory(componentsForTests, stage);
                }
            }
        }
        return instance;
    }
    private LoginComponentFactory(Boolean componentsForTests, Stage stage){
        Connection connection = DataBaseConnectionFactory.getConnectionWrapper(componentsForTests).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authentificationService = new AuthentificationServiceImpl(userRepository, rightsRolesRepository);
        this.loginView = new LoginView(stage);
        this.loginController = new LoginController(loginView, authentificationService);
        this.bookRepository = new BookRepositoryMySql(connection);
    }

    public static Stage getStage(){
        return stage;
    }

    public static Boolean getComponentsForTests(){
        return componentsForTests;
    }

    public AuthentificationService getAuthentificationService(){
        return authentificationService;
    }

    public UserRepository getUserRepository(){
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository(){
        return rightsRolesRepository;
    }

    public LoginView getLoginView(){
        return loginView;
    }

    public BookRepositoryMySql getBookRepository(){
        return bookRepository;
    }

    public LoginController getLoginController(){
        return loginController;
    }

}
