package kr.megaptera.makaoBank.repositoies;

import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.models.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  List<Transaction> findAllBySenderOrReceiver(
      AccountNumber sender, AccountNumber receiver, Pageable pageable);

  Transaction save(Transaction transaction);
}
