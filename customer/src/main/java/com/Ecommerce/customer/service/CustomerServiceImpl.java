package com.Ecommerce.customer.service;



import com.Ecommerce.customer.dto.CustomerRequest;
import com.Ecommerce.customer.dto.CustomerResponse;
import com.Ecommerce.customer.entity.Customer;
import com.Ecommerce.customer.repository.CustomerRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(
            CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse createCustomer(
            CustomerRequest request) {

        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        Customer customer = new Customer();

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setCity(request.getCity());

        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setIsActive(true);

        Customer savedCustomer =
                customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Customer not found with ID: " + id
                ));

        return mapToResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CustomerResponse updateCustomer(
            Long id,
            CustomerRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Customer not found with ID: " + id
                ));

        if (!customer.getEmail().equals(request.getEmail())
                && customerRepository.existsByEmail(request.getEmail())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setCity(request.getCity());

        customer.setUpdatedAt(LocalDateTime.now());

        Customer updatedCustomer =
                customerRepository.save(customer);

        return mapToResponse(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {

        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Customer not found with ID: " + id
            );
        }

        customerRepository.deleteById(id);
    }

    private CustomerResponse mapToResponse(Customer customer) {

        CustomerResponse response = new CustomerResponse();

        response.setCustomerId(customer.getCustomerId());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setCity(customer.getCity());
        response.setIsActive(customer.getIsActive());
        response.setCreatedAt(customer.getCreatedAt());

        return response;
    }
}
