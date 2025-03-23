package com.coopbank.admin.administrative_service.services.impl;

import java.util.Optional;

import com.coopbank.admin.administrative_service.CustomerIprsIdentificationType;
import com.coopbank.admin.administrative_service.client.CustomerSanctionDetailsClient;
import com.coopbank.admin.administrative_service.client.IPRSClient;
import com.coopbank.admin.administrative_service.dtos.SanctionDetails;
import com.coopbank.admin.administrative_service.dtos.SanctionDetailsSoaResponse;
import org.springframework.stereotype.Service;
import com.coopbank.admin.administrative_service.dtos.IprsDetails;
import com.coopbank.admin.administrative_service.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
//	private final CustomerCifsClient customerIdClient;
//	private final CustomerAccountClient customerAccountClient;
	private final IPRSClient iprsClient;
//	private final CustomerBlacklistClient blacklistClient;
	private final CustomerSanctionDetailsClient sanctionDetailsClient;
	private final String minMatchScore = "99";
//	private final CustomerNameMatcher customerNameMatcher;
//	private final CustomerDetailsRestClient customerDetailsClient;
//	private final PhoneNumberValidatorClient phoneNumberValidatorClient;
//	private final MiscellaneousProperties miscellaneousProperties;
//	private final MiddleNameComparator middleNameComparator;


	@Override
	public Optional<IprsDetails> findIprsDetailsBy(CustomerIprsIdentificationType type, String idNo) {
		System.out.println(iprsClient);
		return iprsClient.getIprsDetails(type, idNo);
	}

	@Override
	public Optional<SanctionDetailsSoaResponse> findSanctionDetailsBy(SanctionDetails details) {
		return sanctionDetailsClient.getSanctionDetails(details);
	}
//
//	@Override
//	public Optional<BlacklistDetailsRsType> findBlacklistDetailsBy(CustomerBlacklistIdentificationType type, String idNo) {
//		return blacklistClient.getBlacklistDetails(type, idNo);
//	}



//	@Override
//	public EligibilityResult checkEligibility(CustomerIdIdentificationType idType, String idNumber,
//			EligibilityRequest request) {
//		EligibilityResult result = new EligibilityResult();
//		result.setIdentificationType(idType);
//		result.setIdentificationNo(idNumber);
//
//		boolean isExistingCustomer = false;
//		// 1. check if exists on core
////		List<CustomerIDDetails> customerCifs = findCifsBy(idType, idNumber);
//		var coreCutomerDetailsResults = customerDetailsClient.getCustomerDetails(idType, idNumber);
//
//		if (coreCutomerDetailsResults.isPresent()) {
//			var coreCustomerDetails = coreCutomerDetailsResults.get();
//
//			String cif = null;
//			CustomerDetails corePartyDetails = null;
//
//			if (coreCustomerDetails.size() > 0) {
//
//				for (var partyDetails : coreCustomerDetails) {
//					if (partyDetails.getIdentificationDetails() != null
//							&& partyDetails.getIdentificationDetails().getDocumentCode() != null
//							&& partyDetails.getIdentificationDetails().getReferenceNumber() != null
//							&& partyDetails.getIdentificationDetails().getDocumentCode().equalsIgnoreCase(idType.name())
//							&& partyDetails.getIdentificationDetails().getReferenceNumber().equalsIgnoreCase(idNumber)
//							&& partyDetails.getCustomerType() != null
//							&& partyDetails.getCustomerType().equalsIgnoreCase("Retail")) {
//
//						isExistingCustomer = true;
//						result.setIsExistingCustomer(isExistingCustomer);
//
//						cif = partyDetails.getCustomerId();
//						result.setCif(cif);
//						corePartyDetails = partyDetails;
//
//						break;
//					}
//				}
//
////				if (cif == null || !StringUtils.hasLength(cif.trim()) || corePartyDetails == null) {
////					result.setEligibilityStatus(EligibilityStatus.NOT_RETAIL_CUSTOMER);
////
////					log.info("{}: {}", result.getEligibilityStatus(), result);
////					return result;
////				}
//
////				result.setDateOfBirth(corePartyDetails.getBirthDate() != null
////						? corePartyDetails.getBirthDate().toLocalDate().toString()
////						: null);
//
////				var person = corePartyDetails.getPersonName();
////
////				if (person == null) {
////					result.setEligibilityStatus(EligibilityStatus.CORE_MISMATCH);
////					log.debug("Customer names not found");
////
////					return result;
////				}
//
////				result.setFullName(person.getFullName());
////
////				CustomerNameMatcherResult firstNameResult = customerNameMatcher.compare(
////						request.getFirstName() != null && StringUtils.hasLength(request.getFirstName())
////								? request.getFirstName().trim()
////								: "",
////						person.getFirstName() != null && StringUtils.hasLength(person.getFirstName())
////								? person.getFirstName().trim()
////								: "");
////				CustomerNameMatcherResult lastNameResult = customerNameMatcher.compare(
////						request.getLastName() != null && StringUtils.hasLength(request.getLastName())
////								? request.getLastName().trim()
////								: "",
////						person.getLastName() != null && StringUtils.hasLength(person.getLastName())
////								? person.getLastName().trim()
////								: "");
////
////				if (!firstNameResult.getPass() || !lastNameResult.getPass()) {
////					result.setEligibilityStatus(EligibilityStatus.CORE_MISMATCH);
////					log.debug("First name {}: pass rate = {}, Last name {}: pass rate = {}", person.getFirstName(),
////							firstNameResult.getPass(), person.getLastName(), lastNameResult.getPass());
////
////					log.info("{}: {}", result.getEligibilityStatus(), result);
////
////					return result;
////				}
////			}
////		}
//
////		if (customerCifs.size() > 0) {
////			String cif = null;
////			CustomerIDDetails customerRetailDetails = null;
////
////			for (var details : customerCifs) {
////				if ((details.getDateOfIncorporation() == null
////						|| !StringUtils.hasText(details.getDateOfIncorporation().trim()))
////						&& details.getCustomerId() != null && StringUtils.hasLength(details.getCustomerId().trim())) {
////					cif = details.getCustomerId();
////					customerRetailDetails = details;
////					break;
////				}
////			}
////
////			if (cif == null || !StringUtils.hasLength(cif.trim()) && customerRetailDetails == null) {
////				result.setEligibilityStatus(EligibilityStatus.NOT_RETAIL_CUSTOMER);
////
////				log.info("{}: {}", result.getEligibilityStatus(), result);
////				return result;
////			}
////
////			// 2. if is existing customer verify if eligible
////			result.setIsExistingCustomer(true);
////			result.setCif(cif);
////			result.setFullName(customerRetailDetails.getCustomerName());
////			// TODO
////			result.setDateOfBirth(customerRetailDetails.getDateOfBirth());
////
////			var nameResult = customerNameMatcher.compare(customerRetailDetails.getCustomerName(),
////					request.getFullName());
////
////			if (!nameResult.getPass()) {
////				result.setEligibilityStatus(EligibilityStatus.CORE_MISMATCH);
////				log.debug("Names {}, {}: pass rate = {}", customerRetailDetails.getCustomerName(),
////						request.getFullName(), nameResult);
////
////				log.info("{}: {}", result.getEligibilityStatus(), result);
////
////				return result;
////			}
//
//				if (isExistingCustomer) {
//
//					// 2.1. check if blacklisted
//					var blacklistResult = findBlacklistDetailsBy(CustomerBlacklistIdentificationType.CIF_NUMBER, cif);
//
//					if (blacklistResult.isPresent()) {
//						result.setEligibilityStatus(EligibilityStatus.BLACKLISTED);
//						result.setIsBlacklisted(true);
//						BlacklistDetailsRsType blacklistDetailsRsType = blacklistResult.get();
//						if (blacklistDetailsRsType.getCustomerDetails().size() > 0)
//							result.setBlackListDetails(
//									new BlackListDetails(blacklistDetailsRsType.getCustomerDetails().get(1)));
//						log.info("{}: {}", result.getEligibilityStatus(), result);
//
//						return result;
//					}
//					result.setIsBlacklisted(false);
////
////					CustomerSanctionDetailsIdentificationType customerSanctionDetailsIdentificationType = null;
////
////					if(idType.equals(CustomerIdIdentificationType.NATID)) {
////						customerSanctionDetailsIdentificationType = CustomerSanctionDetailsIdentificationType.NATIID;
////					} else if(idType.equals(CustomerIdIdentificationType.PSPRT)) {
////						customerSanctionDetailsIdentificationType = CustomerSanctionDetailsIdentificationType.PSPRT;
////					} else {
////						customerSanctionDetailsIdentificationType = CustomerSanctionDetailsIdentificationType.ALIENID;
////					}
////
////					// 2.2. check if sanctioned
////					var sanctionRequest = new SanctionDetails();
////					sanctionRequest.setMinMatchScore(minMatchScore);
////					sanctionRequest.setCustomerType("person");
////					sanctionRequest.setIdentificationDocType(customerSanctionDetailsIdentificationType);
////					sanctionRequest.setIdentificationDocNo(idNumber);
////					sanctionRequest.setFullName(corePartyDetails.getPersonName() != null
////							&& corePartyDetails.getPersonName().getFullName() != null
////							&& StringUtils.hasLength(corePartyDetails.getPersonName().getFullName().trim())
////									? corePartyDetails.getPersonName().getFullName().trim()
////									: "");
////					sanctionRequest.setDateOfBirth(corePartyDetails.getBirthDate() != null
////							? corePartyDetails.getBirthDate().toLocalDate().toString()
////							: null);
////
////					var sanctionDetailsResult = findSanctionDetailsBy(sanctionRequest);
////
////					if (sanctionDetailsResult.isPresent()) {
////						result.setEligibilityStatus(EligibilityStatus.SANCTIONED);
////						result.setIsSanctioned(true);
////						SanctionDetailsSoaResponse sanctionDetailsSoaResponse = sanctionDetailsResult.get();
////						SanctionDetailsResponse sanctionDetailsResponse = null;
////
////						if (sanctionDetailsSoaResponse.getPersons().size() > 0) {
////							var details = sanctionDetailsSoaResponse.getPersons().get(0);
////							if (details != null && details.getIdentificationDocType() != null
////									&& details.getIdentificationDocNo() != null) {
////								sanctionDetailsResponse = new SanctionDetailsResponse();
////								sanctionDetailsResponse.setIdentificationType(details.getIdentificationDocType());
////								sanctionDetailsResponse.setIdentificationNo(details.getIdentificationDocNo());
////							}
////						}
////
////						if (sanctionDetailsResponse == null && sanctionDetailsSoaResponse.getEntities().size() > 0) {
////							var details = sanctionDetailsSoaResponse.getEntities().get(0);
////							if (details != null && details.getIdentificationDocType() != null
////									&& details.getIdentificationDocNo() != null) {
////								sanctionDetailsResponse = new SanctionDetailsResponse();
////								sanctionDetailsResponse.setIdentificationType(details.getIdentificationDocType());
////								sanctionDetailsResponse.setIdentificationNo(details.getIdentificationDocNo());
////							}
////						}
////
////						if (sanctionDetailsResponse != null)
////							result.setSanctionDetails(sanctionDetailsResponse);
////
////						log.info("{}: {}", result.getEligibilityStatus(), result);
////						return result;
////					}
////
////					result.setIsSanctioned(false);
//				}
//
////				result.setEligibilityStatus(EligibilityStatus.ELIGIBLE);
////
////				log.info("{}: {}", result.getEligibilityStatus(), result);
////				return result;
//			}
//		}
//		// 2. check iprs
//		result.setIsExistingCustomer(isExistingCustomer);
//
//		CustomerIprsIdentificationType iprsId = null;
//
//		if (idType.equals(CustomerIdIdentificationType.NATID)) {
//			iprsId = CustomerIprsIdentificationType.NATID;
//		} else if (idType.equals(CustomerIdIdentificationType.PSPRT)) {
//			iprsId = CustomerIprsIdentificationType.PASSPORT;
//		} else {
//			iprsId = CustomerIprsIdentificationType.ALIENID;
//		}
//
//		String fullName = null;
//
//		if(miscellaneousProperties.getCheckIprs()) {
//			Optional<IprsDetails> iprsDetailsResult = findIprsDetailsBy(iprsId, idNumber);
//
//			if (iprsDetailsResult.isEmpty()) {
//				result.setEligibilityStatus(EligibilityStatus.IPRS_DETAILS_NOT_FOUND);
//
//				log.info("{}: {}", result.getEligibilityStatus(), result);
//				return result;
//			}
//
//			var iprsDetails = iprsDetailsResult.get();
//
//			boolean hasFirstName = iprsDetails.firstName() != null && StringUtils.hasLength(iprsDetails.firstName());
//			boolean hasMiddleName = iprsDetails.middleName() != null && StringUtils.hasLength(iprsDetails.middleName());
//			boolean hasLastName = iprsDetails.lastName() != null && StringUtils.hasLength(iprsDetails.lastName());
//			if (hasFirstName || hasMiddleName || hasLastName) {
//				StringBuilder nameBuilder = new StringBuilder();
//				if (hasFirstName)
//					nameBuilder.append(iprsDetails.firstName().trim());
//				if (hasMiddleName)
//					nameBuilder.append(" ").append(iprsDetails.middleName().trim());
//				if (hasLastName)
//					nameBuilder.append(" ").append(iprsDetails.lastName());
//
//				fullName = nameBuilder.toString().trim();
//			}
//			CustomerNameMatcherResult firstNameResult = customerNameMatcher.compare(
//					request.getFirstName() != null && StringUtils.hasLength(request.getFirstName())
//					? request.getFirstName().trim()
//							: "",
//							iprsDetails.firstName() != null && StringUtils.hasLength(iprsDetails.firstName())
//							? iprsDetails.firstName().trim()
//									: "");
//
//			var middleNameIsMatch = middleNameComparator.isEqual(iprsDetails.middleName(), request.getMiddleName());
//
//			CustomerNameMatcherResult lastNameResult = customerNameMatcher.compare(
//					request.getLastName() != null && StringUtils.hasLength(request.getLastName())
//					? request.getLastName().trim()
//							: "",
//							iprsDetails.lastName() != null && StringUtils.hasLength(iprsDetails.lastName())
//							? iprsDetails.lastName().trim()
//									: "");
//
//
//			if (!firstNameResult.getPass() || !lastNameResult.getPass() || !middleNameIsMatch) {
//				result.setEligibilityStatus(EligibilityStatus.IPRS_MISMATCH);
//
//				log.debug("First name {}: pass rate = {}, Last name {}: pass rate = {}, iprs middle name: {}", iprsDetails.firstName(),
//						firstNameResult.getPass(), iprsDetails.lastName(), lastNameResult.getPass(), iprsDetails.middleName());
//
//				log.info("{}: {}", result.getEligibilityStatus(), result);
//
//				return result;
//			}
//			result.setFullName(fullName);
//			result.setGender(iprsDetails.gender());
//			result.setDateOfBirth(iprsDetails.dateOfBirth());
//
//		} else {
////			 TODO - REMOVE
////		---------------------------------
//		fullName = request.getFirstName() + " " +request.getMiddleName() + " " +request.getLastName();
//		result.setFullName(fullName);
//		result.setGender("M");
//		result.setDateOfBirth(LocalDate.now().toString());
////		---------------------------------
//
//		}
//
//		// TODO - ENABLE
////		var phoneNoAndIdMatchResult = phoneNumberValidatorClient.validate(idNumber, request.getPhoneNumber());
////
////		if(phoneNoAndIdMatchResult.isEmpty()) {
////			result.setEligibilityStatus(EligibilityStatus.IPRS_MISMATCH);
////
////			log.info("PHONE_NO_ID_NOT_FOUND: {}", result);
////
////			return result;
////		}
////
////		var phoneNoAndIdMatch = phoneNoAndIdMatchResult.get();
////
////		if(!phoneNoAndIdMatch.getStatus().equalsIgnoreCase("1")) {
////			result.setEligibilityStatus(EligibilityStatus.IPRS_MISMATCH);
////
////			log.info("PHONE_NO_ID_MISMATCH: {}", result);
////
////			return result;
////		}
//
//		CustomerSanctionDetailsIdentificationType customerSanctionDetailsIdentificationType = null;
//
//		if(idType.equals(CustomerIdIdentificationType.NATID)) {
//			customerSanctionDetailsIdentificationType = CustomerSanctionDetailsIdentificationType.NATIID;
//		} else if(idType.equals(CustomerIdIdentificationType.PSPRT)) {
//			customerSanctionDetailsIdentificationType = CustomerSanctionDetailsIdentificationType.PSPRT;
//		} else {
//			customerSanctionDetailsIdentificationType = CustomerSanctionDetailsIdentificationType.ALIENID;
//		}
//
//		// 3. check if sanctioned
//		var sanctionRequest = new SanctionDetails();
//		sanctionRequest.setMinMatchScore(minMatchScore);
//		sanctionRequest.setCustomerType("person");
//		sanctionRequest.setIdentificationDocType(customerSanctionDetailsIdentificationType);
//		sanctionRequest.setIdentificationDocNo(idNumber);
//		sanctionRequest.setFullName(fullName);
//		// TODO ENABLE
////		sanctionRequest.setDateOfBirth(iprsDetails.dateOfBirth());
//
//		var sanctionDetailsResult = findSanctionDetailsBy(sanctionRequest);
//
//		if (sanctionDetailsResult.isPresent()) {
//			result.setEligibilityStatus(EligibilityStatus.SANCTIONED);
//			result.setIsSanctioned(true);
//			SanctionDetailsSoaResponse sanctionDetailsSoaResponse = sanctionDetailsResult.get();
//			SanctionDetailsResponse sanctionDetailsResponse = null;
//
//			if (sanctionDetailsSoaResponse.getPersons().size() > 0) {
//				var details = sanctionDetailsSoaResponse.getPersons().get(0);
//				if (details != null && details.getIdentificationDocType() != null
//						&& details.getIdentificationDocNo() != null) {
//					sanctionDetailsResponse = new SanctionDetailsResponse();
//					sanctionDetailsResponse.setIdentificationType(details.getIdentificationDocType());
//					sanctionDetailsResponse.setIdentificationNo(details.getIdentificationDocNo());
//				}
//			}
//
//			if (sanctionDetailsResponse == null && sanctionDetailsSoaResponse.getEntities().size() > 0) {
//				var details = sanctionDetailsSoaResponse.getEntities().get(0);
//				if (details != null && details.getIdentificationDocType() != null
//						&& details.getIdentificationDocNo() != null) {
//					sanctionDetailsResponse = new SanctionDetailsResponse();
//					sanctionDetailsResponse.setIdentificationType(details.getIdentificationDocType());
//					sanctionDetailsResponse.setIdentificationNo(details.getIdentificationDocNo());
//				}
//			}
//
//			if (sanctionDetailsResponse != null)
//				result.setSanctionDetails(sanctionDetailsResponse);
//
//			log.info("{}: {}", result.getEligibilityStatus(), result);
//			return result;
//		}
//
//		result.setIsSanctioned(false);
//
//		result.setEligibilityStatus(EligibilityStatus.ELIGIBLE);
//
//		log.info("{}: {}", result.getEligibilityStatus(), result);
//
//		return result;
//	}
}
