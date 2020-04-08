package com.example.bankingApplication.Model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "account")
@EntityListeners(AuditingEntityListener.class)
public class Account {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Account_Id", updatable = false)	
	private UUID id;
	
	@Column(name="accountNumber",nullable  = false)
	private Long accountNumber;
	
	@OneToOne(cascade=CascadeType.ALL)
	private BankInfo bankInformation;
	
	@Column(name="accountType",nullable  = false)
	private String accountType;
	
	@Column(name="accountBalance",nullable  = false)
	private Double accountBalance;
    
    @Temporal(TemporalType.TIME)
	private Date createDateTime;
	
    @Temporal(TemporalType.TIME)
	private Date updateDateTime;

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

	public BankInfo getBankInformation() {
		return bankInformation;
	}

	public void setBankInformation(BankInfo bankInformation) {
		this.bankInformation = bankInformation;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	
    
}
