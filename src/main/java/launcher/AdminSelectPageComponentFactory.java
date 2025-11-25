package launcher;

import controller.AdminSelectPageController;
import javafx.stage.Stage;
import view.AdminSelectPageView;

public class AdminSelectPageComponentFactory {
    private final AdminSelectPageView adminSelectPageView;
    private final AdminSelectPageController adminSelectPageController;

    private static volatile AdminSelectPageComponentFactory instance;

    public static AdminSelectPageComponentFactory getInstance(Boolean componentsForTests, Stage stage){
        if (instance == null){
            synchronized (AdminSelectPageComponentFactory.class){
                if (instance == null){
                    instance = new AdminSelectPageComponentFactory(componentsForTests, stage);
                }
            }
        }
        return instance;
    }

    public AdminSelectPageComponentFactory(Boolean componentsForTests, Stage primaryStage){
        this.adminSelectPageView = new AdminSelectPageView(primaryStage);
        this.adminSelectPageController = new AdminSelectPageController(adminSelectPageView);
    }
}
