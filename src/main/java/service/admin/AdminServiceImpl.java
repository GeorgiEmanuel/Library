package service.admin;

import model.Role;
import model.User;
import model.validator.Notification;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final RightsRolesRepository rightsRolesRepository;

    public AdminServiceImpl(UserRepository userRepository, RightsRolesRepository rightsRolesRepository){
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }
    @Override
    public Notification<User> save(User user) {
        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);

        Notification<User> userRegisterNotification = new Notification<>();
        user.setPassword(hashPassword(user.getPassword()));
        user.setRoles(Collections.singletonList(customerRole));
        Notification<User> saveNotification = userRepository.save(user);

        if (saveNotification.hasErrors()) {
            saveNotification.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(null);
        } else {
            userRegisterNotification.setResult(user);
        }


        return userRegisterNotification;
    }

    @Override
    public Notification<Boolean> delete(User user) {

        Notification<Boolean> userDeleteNotification = new Notification<>();
        userDeleteNotification.setResult(Boolean.TRUE);
        Notification<Boolean> userRepositoryDeleteNotification = userRepository.delete(user.getUsername());

        if (userDeleteNotification.hasErrors()){
            userDeleteNotification.setResult(Boolean.FALSE);
            userRepositoryDeleteNotification.getErrors().forEach(userDeleteNotification::addError);
        }

        return userDeleteNotification;
    }
    private String hashPassword(String password){

        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff  & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
