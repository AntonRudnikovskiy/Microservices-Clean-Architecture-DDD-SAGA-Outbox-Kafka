package order.processing.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.processing.system.event.OrderCreatedEvent;
import order.processing.system.ports.output.message.publisher.OrderCreatedPaymentRequestMessagePublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedEventApplicationListener {
    private final OrderCreatedPaymentRequestMessagePublisher publisher;

    @EventListener
    public void process(OrderCreatedEvent orderCreatedEvent) {
        publisher.publish(orderCreatedEvent);
        log.info("OrderCreatedEvent was published");
    }
}
