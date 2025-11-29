package service.admin;

import com.lowagie.text.Document;
import model.User;
import model.validator.Notification;

import java.lang.annotation.Documented;
import java.util.Date;
import java.util.List;

public interface AdminService {

    List<User> findAll();
    Notification<User> save(User user);
    Notification<Boolean> delete(User user);
    Notification<Document> generateReport();
}

