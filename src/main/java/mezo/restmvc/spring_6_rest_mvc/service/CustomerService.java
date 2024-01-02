package mezo.restmvc.spring_6_rest_mvc.service;

import mezo.restmvc.spring_6_rest_mvc.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    Customer getCustomerById(UUID uuid);

    List<Customer> getAllCustomers();

    Customer saveNewCustomer(Customer customer);

    void updateCustomerById(UUID customerId, Customer customer);

    void deleteCustomerById(UUID customerId);

    void patchCustomerById(UUID customerId, Customer customer);
}
