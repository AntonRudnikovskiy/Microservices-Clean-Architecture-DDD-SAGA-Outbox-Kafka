package order.processing.system.valueobject;

import java.util.UUID;

public abstract class OrderId extends BaseId<UUID> {
    protected OrderId(UUID value) {
        super(value);
    }
}
