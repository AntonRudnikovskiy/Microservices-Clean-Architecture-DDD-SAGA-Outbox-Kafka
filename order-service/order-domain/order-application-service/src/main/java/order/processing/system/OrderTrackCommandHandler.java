package order.processing.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.processing.system.dto.track.TrackOrderQuery;
import order.processing.system.dto.track.TrackOrderResponse;
import order.processing.system.entity.Order;
import order.processing.system.exception.OrderNotFoundException;
import order.processing.system.mapper.OrderMapper;
import order.processing.system.ports.output.repository.OrderRepository;
import order.processing.system.valueobject.TrackingId;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderTrackCommandHandler {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    //@Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        UUID orderTrackingId = trackOrderQuery.getOrderTrackingId();
        Optional<Order> order = orderRepository.findTracking(new TrackingId(orderTrackingId));
        if (order.isEmpty()) {
            log.warn("Could not find order with tracking id: {}", orderTrackingId);
            throw new OrderNotFoundException("Could not find order with tracking id: " + orderTrackingId);
        }
        return orderMapper.orderToTrackOrderResponse(order.get());
    }
}
