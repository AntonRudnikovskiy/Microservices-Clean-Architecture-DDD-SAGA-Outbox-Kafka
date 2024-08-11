package order.processing.system.ports.output.repository;

import order.processing.system.entity.Order;
import order.processing.system.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findTracking(TrackingId trackingId);
}
