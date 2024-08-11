package order.processing.system.event.publisher;

import order.processing.system.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {

    void publish(T domainEvent);
}
