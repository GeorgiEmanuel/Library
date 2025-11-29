package service.employee;

import model.User;

import java.util.List;

public interface EmployeeService {
    List<User> findAllEmployees();
}
