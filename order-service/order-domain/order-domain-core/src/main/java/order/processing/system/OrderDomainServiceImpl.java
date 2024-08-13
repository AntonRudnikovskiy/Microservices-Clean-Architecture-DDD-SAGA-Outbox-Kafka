package order.processing.system;

import lombok.extern.slf4j.Slf4j;
import order.processing.system.entity.Order;
import order.processing.system.entity.OrderItem;
import order.processing.system.entity.Product;
import order.processing.system.entity.Restaurant;
import order.processing.system.event.OrderCancelledEvent;
import order.processing.system.event.OrderCreatedEvent;
import order.processing.system.event.OrderPaidEvent;
import order.processing.system.exception.OrderDomainException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {
    private static final String UTC = "UTC";

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order was initiated with id: {}", order.getId().getValue());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public OrderPaidEvent payOrder(Order order, Restaurant restaurant) {
        order.pay();
        log.info("Payment for order with id: {}", order.getId().getValue());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order was approved with id: {}", order.getId().getValue());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel();
        log.info("Order payment was cancelled with id: {}", order);
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel();
        log.info("Order was cancelled with id: {}", order.getId().getValue());
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive()) {
            throw new OrderDomainException("Restaurant with id " + restaurant.getId().getValue() +
                    " is currently not active");
        }
    }

    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        Map<UUID, Product> productMap = new HashMap<>();
        for (Product product : restaurant.getProducts()) {
            productMap.put(product.getId().getValue(), product);
        }

        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            Product restaurantProduct = productMap.get(product.getId().getValue());
            if (product.equals(restaurantProduct)) {
                product.updateWithConfirmedNameAndPrice(restaurantProduct.getName(), restaurantProduct.getPrice());
                log.info("Product with id: {} was found", product.getId().getValue());
            }
            else {
                log.warn("Product with id: {} was not found", product.getId().getValue());
            }
        }
    }
}
