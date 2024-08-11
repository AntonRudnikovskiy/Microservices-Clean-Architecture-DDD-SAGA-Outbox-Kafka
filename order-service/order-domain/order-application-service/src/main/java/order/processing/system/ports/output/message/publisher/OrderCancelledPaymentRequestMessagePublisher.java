package order.processing.system.ports.output.message.publisher;

import order.processing.system.event.OrderCancelledEvent;
import order.processing.system.event.publisher.DomainEventPublisher;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
