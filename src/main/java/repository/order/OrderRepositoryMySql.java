package repository.order;

import model.Order;
import model.builder.OrderBuilder;
import model.validator.Notification;

import java.sql.*;
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
