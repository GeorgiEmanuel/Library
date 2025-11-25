package service.admin;

import model.User;
import model.validator.Notification;

import java.util.List;

public interface AdminService {

    List<User> findAll();
    Notification<Boolean> save(User user);
    Notification<Boolean> delete(User user);
}

