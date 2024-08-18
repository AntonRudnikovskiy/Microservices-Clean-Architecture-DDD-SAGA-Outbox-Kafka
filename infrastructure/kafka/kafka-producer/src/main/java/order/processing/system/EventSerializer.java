package order.processing.system;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventSerializer implements Serializer<Object> {
    private final ObjectMapper objectMapper;

    @Override
    public byte[] serialize(String topic, Object data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (IOException ex) {
            log.error("Unable to serialize message: {}", data, ex);
            return new byte[0];
        }
    }
}
