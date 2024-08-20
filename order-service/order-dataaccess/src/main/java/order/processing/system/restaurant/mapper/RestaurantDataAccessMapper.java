package order.processing.system.restaurant.mapper;

import order.processing.system.entity.Product;
import order.processing.system.entity.Restaurant;
import order.processing.system.restaurant.entity.RestaurantEntity;
import order.processing.system.restaurant.exception.RestaurantDataAccessException;
import order.processing.system.valueobject.Money;
import order.processing.system.valueobject.ProductId;
import order.processing.system.valueobject.RestaurantId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class RestaurantDataAccessMapper {

    public List<UUID> restaurantToRestaurantProducts(Restaurant restaurant) {
        return restaurant.getProducts().stream()
                .map(product -> product.getId().getValue())
                .toList();
    }

    public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntities) {
        RestaurantEntity restaurantEntity =
                restaurantEntities.stream().findFirst().orElseThrow(() ->
                        new RestaurantDataAccessException("Restaurant could not be found!"));

        List<Product> restaurantProducts = restaurantEntities.stream().map(entity ->
                new Product(new ProductId(entity.getProductId()), entity.getProductName(),
                        new Money(entity.getProductPrice()))).toList();

        return Restaurant.Builder.builder()
                .id(new RestaurantId(restaurantEntity.getRestaurantId()))
                .products(restaurantProducts)
                .active(restaurantEntity.isRestaurantActive())
                .build();
    }

}
