package kr.megaptera.makaoBank.services;

import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.models.Transaction;
import kr.megaptera.makaoBank.repositoies.TransactionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TransactionService {
  private final TransactionRepository transactionRepository;

  public TransactionService(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public List<Transaction> list(AccountNumber accountNumber) {
    return transactionRepository.findAllBySenderOrReceiverOrderByCreatedAtDesc(
        accountNumber, accountNumber
    );
  }
}
