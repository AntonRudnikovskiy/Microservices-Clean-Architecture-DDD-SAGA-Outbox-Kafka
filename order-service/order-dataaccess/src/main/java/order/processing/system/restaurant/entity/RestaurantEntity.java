package order.processing.system.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(RestaurantEntityId.class)
@Builder
@Table(name = "order_restaurant_m_view", schema = "restaurant")
public class RestaurantEntity {

    @Id
    private UUID restaurantId;

    @Id
    private UUID productId;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "restaurant_active")
    private boolean restaurantActive;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private BigDecimal productPrice;
}
