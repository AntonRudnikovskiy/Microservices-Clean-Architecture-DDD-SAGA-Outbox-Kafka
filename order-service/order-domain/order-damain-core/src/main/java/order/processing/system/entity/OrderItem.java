package order.processing.system.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import order.processing.system.valueobject.Money;
import order.processing.system.valueobject.OrderId;
import order.processing.system.valueobject.OrderItemId;

@AllArgsConstructor
@Setter
@Getter
public class OrderItem extends BaseEntity<OrderItemId> {
    private OrderId orderId;
    private final Product product;
    private final int quantity;
    private final Money price;
    private final Money subtotal;
}
