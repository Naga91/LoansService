package com.nt.loans.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nt.loans.config.LoansServiceConfig;
import com.nt.loans.model.Customer;
import com.nt.loans.model.Loans;
import com.nt.loans.model.Properties;
import com.nt.loans.repository.LoansRepository;

@RestController
public class LoansController {

	@Autowired
	private LoansRepository loansRepository;

	@Autowired
	private LoansServiceConfig loansConfig;

	@PostMapping("/myLoans")
	public List<Loans> getLoansDetails(@RequestHeader("nt-trace-id") String traceId, @RequestBody Customer customer) {
		System.out.println("Inside Loans service, traceId: " + traceId);
		List<Loans> loans = this.loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
		if (loans != null) {
			return loans;
		} else {
			return null;
		}
	}

	@GetMapping("/loans/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		Properties properties = new Properties(loansConfig.getMsg(), loansConfig.getBuildVersion(),
				loansConfig.getMailDetails(), loansConfig.getActiveBranches());
		String response = new ObjectMapper().writeValueAsString(properties);
		return response;
	}

}
