package order.processing.system.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import order.processing.system.valueobject.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PaymentResponse {
    private String id;
    private String sagaId;
    private String orderId;
    private String customerId;
    private BigDecimal price;
    private Instant createdAt;
    private PaymentStatus paymentStatus;
    private final List<String> failureMessages;
}
