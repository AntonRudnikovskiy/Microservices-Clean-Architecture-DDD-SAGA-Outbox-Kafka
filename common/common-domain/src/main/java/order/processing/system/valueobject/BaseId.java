package order.processing.system.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class BaseId<T> {
    private final T value;

    protected BaseId(T value) {
        this.value = value;
    }
}
