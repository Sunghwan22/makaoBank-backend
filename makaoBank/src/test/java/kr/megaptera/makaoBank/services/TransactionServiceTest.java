package kr.megaptera.makaoBank.services;

import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.models.Transaction;
import kr.megaptera.makaoBank.repositoies.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class TransactionServiceTest {
  private TransactionService transactionService;
  private TransactionRepository transactionRepository;

  @BeforeEach
  void setUp() {
    transactionRepository = mock(TransactionRepository.class);
    transactionService = new TransactionService(transactionRepository);
  }

  @Test
  void list() {
    AccountNumber accountNumber = new AccountNumber("1234");
    Transaction transaction = mock(Transaction.class);

    given(transactionRepository.findAllBySenderOrReceiver(accountNumber,accountNumber, any()))
        .willReturn(List.of(transaction));
    // 내꺼만 봐야 한다.
    List<Transaction> transactions = transactionService.list(accountNumber, 1);

    assertThat(transactions).hasSize(1);
  }

}