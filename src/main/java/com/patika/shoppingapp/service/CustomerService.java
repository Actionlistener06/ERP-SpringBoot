package com.patika.shoppingapp.service;

import com.patika.shoppingapp.model.Customer;
import com.patika.shoppingapp.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer getCustomerById(String customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    @Transactional
    public Customer createCustomer(Customer customer) {

        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(String customerId, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(customerId).orElse(null);
        if (existingCustomer == null) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }
        return customerRepository.save(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(String customerId) {
        Customer existingCustomer = customerRepository.findById(customerId).orElse(null);
        if (existingCustomer == null) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }


        customerRepository.delete(existingCustomer);
    }

}
