package service.order;

import model.Order;
import model.validator.Notification;

import java.time.LocalDateTime;

public interface OrderService {
    Notification<Boolean> orderBook(Long user_id, Long book_id, Long quantity, Long price, LocalDateTime purchasedDate);
}
