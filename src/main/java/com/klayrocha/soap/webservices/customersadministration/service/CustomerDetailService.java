package com.klayrocha.soap.webservices.customersadministration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.klayrocha.soap.webservices.customersadministration.bean.Customer;
import com.klayrocha.soap.webservices.customersadministration.helper.Status;
import com.klayrocha.soap.webservices.customersadministration.repository.CustomerRepository;

@Component
public class CustomerDetailService {
	@Autowired
	private CustomerRepository customerRepository;

	public Customer findById(Integer id) {
		return this.customerRepository.findById(id).orElse(null);
	}

	public List<Customer> findAll() {
		return this.customerRepository.findAll();
	}

	public Status deleteById(Integer id) {
		try {
			this.customerRepository.deleteById(id);
			return Status.SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			return Status.FAILURE;
		}
	}

	public Status insert(Customer customer) {
		try {
			this.customerRepository.save(customer);
			return Status.SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			return Status.FAILURE;
		}
	}
}
