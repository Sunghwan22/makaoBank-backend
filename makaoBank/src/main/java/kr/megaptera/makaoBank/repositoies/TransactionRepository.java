package kr.megaptera.makaoBank.repositoies;

import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  Transaction save(Transaction transaction);

  List<Transaction> findAllBySenderOrReceiverOrderByCreatedAtDesc(AccountNumber sender, AccountNumber receiver);
}
