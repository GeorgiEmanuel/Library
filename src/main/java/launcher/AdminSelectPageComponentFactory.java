package launcher;

import controller.AdminSelectPageController;
import javafx.stage.Stage;
import model.User;
import view.AdminSelectPageView;

public class AdminSelectPageComponentFactory {
    private final AdminSelectPageView adminSelectPageView;
    private final AdminSelectPageController adminSelectPageController;

    private static volatile AdminSelectPageComponentFactory instance;

    public static AdminSelectPageComponentFactory getInstance(Boolean componentsForTest, Stage stage, User user){
        if (instance == null){
            synchronized (AdminSelectPageComponentFactory.class){
                if (instance == null){
                    instance = new AdminSelectPageComponentFactory(componentsForTest, stage, user);
                }
            }
        }
        return instance;
    }
    public static void resetInstance(){
        instance = null;
    }

    private AdminSelectPageComponentFactory(Boolean componentsForTest, Stage primaryStage, User user){
        this.adminSelectPageView = new AdminSelectPageView(primaryStage);
        this.adminSelectPageController = new AdminSelectPageController(componentsForTest, adminSelectPageView, user);
    }
}
