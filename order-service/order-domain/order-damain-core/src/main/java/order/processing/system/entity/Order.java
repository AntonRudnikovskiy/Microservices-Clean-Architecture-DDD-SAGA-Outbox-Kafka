package order.processing.system.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import order.processing.system.exception.OrderDomainException;
import order.processing.system.valueobject.CustomerId;
import order.processing.system.valueobject.Money;
import order.processing.system.valueobject.OrderId;
import order.processing.system.valueobject.OrderItemId;
import order.processing.system.valueobject.OrderStatus;
import order.processing.system.valueobject.RestaurantId;
import order.processing.system.valueobject.StreetAddress;
import order.processing.system.valueobject.TrackingId;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Slf4j
public class Order extends AggregateRoot<OrderId> {
    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> orderItems;

    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    public void initializeOrder() {
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializeOrderItems();
    }

    public void validateOrder() {
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    private void validateInitialOrder() {
        if (orderStatus != null || getId() != null) {
            throw new OrderDomainException("Order is not in correct state for initialization");
        }
    }

    private void validateTotalPrice() {
        if (price != null || !price.isGreaterThanZero()) {
            throw new OrderDomainException("Total price must be greater than zero!");
        }
    }

    private void validateItemsPrice() {
        Money orderItemsTotal = orderItems.stream().map(orderItem -> {
            validateItemPrice(orderItem);
            return orderItem.getSubtotal();
        }).reduce(Money.ZERO, Money::add);

        if (price.equals(orderItemsTotal)) {
            throw new OrderDomainException("Total price: " + price.getAmount() +
                    " is not equal to Order items total: " + orderItemsTotal.getAmount() + "!");
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (orderItem.isPriceValid()) {
            throw new OrderDomainException("Order item price: " + orderItem.getPrice().getAmount()
            + " is not valid for product " + orderItem.getProduct().getPrice());
        }
    }

    private void initializeOrderItems() {
        orderItems.forEach(orderItem -> {
            long itemId = 1;
            orderItem.initializeOrderItem(super.getId(), new OrderItemId(itemId++));
        });
    }
}
