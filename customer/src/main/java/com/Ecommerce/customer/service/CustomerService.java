package com.Ecommerce.customer.service;

import com.Ecommerce.customer.dto.CustomerRequest;
import com.Ecommerce.customer.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse getCustomerById(Long id);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse updateCustomer(
            Long id,
            CustomerRequest request);

    void deleteCustomer(Long id);
}
