package repository.order;

import model.MonthlyReport;
import model.MonthlyReportRow;
import model.Order;
import model.builder.MonthlyReportRowBuilder;
import model.builder.OrderBuilder;
import model.validator.Notification;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static database.Constants.Tables.ORDER;

public class OrderRepositoryMySql implements OrderRepository {

    private final Connection connection;


    public OrderRepositoryMySql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Order> findAllOrders() {

        String sql = "SELECT * FROM book;";
        List<Order> orders = new LinkedList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                orders.add(getOrderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Notification<Boolean> orderBook(Order order) {
        Notification<Boolean> orderNotification = new Notification<>();
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO `" + ORDER + "` values (null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertStatement.setLong(1, order.getUserId());
            insertStatement.setLong(2, order.getBookId());
            insertStatement.setLong(3, order.getQuantity());
            insertStatement.setLong(4, order.getPrice());
            insertStatement.setTimestamp(5, java.sql.Timestamp.valueOf(order.getOrderDate()));


            int rowsInserted = insertStatement.executeUpdate();
            if (rowsInserted == 0){
                    orderNotification.addError("Order insert failed");
                orderNotification.setResult(Boolean.FALSE);

            }
            orderNotification.setResult(Boolean.TRUE);

        } catch (SQLException e) {
            e.printStackTrace();
            orderNotification.addError("Something is wrong with the Database!");
        }
        return orderNotification;

    }

    @Override
    public void removeAllOrders() {

    }

    @Override
    public List<Order> existsOrderByUser(String username) {
        return List.of();
    }

    @Override
    public Notification<MonthlyReport> generateMonthlyReport() {
        Notification<MonthlyReport> monthlyReportNotification = new Notification<>();
        List<MonthlyReportRow> monthlyReportRows = new ArrayList<>();
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();
        try {
            String statement =
                    "SELECT u.username AS customer_username, " +
                    "SUM(o.quantity) AS books_bought, " +
                    "SUM(o.price) AS total_price " +
                    "FROM `order` o JOIN `user` u ON o.user_id = u.id " +
                    "WHERE MONTH(o.purchased_date) = ? " +
                    "AND YEAR(o.purchased_date) = ? " +
                    "GROUP BY u.username " +
                    "ORDER BY books_bought DESC, total_price DESC;";

            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, currentMonth);
            preparedStatement.setInt(2, currentYear);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()){
                monthlyReportNotification.addError("No data available for the specified time !");
            } else {
                while (resultSet.next()) {
                    monthlyReportRows.add(getMonthlyReportFromResultSet(resultSet));
                }
                monthlyReportNotification.setResult(new MonthlyReport(monthlyReportRows));
            }

        }catch (SQLException e){
            e.printStackTrace();
            monthlyReportNotification.addError("Something is wrong with the database !");
        }
        return monthlyReportNotification;
    }

    private MonthlyReportRow getMonthlyReportFromResultSet(ResultSet resultSet) throws SQLException{
        return new MonthlyReportRowBuilder()
                .setUsername(resultSet.getString("customer_username"))
                .setNumberOfBooksBought(resultSet.getLong("books_bought"))
                .setTotalPrice(resultSet.getLong("total_price"))
                .build();
    }


    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        return new OrderBuilder()
                .setId(resultSet.getLong("id"))
                .setUserId(resultSet.getLong("user_id"))
                .setBookId(resultSet.getLong("book_id"))
                .setQuantity(resultSet.getLong("quantity"))
                .setPrice(resultSet.getLong("price"))
                .setOrderDate(new java.sql.Timestamp(resultSet.getDate("purchased_date").getTime()).toLocalDateTime())
                .build();
    }
}
