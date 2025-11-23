package service.order;

import model.Order;
import model.builder.OrderBuilder;
import model.validator.Notification;
import repository.order.OrderRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }


    @Override
    public Notification<Boolean> orderBook(Long user_id, Long book_id, Long quantity, Long price, LocalDateTime purchasedDate) {

        Order order = new OrderBuilder()
                .setUserId(user_id)
                .setBookId(book_id)
                .setQuantity(quantity)
                .setPrice(price)
                .setOrderDate(purchasedDate)
                .build();

       Notification<Boolean> orderBookNotification = new Notification<>();

       Notification<Boolean> orderBookRepositoryNotification = orderRepository.orderBook(order);

       if (orderBookRepositoryNotification.hasErrors()) {
           orderBookNotification.setResult(Boolean.FALSE);
           orderBookRepositoryNotification.getErrors().forEach(orderBookNotification::addError);
       }
       else {
           orderBookNotification.setResult(Boolean.TRUE);
       }
       return orderBookNotification;
    }
}
