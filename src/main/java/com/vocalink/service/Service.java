package com.vocalink.service;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import com.vocalink.model.Transaction;

public interface Service {
  CompletionStage<Optional<String>> processTransaction(Transaction tx);
}
