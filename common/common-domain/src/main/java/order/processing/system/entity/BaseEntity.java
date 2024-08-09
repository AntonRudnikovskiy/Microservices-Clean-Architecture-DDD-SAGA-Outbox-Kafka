package order.processing.system.entity;

import lombok.Data;

@Data
public abstract class BaseEntity<ID> {
    private ID id;
}
