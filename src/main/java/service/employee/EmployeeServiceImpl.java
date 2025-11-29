package service.employee;

import model.User;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService{

    private final UserRepository userRepository;

    private final RightsRolesRepository rightsRolesRepository;

    public EmployeeServiceImpl(UserRepository userRepository, RightsRolesRepository rightsRolesRepository){
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAllEmployees(){
        return userRepository.findAllEmployees();

    }
}
