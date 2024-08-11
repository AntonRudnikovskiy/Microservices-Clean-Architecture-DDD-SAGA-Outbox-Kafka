package order.processing.system.ports.input.service;

import jakarta.validation.Valid;
import order.processing.system.dto.CreateOrderCommand;
import order.processing.system.dto.CreateOrderResponse;
import order.processing.system.dto.track.TrackOrderQuery;
import order.processing.system.dto.track.TrackOrderResponse;

public interface OrderApplicationService {

    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

    TrackOrderResponse trackOrder(@Valid TrackOrderQuery createOrderCommand);
}
