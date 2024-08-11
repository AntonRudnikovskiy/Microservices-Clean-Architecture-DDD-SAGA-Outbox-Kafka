package order.processing.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.processing.system.dto.CreateOrderCommand;
import order.processing.system.dto.CreateOrderResponse;
import order.processing.system.dto.track.TrackOrderQuery;
import order.processing.system.dto.track.TrackOrderResponse;
import order.processing.system.ports.input.service.OrderApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
class OrderApplicationServiceImpl implements OrderApplicationService {
    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final OrderTrackCommandHandler orderTrackCommandHandler;

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery createOrderCommand) {
        return orderTrackCommandHandler.trackOrder(createOrderCommand);
    }
}
