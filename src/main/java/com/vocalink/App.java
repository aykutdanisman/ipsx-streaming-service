package com.vocalink;

import java.net.URI;
import java.nio.file.Paths;

import com.vocalink.model.Transaction;
import com.vocalink.model.TransactionResult;
import com.vocalink.service.Service;
import com.vocalink.service.impl.ParserService;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import akka.stream.javadsl.FileIO;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Framing;
import akka.stream.javadsl.FramingTruncation;
import akka.stream.javadsl.Sink;
import akka.util.ByteString;


public class App {

	private static final String inputFileName = "data.txt";

	public static void main(String[] args) {

		try {
			final ActorSystem system = ActorSystem.create();
			final Materializer materializer = Materializer.createMaterializer(system);

			// business service that is going to be used to process each line
			Service parserService = new ParserService();

			final URI file = App.class.getClassLoader().getResource(inputFileName).toURI();

			// stream each line to the ParserService.processTransaction()
			// we don not care the result. The process result will be handled inside..
			Flow<Transaction, TransactionResult, NotUsed> parserFlow = Flow.of(Transaction.class)
					.map(parserService::processTransaction).map(TransactionResult::new);

			
			FileIO.fromPath(Paths.get(file))
			.via(Framing.delimiter(ByteString.fromString("\r\n"), 256, FramingTruncation.ALLOW)
					.map(ByteString::utf8String))
			.map(line -> new Transaction(line)).via(parserFlow).map(tr->handleResult(tr)).to(Sink.ignore()).run(materializer);
			
		
		} catch (Exception e) {
			System.out.println("An unexpected error occured while prcessing the data!");
			e.printStackTrace();
		}
		
	}
	
	private static TransactionResult handleResult(TransactionResult transactionResult) {
		transactionResult.getResult().whenComplete(
				(result, exception) -> {
					if (result != null && result.isEmpty()) {
						//if the process is success, result is empty
						//further actions can be taken according to the result
						System.out.println(" - SUUCESS");
					}else if(result != null){ //if the result is not empty, then the process is FAIL
						System.out.println(" - "+result.get());
					}else {
						System.out.println(" - ERROR" + exception.getMessage());
					}
				}
				);
		return transactionResult;
	}
}
