package order.processing.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.processing.system.dto.CreateOrderCommand;
import order.processing.system.dto.CreateOrderResponse;
import order.processing.system.dto.track.TrackOrderQuery;
import order.processing.system.dto.track.TrackOrderResponse;
import order.processing.system.ports.input.service.OrderApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderApplicationService orderApplicationService;

    @PostMapping("/")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderCommand createOrderCommand) {
        log.info("Creating order for customer: {} restaurant: {}", createOrderCommand.getCustomerId(),
                createOrderCommand.getRestaurantId());
        CreateOrderResponse order = orderApplicationService.createOrder(createOrderCommand);
        log.info("Order with tracking id: {}", order.getOrderTrackingId());
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<TrackOrderResponse> trackOrder(@PathVariable UUID trackingId) {
        TrackOrderResponse trackOrder = orderApplicationService.trackOrder(new TrackOrderQuery(trackingId));
        log.info("Returning order status with tracking id: {}", trackOrder.getOrderTrackingId());
        return ResponseEntity.ok(trackOrder);
    }
}
