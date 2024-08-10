package order.processing.system.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import order.processing.system.valueobject.CustomerId;
import order.processing.system.valueobject.Money;
import order.processing.system.valueobject.OrderId;
import order.processing.system.valueobject.OrderStatus;
import order.processing.system.valueobject.RestaurantId;
import order.processing.system.valueobject.StreetAddress;
import order.processing.system.valueobject.TrackingId;

import java.util.List;

@AllArgsConstructor
@Getter
public class Order extends AggregateRoot<OrderId> {
    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> orderItems;

    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;
}
