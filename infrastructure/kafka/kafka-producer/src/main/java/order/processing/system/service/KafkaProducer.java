package order.processing.system.service;

public interface KafkaProducer<T> {
    void send(T t);
}
