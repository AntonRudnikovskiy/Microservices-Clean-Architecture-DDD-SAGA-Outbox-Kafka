package order.processing.system.ports.output.message.publisher;

import order.processing.system.event.OrderCreatedEvent;
import order.processing.system.event.publisher.DomainEventPublisher;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
