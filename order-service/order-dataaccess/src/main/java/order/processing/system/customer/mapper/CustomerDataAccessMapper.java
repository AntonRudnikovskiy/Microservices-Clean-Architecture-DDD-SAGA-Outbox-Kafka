package order.processing.system.customer.mapper;

import order.processing.system.customer.entity.CustomerEntity;
import order.processing.system.entity.Customer;
import order.processing.system.valueobject.CustomerId;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataAccessMapper {

    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return new Customer(new CustomerId(customerEntity.getId()));
    }
}
