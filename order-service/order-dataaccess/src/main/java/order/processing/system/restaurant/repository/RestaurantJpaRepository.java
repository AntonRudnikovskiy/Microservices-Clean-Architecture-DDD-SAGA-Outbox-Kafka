package order.processing.system.restaurant.repository;

import order.processing.system.restaurant.entity.RestaurantEntity;
import order.processing.system.restaurant.entity.RestaurantEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, RestaurantEntityId> {

    Optional<List<RestaurantEntity>> findRestaurantIdAndProductIdIn(UUID restaurantId, List<UUID> productIds);
}
