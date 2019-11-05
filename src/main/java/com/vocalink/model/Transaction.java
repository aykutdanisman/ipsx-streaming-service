package com.vocalink.model;

public class Transaction {

	private String transactionData;

	
	public Transaction(String transactionData) {
		super();
		this.transactionData = transactionData;
	}

	public String getTransactionData() {
		return transactionData;
	}

	public void setTransactionData(String transactionData) {
		this.transactionData = transactionData;
	}

}
