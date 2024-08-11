package order.processing.system;

import lombok.extern.slf4j.Slf4j;
import order.processing.system.dto.track.TrackOrderQuery;
import order.processing.system.dto.track.TrackOrderResponse;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderTrackCommandHandler {

    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return null;
    }
}
