package com.vocalink.model;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public class TransactionResult {
	
	private CompletionStage<Optional<String>> result;

	public CompletionStage<Optional<String>> getResult() {
		return result;
	}

	public void setResult(CompletionStage<Optional<String>> result) {
		this.result = result;
	}

	public TransactionResult(CompletionStage<Optional<String>> result) {
		super();
		this.result = result;
	}
	
	

	
}
