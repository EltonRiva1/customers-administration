package com.klayrocha.soap.webservices.customersadministration.soap;

import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.klayrocha.soap.webservices.customersadministration.bean.Customer;
import com.klayrocha.soap.webservices.customersadministration.service.CustomerDetailService;
import com.klayrocha.soap.webservices.customersadministration.soap.exception.CustomerNotFoundException;

import br.com.klayrocha.CustomerDetail;
import br.com.klayrocha.DeleteCustomerRequest;
import br.com.klayrocha.DeleteCustomerResponse;
import br.com.klayrocha.GetAllCustomerDetailRequest;
import br.com.klayrocha.GetAllCustomerDetailResponse;
import br.com.klayrocha.GetCustomerDetailRequest;
import br.com.klayrocha.GetCustomerDetailResponse;
import br.com.klayrocha.InsertCustomerRequest;
import br.com.klayrocha.InsertCustomerResponse;
import br.com.klayrocha.Status;

@Endpoint
public class CustomerDetailEndpoint {
	@Autowired
	CustomerDetailService customerDetailService;

	@PayloadRoot(namespace = "http://klayrocha.com.br", localPart = "GetCustomerDetailRequest")
	@ResponsePayload
	public JAXBElement<GetCustomerDetailResponse> processCustomerDetailRequest(
			@RequestPayload JAXBElement<GetCustomerDetailRequest> customerDetailRequest) throws Exception {
		int customerId = customerDetailRequest.getValue().getId();
		Customer customer = Optional.ofNullable(this.customerDetailService.findById(customerId))
				.orElseThrow(() -> new CustomerNotFoundException("Invalid Customer id " + customerId));
		GetCustomerDetailResponse getCustomerDetailResponse = this.convertToGetCustomerDetailResponse(customer);
		return new JAXBElement<>(new QName("http://klayrocha.com.br", "GetCustomerDetailResponse"),
				GetCustomerDetailResponse.class, getCustomerDetailResponse);
	}

	private CustomerDetail convertToCustomerDetail(Customer customer) {
		CustomerDetail customerDetail = new CustomerDetail();
		customerDetail.setId(customer.getId());
		customerDetail.setName(customer.getName());
		customerDetail.setPhone(customer.getPhone());
		customerDetail.setEmail(customer.getEmail());
		return customerDetail;
	}

	private GetCustomerDetailResponse convertToGetCustomerDetailResponse(Customer customer) {
		GetCustomerDetailResponse getCustomerDetailResponse = new GetCustomerDetailResponse();
		getCustomerDetailResponse.setCustomerDetail(this.convertToCustomerDetail(customer));
		return getCustomerDetailResponse;
	}

	private GetAllCustomerDetailResponse convertToGetAllCustomerDetailResponse(List<Customer> customers) {
		GetAllCustomerDetailResponse getAllCustomerDetailResponse = new GetAllCustomerDetailResponse();
		customers.stream().forEach(customer -> getAllCustomerDetailResponse.getCustomerDetail()
				.add(this.convertToCustomerDetail(customer)));
		return getAllCustomerDetailResponse;
	}

	@PayloadRoot(namespace = "http://klayrocha.com.br", localPart = "GetAllCustomerDetailRequest")
	@ResponsePayload
	public JAXBElement<GetAllCustomerDetailResponse> processGetAllCustomerDetailRequest(
			@RequestPayload JAXBElement<GetAllCustomerDetailRequest> getAllCustomerDetailRequest) {
		List<Customer> customers = this.customerDetailService.findAll();
		GetAllCustomerDetailResponse getAllCustomerDetailResponse = this
				.convertToGetAllCustomerDetailResponse(customers);
		return new JAXBElement<>(new QName("http://klayrocha.com.br", "GetAllCustomerDetailResponse"),
				GetAllCustomerDetailResponse.class, getAllCustomerDetailResponse);
	}

	private Status convertStatusSoap(com.klayrocha.soap.webservices.customersadministration.helper.Status status) {
		return status == com.klayrocha.soap.webservices.customersadministration.helper.Status.FAILURE ? Status.FAILURE
				: Status.SUCCESS;
	}

	@PayloadRoot(namespace = "http://klayrocha.com.br", localPart = "DeleteCustomerRequest")
	@ResponsePayload
	public JAXBElement<DeleteCustomerResponse> deleteCustomerRequest(
			@RequestPayload JAXBElement<DeleteCustomerRequest> deleteCustomerRequest) {
		int customerId = deleteCustomerRequest.getValue().getId();
		Status status = this.convertStatusSoap(this.customerDetailService.deleteById(customerId));
		DeleteCustomerResponse deleteCustomerResponse = new DeleteCustomerResponse();
		deleteCustomerResponse.setStatus(status);
		return new JAXBElement<>(new QName("http://klayrocha.com.br", "DeleteCustomerResponse"),
				DeleteCustomerResponse.class, deleteCustomerResponse);
	}

	@PayloadRoot(namespace = "http://klayrocha.com.br", localPart = "InsertCustomerRequest")
	@ResponsePayload
	public JAXBElement<InsertCustomerResponse> insertCustomerRequest(
			@RequestPayload JAXBElement<InsertCustomerRequest> insertCustomerRequest) {
		CustomerDetail customerDetail = insertCustomerRequest.getValue().getCustomerDetail();
		Status status = this.convertStatusSoap(this.customerDetailService.insert(this.convertCustomer(customerDetail)));
		InsertCustomerResponse insertCustomerResponse = new InsertCustomerResponse();
		insertCustomerResponse.setStatus(status);
		return new JAXBElement<>(new QName("http://klayrocha.com.br", "InsertCustomerResponse"),
				InsertCustomerResponse.class, insertCustomerResponse);
	}

	private Customer convertCustomer(CustomerDetail customerDetail) {
		return new Customer(customerDetail.getId(), customerDetail.getName(), customerDetail.getPhone(),
				customerDetail.getEmail());
	}
}