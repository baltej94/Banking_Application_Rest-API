package com.example.bankingApplication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WithdrawDetails {

private Long accountNumber;
	
	private Double withdrawlAmount;

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getWithdrawlAmount() {
		return withdrawlAmount;
	}

	public void setWithdrawlAmount(Double withdrawlAmount) {
		this.withdrawlAmount = withdrawlAmount;
	}
	
	
}
