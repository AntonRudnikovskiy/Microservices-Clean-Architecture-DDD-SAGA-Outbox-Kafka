package order.processing.system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.processing.system.KafkaConsumerConfigData;
import order.processing.system.Topic;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerImpl implements KafkaProducer<Topic> {
    private final Producer<String, Object> producer;
    private final KafkaConsumerConfigData kafkaConsumerConfigData;

    @Override
    public void send(Topic topic) {
        ProducerRecord<String, Object> record = new ProducerRecord<>(
                kafkaConsumerConfigData.getTopicName(),
                topic.getTopic()
        );

        try {
            producer.send(record).get();
        } catch (InterruptedException ex) {
            log.warn("Await interrupted", ex);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof RuntimeException) {
                throw (RuntimeException) e.getCause();
            } else {
                throw new RuntimeException("Unable to send message in kafka", e.getCause());
            }
        }
    }
}
