package order.processing.system;

import order.processing.system.entity.Order;
import order.processing.system.entity.Restaurant;
import order.processing.system.event.OrderCancelledEvent;
import order.processing.system.event.OrderCreatedEvent;
import order.processing.system.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

    OrderPaidEvent payOrder(Order order, Restaurant restaurant);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
