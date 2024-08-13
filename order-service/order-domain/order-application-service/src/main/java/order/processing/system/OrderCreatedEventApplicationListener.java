package order.processing.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.processing.system.event.OrderCreatedEvent;
import order.processing.system.ports.output.message.publisher.OrderCreatedPaymentRequestMessagePublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedEventApplicationListener {
    private final OrderCreatedPaymentRequestMessagePublisher publisher;

    @TransactionalEventListener
    public void process(OrderCreatedEvent orderCreatedEvent) {
        publisher.publish(orderCreatedEvent);
        log.info("OrderCreatedEvent was published");
    }
}
