package com.coopbank.admin.administrative_service.controllers;

import com.coopbank.admin.administrative_service.CustomerIprsIdentificationType;
import com.coopbank.admin.administrative_service.dtos.SanctionDetails;
import com.coopbank.admin.administrative_service.client.CustomerSanctionDetailsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coopbank.admin.administrative_service.dtos.StandardResponse;
import com.coopbank.admin.administrative_service.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
	private final CustomerService customerService;

	@GetMapping("{idNumber}/iprs")
	public ResponseEntity<?> checkIprs(@PathVariable String idNumber, @RequestParam("idType") String idType) {
		if (idType == null || !StringUtils.hasText(idType)) {
			return new ResponseEntity<StandardResponse<?>>(
					StandardResponse.builder().message("Id type is required!").build(), HttpStatus.BAD_REQUEST);
		}

		System.out.println("CustomerIprsIdentificationType.NATID" + CustomerIprsIdentificationType.NATID);
		System.out.println("idNumber" + idNumber);


		var iprsDetailsResult = customerService.findIprsDetailsBy(CustomerIprsIdentificationType.NATID, idNumber);

		if (iprsDetailsResult.isEmpty()) {
			return new ResponseEntity<StandardResponse<?>>(
					StandardResponse.builder().message("Customer's iprs details not found!")
							.status(404)
							.build(),
					HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<StandardResponse<?>>(StandardResponse.builder().data(iprsDetailsResult.get())
				.message("Customer's iprs details have been found successfully.")
				.status(200)
				.build(), HttpStatus.OK);
	}


	@GetMapping("{idNumber}/sanction-details")
	public ResponseEntity<?> checkSanctionDetails(@PathVariable String idNumber,
												  @RequestParam("minMatchScore") String minMatchScore) {

//		if (idType == null || !StringUtils.hasText(idType.name())) {
//			return new ResponseEntity<StandardResponse<?>>(
//					StandardResponse.builder().message("Id type is required!").build(), HttpStatus.BAD_REQUEST);
//		}

		SanctionDetails details = new SanctionDetails();
		details.setMinMatchScore(minMatchScore);
		details.setIdentificationDocNo(idNumber);

		var blacklistDetailsResult = customerService.findSanctionDetailsBy(details);

		if (blacklistDetailsResult.isPresent()) {
			return new ResponseEntity<StandardResponse<?>>(StandardResponse.builder().data(blacklistDetailsResult.get())
					.message("Customer sanction details found.").build(), HttpStatus.OK);
		}

		return new ResponseEntity<StandardResponse<?>>(
				StandardResponse.builder().message("Customer sanction details not found!").build(), HttpStatus.OK);
	}
//	// TODO - REMOVE BANK ID
//	@PostMapping("{idNumber}/eligibility-check")
//	public ResponseEntity<?> eligibility(@RequestParam("idType") CustomerIdIdentificationType idType, @PathVariable String idNumber,
//			@RequestParam("bankCode") String bankCode, @RequestBody EligibilityRequest body) {
//		log.info("Params: idType: {}, idNumber: {}, bankCode {}", idType, idNumber, bankCode);
//
//		if (bankCode == null || !StringUtils.hasText(bankCode) || idType == null || !StringUtils.hasText(idType.name()) || idNumber == null || !StringUtils.hasText(idNumber)) {
//
//			return new ResponseEntity<StandardResponse<?>>(
//					StandardResponse.builder().message("Id type, id number and Bank code are required!").build(),
//					HttpStatus.BAD_REQUEST);
//		}
//
//		EligibilityResult response = customerService.checkEligibility(idType, idNumber, body);
//
//		return new ResponseEntity<StandardResponse<?>>(
//				StandardResponse.builder().data(response).message("Eligibility populated successfully.").build(), HttpStatus.OK);
//	}


//	@GetMapping("{idNumber}/blacklist")
//	public ResponseEntity<?> checkBlacklist(@PathVariable String idNumber, @RequestParam("idType") String idType) {
//		if (idType == null || !StringUtils.hasText(idType)) {
//			return new ResponseEntity<StandardResponse<?>>(
//					StandardResponse.builder().message("Id type is required!").build(), HttpStatus.BAD_REQUEST);
//		}
//
//		var blacklistDetailsResult = customerService.findBlacklistDetailsBy(CustomerBlacklistIdentificationType.ID_DOC_NUMBER, idNumber);
//
//		if (blacklistDetailsResult.isPresent()) {
//			return new ResponseEntity<StandardResponse<?>>(StandardResponse.builder().data(blacklistDetailsResult.get())
//					.message("Customer is blacklisted!").build(), HttpStatus.OK);
//		}
//
//		return new ResponseEntity<StandardResponse<?>>(
//				StandardResponse.builder().message("Customer is not blacklisted.").build(), HttpStatus.OK);
//	}
}
