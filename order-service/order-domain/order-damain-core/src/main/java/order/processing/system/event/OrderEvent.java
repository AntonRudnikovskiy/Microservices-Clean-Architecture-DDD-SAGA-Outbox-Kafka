package order.processing.system.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import order.processing.system.entity.Order;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public abstract class OrderEvent implements DomainEvent<Order> {
    private final Order order;
    private final ZonedDateTime createdAt;
}
