//package com.coopbank.admin.administrative_service.dtos;
//
//import java.io.Serializable;
//
//import ke.co.coopbank.bidbond.engine.ws.client.customer.blacklist.BlacklistDetailsRsType;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class BlackListDetails implements Serializable {
//	private static final long serialVersionUID = -8445659634825860935L;
//
//	public BlackListDetails(BlacklistDetailsRsType.CustomerDetails input) {
//		this.idNumber = input.getIdNumber();
//		this.branchNumber = input.getBranchNumber();
//		this.accountId = input.getAccountId();
//		this.shortName = input.getShortName();
//		this.reasonCode = input.getReasonCode();
//		this.comments = input.getComments();
//		this.lastUpdated = input.getLastUpdated();
//		this.forensic = input.getForensic();
//		this.cifId = input.getCIFId();
//	}
//
//	private String idNumber;
//	private String branchNumber;
//	private String accountId;
//	private String shortName;
//	private String reasonCode;
//	private String comments;
//	private String lastUpdated;
//	private String forensic;
//	private String cifId;
//}
