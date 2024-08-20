package order.processing.system.order.adapter;

import lombok.RequiredArgsConstructor;
import order.processing.system.entity.Order;
import order.processing.system.order.entity.OrderEntity;
import order.processing.system.order.mapper.OrderDataAccessMapper;
import order.processing.system.order.repository.OrderJpaRepository;
import order.processing.system.ports.output.repository.OrderRepository;
import order.processing.system.valueobject.TrackingId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = orderJpaRepository.save(orderDataAccessMapper.orderToOrderEntity(order));
        return orderDataAccessMapper.orderToOrderEntity(orderEntity);
    }

    @Override
    public Optional<Order> findTracking(TrackingId trackingId) {
        return orderJpaRepository.findByTrackingId(trackingId.getValue())
                .map(orderDataAccessMapper::orderToOrderEntity);
    }
}
