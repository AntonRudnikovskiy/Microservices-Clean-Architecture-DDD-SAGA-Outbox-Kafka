package order.processing.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
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

    private Order(Builder builder) {
        super.setId(builder.orderId);
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        deliveryAddress = builder.deliveryAddress;
        price = builder.price;
        orderItems = builder.orderItems;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
    }

    public void initializeOrder() {
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializeOrderItems();
    }

    public void validateOrder() {
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    public void pay() {
        if (orderStatus != OrderStatus.PENDING) {
            log.error("Order is not in correct state for pay operation!");
            throw new OrderDomainException("Order is not in correct state for pay operation!");
        }
        orderStatus = OrderStatus.PAID;
    }

    public void approve() {
        if (orderStatus != OrderStatus.PAID) {
            log.error("Order is not in correct state for approve operation!");
            throw new OrderDomainException("Order is not in correct state for approve operation!");
        }
        orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel() {
        if (orderStatus != OrderStatus.PAID) {
            log.error("Order is not in correct state for initCancel operation!");
            throw new OrderDomainException("Order is not in correct state for initCancel operation!");
        }
        orderStatus = OrderStatus.CANCELLED;
    }

    public void cancel() {
        if (orderStatus == OrderStatus.CANCELLED || orderStatus == OrderStatus.PENDING) {
            log.error("Order is not in correct state for cancel operation!");
            throw new OrderDomainException("Order is not in correct state for cancel operation!");
        }
        orderStatus = OrderStatus.CANCELLED;
    }

    private void validateInitialOrder() {
        if (orderStatus != null || getId() != null) {
            log.error("Order is not in correct state for initialization");
            throw new OrderDomainException("Order is not in correct state for initialization");
        }
        orderStatus = OrderStatus.APPROVED;
    }

    private void validateTotalPrice() {
        if (price != null || !price.isGreaterThanZero()) {
            log.error("Order is not in correct state for pay operation!");
            throw new OrderDomainException("Order is not in correct state for pay operation!");
        }
    }

    private void validateItemsPrice() {
        Money orderItemsTotal = orderItems.stream().map(orderItem -> {
            validateItemPrice(orderItem);
            return orderItem.getSubtotal();
        }).reduce(Money.ZERO, Money::add);

        if (price.equals(orderItemsTotal)) {
            log.error("Total price: " + price.getAmount() +
                    " is not equal to Order items total: " + orderItemsTotal.getAmount() + "!");
            throw new OrderDomainException("Total price: " + price.getAmount() +
                    " is not equal to Order items total: " + orderItemsTotal.getAmount() + "!");
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (orderItem.isPriceValid()) {
            log.error("Order item price: " + orderItem.getPrice().getAmount()
                    + " is not valid for product " + orderItem.getProduct().getPrice());
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

    public static final class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private StreetAddress deliveryAddress;
        private Money price;
        private List<OrderItem> orderItems;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder deliveryAddress(StreetAddress val) {
            deliveryAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder orderItems(List<OrderItem> val) {
            orderItems = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
