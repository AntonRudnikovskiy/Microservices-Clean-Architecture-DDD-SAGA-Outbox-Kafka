package order.processing.system.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import order.processing.system.valueobject.OrderApprovalStatus;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantApprovalResponse {
    private String id;
    private String sagaId;
    private String orderId;
    private String restaurantId;
    private Instant createdAt;
    private OrderApprovalStatus orderApprovalStatus;
    private final List<String> failureMessages;
}
