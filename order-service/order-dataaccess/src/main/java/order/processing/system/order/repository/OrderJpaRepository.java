package order.processing.system.order.repository;

import order.processing.system.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByTrackingId(UUID trackingId);
}
