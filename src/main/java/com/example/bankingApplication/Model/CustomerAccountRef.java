package com.example.bankingApplication.Model;


	import java.util.UUID;

import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;

	import lombok.AllArgsConstructor;
	import lombok.Builder;
	import lombok.Data;
	import lombok.NoArgsConstructor;

	@Entity
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@Builder
	public class CustomerAccountRef {

		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		@Column(name="CUST_ACC_XREF_ID")
		private UUID id;
		
		private Long accountNumber;
		
		private Long customerNumber;

		public UUID getId() {
			return id;
		}

		public void setId(UUID id) {
			this.id = id;
		}

		public Long getAccountNumber() {
			return accountNumber;
		}

		public void setAccountNumber(Long accountNumber) {
			this.accountNumber = accountNumber;
		}

		public Long getCustomerNumber() {
			return customerNumber;
		}

		public void setCustomerNumber(Long customerNumber) {
			this.customerNumber = customerNumber;
		}

		
	}
