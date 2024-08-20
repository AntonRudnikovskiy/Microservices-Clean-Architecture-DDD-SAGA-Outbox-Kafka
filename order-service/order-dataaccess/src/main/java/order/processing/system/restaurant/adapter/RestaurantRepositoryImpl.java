package order.processing.system.restaurant.adapter;

import lombok.RequiredArgsConstructor;
import order.processing.system.entity.Restaurant;
import order.processing.system.ports.output.repository.RestaurantRepository;
import order.processing.system.restaurant.entity.RestaurantEntity;
import order.processing.system.restaurant.mapper.RestaurantDataAccessMapper;
import order.processing.system.restaurant.repository.RestaurantJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepository {
    private final RestaurantJpaRepository repository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;
    @Override
    public Optional<Restaurant> findRestaurantInformation(Restaurant restaurant) {
        List<UUID> restaurantProducts = restaurantDataAccessMapper.restaurantToRestaurantProducts(restaurant);
        Optional<List<RestaurantEntity>> restaurantIdAndProductIdIn = repository.findRestaurantIdAndProductIdIn(
                restaurant.getId().getValue(), restaurantProducts);
        return restaurantIdAndProductIdIn.map(restaurantDataAccessMapper::restaurantEntityToRestaurant);
    }
}
