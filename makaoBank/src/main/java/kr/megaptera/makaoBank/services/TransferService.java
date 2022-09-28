package kr.megaptera.makaoBank.services;

import kr.megaptera.makaoBank.exceptions.AccountNotFound;
import kr.megaptera.makaoBank.exceptions.IncorrectAmount;
import kr.megaptera.makaoBank.models.Account;
import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.models.Transaction;
import kr.megaptera.makaoBank.repositoies.AccountRepository;
import kr.megaptera.makaoBank.repositoies.TransactionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TransferService {
  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;

  public TransferService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
  }

  public Long transfer(AccountNumber from, AccountNumber to, Long amount, String name) {
    Account account1 = accountRepository.findByAccountNumber(from)
        .orElseThrow(() -> new AccountNotFound(from));

    Account account2 = accountRepository.findByAccountNumber(to)
        .orElseThrow(() -> new AccountNotFound(to));

    if (amount <= 0) {
      throw new IncorrectAmount(amount);
    }

    account1.transferTo(account2, amount);

    Transaction transaction = new Transaction(
        account1.accountNumber(), account2.accountNumber(), amount, name
    );
    transactionRepository.save(transaction);

    return amount;
  }
}
