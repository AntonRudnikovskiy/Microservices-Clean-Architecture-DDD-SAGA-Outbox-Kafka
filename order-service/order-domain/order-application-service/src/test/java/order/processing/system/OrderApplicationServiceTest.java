package order.processing.system;

import order.processing.system.dto.CreateOrderCommand;
import order.processing.system.dto.OrderAddress;
import order.processing.system.dto.OrderItem;
import order.processing.system.entity.Customer;
import order.processing.system.entity.Order;
import order.processing.system.entity.Product;
import order.processing.system.entity.Restaurant;
import order.processing.system.event.OrderCreatedEvent;
import order.processing.system.exception.OrderDomainException;
import order.processing.system.mapper.OrderMapper;
import order.processing.system.ports.output.repository.CustomerRepository;
import order.processing.system.ports.output.repository.OrderRepository;
import order.processing.system.ports.output.repository.RestaurantRepository;
import order.processing.system.valueobject.CustomerId;
import order.processing.system.valueobject.Money;
import order.processing.system.valueobject.OrderId;
import order.processing.system.valueobject.ProductId;
import order.processing.system.valueobject.RestaurantId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderApplicationServiceTest {
    @InjectMocks
    private OrderCreateCommandHandler orderCreateCommandHandler;
    @Spy
    private OrderMapper orderMapper;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private OrderDomainServiceImpl orderDomainService;
    @Mock
    private RestaurantRepository restaurantRepository;

    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongPrice;
    private CreateOrderCommand getCreateOrderCommandWrongProductPrice;
    private CreateOrderCommand orderNull;
    private Restaurant restaurant;
    private final UUID CUSTOMER_ID = UUID.fromString("e58ed763-928c-4155-bee9-fdbaaadc15f3");
    private final UUID RESTAURANT_ID = UUID.fromString("e58ed763-928c-4155-bee9-fdbaaadc15f3");
    private final UUID PRODUCT_ID = UUID.fromString("e58ed763-928c-4155-bee9-fdbaaadc15f3");
    private final UUID ORDER_ID = UUID.fromString("e58ed763-928c-4155-bee9-fdbaaadc15f3");
    private final BigDecimal PRICE = new BigDecimal("200.00");

    @BeforeEach
    public void init() {
        createOrderCommand = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(PRICE)
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build()))
                .build();

        createOrderCommandWrongPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("250.00"))
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("10.00"))
                                .subTotal(new BigDecimal("10.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build()))
                .build();

        getCreateOrderCommandWrongProductPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("210.00"))
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("60.00"))
                                .subTotal(new BigDecimal("60.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();


        restaurant = Restaurant.builder()
                .build();

        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

//        Restaurant restaurant = Restaurant.builder()
//                .products(List.of(new Product(new ProductId(PRODUCT_ID), "product-1",
//                                new Money(new BigDecimal("50.00"))),
//                        new Product(new ProductId(PRODUCT_ID), "product-2",
//                                new Money(new BigDecimal("50.00")))))
//                .active(true)
//                .build();
//        restaurant.setId(new RestaurantId(createOrderCommand.getRestaurantId()));
//
//        Order order = orderMapper.createOrderCommandToOrder(createOrderCommand);
//        order.setId(new OrderId(ORDER_ID));
//
//        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
//        when(restaurantRepository.findRestaurantInformation(orderMapper.createOrderCommandToRestaurant(createOrderCommand)))
//                .thenReturn(Optional.of(restaurant));
//        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    public void testCreateOrderWithCustomerNotExist() {
        orderNull = CreateOrderCommand.builder().build();
        when(customerRepository.findCustomer(null)).thenReturn(Optional.empty());
        assertThrows(OrderDomainException.class,
                () -> orderCreateCommandHandler.createOrder(orderNull));
    }

    @Test
    public void testCreateOrderWithRestaurantNotExist() {
        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));
        Restaurant restaurant = Restaurant.builder().build();

        orderNull = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .items(new ArrayList<>())
                .build();

        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(orderMapper.createOrderCommandToRestaurant(orderNull)).thenReturn(restaurant);
        when(restaurantRepository.findRestaurantInformation(restaurant)).thenReturn(Optional.empty());
        assertThrows(OrderDomainException.class,
                () -> orderCreateCommandHandler.createOrder(orderNull));
    }

    @Test
    public void testCreateOrderWithWrongPrice() {
        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

        Order order = orderMapper.createOrderCommandToOrder(createOrderCommandWrongPrice);
        order.setId(new OrderId(ORDER_ID));

        restaurant.setId(new RestaurantId(RESTAURANT_ID));
        restaurant = Restaurant.builder()
                .products(List.of(new Product(new ProductId(PRODUCT_ID), "product-1",
                                new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID), "product-2",
                                new Money(new BigDecimal("40.00")))))
                .active(true)
                .build();

        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(orderMapper.createOrderCommandToRestaurant(createOrderCommandWrongPrice)).thenReturn(restaurant);
        when(restaurantRepository.findRestaurantInformation(restaurant)).thenReturn(Optional.of(restaurant));
        when(orderDomainService.validateAndInitiateOrder(order, restaurant)).thenReturn(new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of("UTC"))));


        orderCreateCommandHandler.createOrder(createOrderCommandWrongPrice);
        assertThrows(OrderDomainException.class,
                () -> orderCreateCommandHandler.createOrder(createOrderCommandWrongPrice));
//        assertEquals(orderDomainException.getMessage(),
//                "Could not find customer with customer id: e58ed763-928c-4155-bee9-fdbaaadc15f3");
    }

    @Test
    public void testCreateOrderWithWrongProductPrice() {
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderCreateCommandHandler.createOrder(getCreateOrderCommandWrongProductPrice));
//        assertEquals(orderDomainException.getMessage(),
//                "Could not find customer with customer id: e58ed763-928c-4155-bee9-fdbaaadc15f3");
    }
}

