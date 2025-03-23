package com.coopbank.admin.administrative_service.services;

import java.util.Optional;
import com.coopbank.admin.administrative_service.CustomerIprsIdentificationType;
import com.coopbank.admin.administrative_service.dtos.IprsDetails;
import com.coopbank.admin.administrative_service.dtos.SanctionDetails;
import com.coopbank.admin.administrative_service.dtos.SanctionDetailsSoaResponse;


public interface CustomerService {

	Optional<IprsDetails> findIprsDetailsBy(CustomerIprsIdentificationType type, String idNo);

//	Optional<BlacklistDetailsRsType> findBlacklistDetailsBy(
//			ke.co.coopbank.bidbond.engine.client.CustomerBlacklistClient.CustomerBlacklistIdentificationType type, String idNo);

	Optional<SanctionDetailsSoaResponse> findSanctionDetailsBy(SanctionDetails details);

//	EligibilityResult checkEligibility( idType, String idNumber, EligibilityRequest request);
}
