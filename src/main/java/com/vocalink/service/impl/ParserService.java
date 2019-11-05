package com.vocalink.service.impl;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.vocalink.model.Transaction;
import com.vocalink.service.Service;

public class ParserService implements Service {

	public CompletionStage<Optional<String>> processTransaction(final Transaction transaction) {


	    return CompletableFuture.supplyAsync(() -> {
	    	try {
				//parse the data
				Long.parseLong(transaction.getTransactionData());
				return Optional.empty();
			}catch (NumberFormatException e) {
				//error occurred while parsing
	            return Optional.of("ERROR");
			}
	    });
	  }
		
}
