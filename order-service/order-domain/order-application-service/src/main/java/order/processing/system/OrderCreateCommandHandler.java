package order.processing.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.processing.system.dto.CreateOrderCommand;
import order.processing.system.dto.CreateOrderResponse;
import order.processing.system.entity.Customer;
import order.processing.system.entity.Order;
import order.processing.system.entity.Restaurant;
import order.processing.system.event.OrderCreatedEvent;
import order.processing.system.exception.OrderDomainException;
import order.processing.system.mapper.OrderMapper;
import order.processing.system.ports.output.repository.CustomerRepository;
import order.processing.system.ports.output.repository.OrderRepository;
import order.processing.system.ports.output.repository.RestaurantRepository;
import order.processing.system.valueobject.RestaurantId;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreateCommandHandler {
    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderMapper orderMapper;

    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        Order savedOrder = saveOder(order);
        log.info("Order is created with id: {}", order.getId().getValue());
        return orderMapper.orderResponse(savedOrder);
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderMapper.createOrderCommandToRestaurant(createOrderCommand);
        restaurant.setId(new RestaurantId(createOrderCommand.getRestaurantId()));
        Optional<Restaurant> restaurantInformation = restaurantRepository.findRestaurantInformation(restaurant);
        if (restaurantInformation.isEmpty()) {
            log.warn("Could not find restaurant with restaurant id: {}", createOrderCommand.getRestaurantId());
            throw new OrderDomainException("Could not find restaurant with restaurant id: " +
                    createOrderCommand.getRestaurantId());
        }
        return restaurantInformation.get();
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if (customer.isEmpty()) {
            log.warn("Could not find customer with customer id: {}", customerId);
            throw new OrderDomainException("Could not find customer with customer id: " + customerId);
        }
    }

    private Order saveOder(Order order) {
        Order orderResult = orderRepository.save(order);
        if (orderResult == null) {
            log.error("Order was not found: {}", order.getId().getValue());
            throw new OrderDomainException("Order was not found");
        }
        log.info("Order is saved with id: {}", order.getId().getValue());
        return orderResult;
    }
}
