package order.processing.system.order.mapper;

import order.processing.system.entity.Order;
import order.processing.system.entity.OrderItem;
import order.processing.system.entity.Product;
import order.processing.system.order.entity.OrderAddressEntity;
import order.processing.system.order.entity.OrderEntity;
import order.processing.system.order.entity.OrderItemEntity;
import order.processing.system.valueobject.CustomerId;
import order.processing.system.valueobject.Money;
import order.processing.system.valueobject.OrderId;
import order.processing.system.valueobject.OrderItemId;
import order.processing.system.valueobject.ProductId;
import order.processing.system.valueobject.RestaurantId;
import order.processing.system.valueobject.StreetAddress;
import order.processing.system.valueobject.TrackingId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDataAccessMapper {

    public OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .customerId(order.getCustomerId().getValue())
                .restaurantId(order.getRestaurantId().getValue())
                .trackingId(order.getTrackingId().getValue())
                .price(order.getPrice().getAmount())
                .orderStatus(order.getOrderStatus())
                .address(deliveryAddressToAddressEntity(order.getDeliveryAddress()))
                .items(orderItemsToOrderItemEntities(order.getOrderItems()))
                .build();
        orderEntity.getAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));

        return orderEntity;
    }

    public Order orderToOrderEntity(OrderEntity orderEntity) {
        return Order.Builder.builder()
                .id(new OrderId(orderEntity.getId()))
                .customerId(new CustomerId(orderEntity.getCustomerId()))
                .restaurantId(new RestaurantId(orderEntity.getRestaurantId()))
                .deliveryAddress(AddressEntityToDeliveryAddress(orderEntity.getAddress()))
                .price(new Money(orderEntity.getPrice()))
                .orderItems(orderItemEntitiesToOrderItems(orderEntity.getItems()))
                .trackingId(new TrackingId(orderEntity.getTrackingId()))
                .orderStatus(orderEntity.getOrderStatus())
                .build();
    }

    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> items) {
        return items.stream()
                .map(orderItemEntity -> OrderItem.Builder.builder()
                        .id(new OrderItemId(orderItemEntity.getId()))
                        .product(new Product(new ProductId(orderItemEntity.getProductId())))
                        .price(new Money(orderItemEntity.getPrice()))
                        .quantity(orderItemEntity.getQuantity())
                        .subtotal(new Money(orderItemEntity.getSubtotal()))
                        .build())
                .collect(Collectors.toList());
    }

    private StreetAddress AddressEntityToDeliveryAddress(OrderAddressEntity address) {
        return new StreetAddress(address.getId(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity());
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> OrderItemEntity.builder()
                        .id(orderItem.getId().getValue())
                        .productId(orderItem.getProduct().getId().getValue())
                        .price(orderItem.getPrice().getAmount())
                        .quantity(orderItem.getQuantity())
                        .subtotal(orderItem.getSubtotal().getAmount())
                        .build())
                .toList();
    }

    private OrderAddressEntity deliveryAddressToAddressEntity(StreetAddress deliveryAddress) {
        return OrderAddressEntity.builder()
                .id(deliveryAddress.getId())
                .street(deliveryAddress.getStreet())
                .postalCode(deliveryAddress.getPostalCode())
                .city(deliveryAddress.getCity())
                .build();
    }
}
