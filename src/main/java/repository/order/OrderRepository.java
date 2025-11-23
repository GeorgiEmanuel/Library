package repository.order;
import model.Order;
import model.validator.Notification;

import java.util.List;

public interface OrderRepository {

    List<Order> findAllOrders();

    Notification<Boolean> orderBook(Order order);

    void removeAllOrders();

    List<Order> existsOrderByUser(String username);


}
