package service.admin;

import model.User;
import model.validator.Notification;

import java.util.List;

public interface AdminService {

    List<User> findAll();
    Notification<User> save(User user);
    Notification<Boolean> delete(User user);
}

