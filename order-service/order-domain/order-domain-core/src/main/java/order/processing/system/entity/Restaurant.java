package order.processing.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import order.processing.system.valueobject.RestaurantId;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class Restaurant extends AggregateRoot<RestaurantId> {
    private final List<Product> products;
    private boolean active;
}
