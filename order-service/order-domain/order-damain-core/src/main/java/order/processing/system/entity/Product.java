package order.processing.system.entity;

import lombok.Getter;
import lombok.Setter;
import order.processing.system.valueobject.Money;
import order.processing.system.valueobject.ProductId;

@Getter
@Setter
public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId, String name, Money price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }
}
