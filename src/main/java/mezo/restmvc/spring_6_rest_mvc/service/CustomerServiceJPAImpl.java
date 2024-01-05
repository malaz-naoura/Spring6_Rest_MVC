package mezo.restmvc.spring_6_rest_mvc.service;

import lombok.RequiredArgsConstructor;
import mezo.restmvc.spring_6_rest_mvc.mappers.CustomerMapper;
import mezo.restmvc.spring_6_rest_mvc.model.CustomerDTO;
import mezo.restmvc.spring_6_rest_mvc.repositories.CustomerRepo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPAImpl implements CustomerService {
    private final CustomerRepo customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID uuid) {
        return Optional.ofNullable(customerMapper
                .objToDto(customerRepository.findById(uuid).orElse(null)));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                                 .map(customerMapper::objToDto)
                                 .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        return customerMapper.objToDto(customerRepository
                .save(customerMapper.dtoToObj(customer)));
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            foundCustomer.setName(customer.getName());
            atomicReference.set(Optional.of(customerMapper
                    .objToDto(customerRepository.save(foundCustomer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        if(customerRepository.existsById(customerId)){
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            if (StringUtils.hasText(customer.getName())){
                foundCustomer.setName(customer.getName());
            }
            atomicReference.set(Optional.of(customerMapper
                    .objToDto(customerRepository.save(foundCustomer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
}
