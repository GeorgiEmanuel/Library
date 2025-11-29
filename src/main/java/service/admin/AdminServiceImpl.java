package service.admin;

import com.lowagie.text.Document;
import model.MonthlyReport;
import model.Role;
import model.User;
import model.validator.Notification;
import repository.order.OrderRepository;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import static database.Constants.Roles.EMPLOYEE;

public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final RightsRolesRepository rightsRolesRepository;

    private final OrderRepository orderRepository;

    public AdminServiceImpl(UserRepository userRepository, RightsRolesRepository rightsRolesRepository, OrderRepository orderRepository){
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
        this.orderRepository = orderRepository;
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

    @Override
    public Notification<Document> generateReport() {
        Notification<Document> reportGenerationNotification = new Notification<>();
        Notification<MonthlyReport>  monthlyReportNotification = orderRepository.generateMonthlyReport();
        if (monthlyReportNotification.hasErrors()){
            monthlyReportNotification.getErrors().forEach(reportGenerationNotification::addError);

        } else {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream("Report.pdf"));
                document.open();
                document.add(new Paragraph(monthlyReportNotification.getResult().toString()));

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                document.close();
            }
        }
        return reportGenerationNotification;
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
