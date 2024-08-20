package order.processing.system.customer.adapter;

import lombok.RequiredArgsConstructor;
import order.processing.system.customer.mapper.CustomerDataAccessMapper;
import order.processing.system.customer.repository.CustomerJpaRepository;
import order.processing.system.entity.Customer;
import order.processing.system.ports.output.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {
    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerDataAccessMapper customerDataAccessMapper;
    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
        return customerJpaRepository.findById(customerId)
                .map(customerDataAccessMapper::customerEntityToCustomer);
    }
}
