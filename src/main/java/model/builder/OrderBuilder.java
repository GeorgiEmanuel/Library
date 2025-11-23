package model.builder;

import model.Order;
import java.time.LocalDateTime;

public class OrderBuilder {

    private Order order;

    public OrderBuilder(){ this.order = new Order(); };

    public OrderBuilder setId(Long id){
        this.order.setId(id);
        return this;
    }

    public OrderBuilder setUserId(Long userId){
        this.order.setUserId(userId);
        return this;
    }

    public OrderBuilder setBookId(Long bookId){
        this.order.setBookId(bookId);
        return this;
    }

    public OrderBuilder setQuantity(Long quantity){
        this.order.setQuantity(quantity);
        return this;
    }

    public OrderBuilder setPrice(Long price){
        this.order.setPrice(price);
        return this;
    }

    public OrderBuilder setOrderDate(LocalDateTime orderDate){
        this.order.setOrderDate(orderDate);
        return this;
    }

    public Order build(){ return order; }

}

