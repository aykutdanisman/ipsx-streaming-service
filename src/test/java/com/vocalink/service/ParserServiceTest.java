package com.vocalink.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.vocalink.model.Transaction;
import com.vocalink.service.impl.ParserService;

public class ParserServiceTest {
	
	ParserService parserService;
	
	@Before
	public void prepare() {
		parserService = new ParserService();
	}

	@Test
	public void forGivenNumber_ParserService_ResultsSUCCESS() throws Exception {

		Transaction transaction = new Transaction("123");//for given
		Optional<String> expectedResult = Optional.empty();//expected
		var actualResult = new Object() {Optional<String> value;};

		CompletionStage<Optional<String>> process = parserService.processTransaction(transaction);

		process.whenComplete((result, exception) -> {
			if(result!=null)
				actualResult.value = result;
			else
				actualResult.value = Optional.of("FATALERROR");
		}
		);
		
		assertThat(expectedResult, is(actualResult.value));

	}
	
	@Test
	public void forGivenChar_ParserService_ResultsERROR() throws Exception {

		Transaction transaction = new Transaction("abc");//for given
		Optional<String> expectedResult = Optional.of("ERROR");//expected
		var actualResult = new Object() {Optional<String> value;};

		CompletionStage<Optional<String>> process = parserService.processTransaction(transaction);

		process.whenComplete((result, exception) -> {
				if(result!=null)
					actualResult.value = result;
				else
					actualResult.value = Optional.of("FATALERROR");
			}
		);
		
		assertThat(expectedResult, is(actualResult.value));
		
	}

}
