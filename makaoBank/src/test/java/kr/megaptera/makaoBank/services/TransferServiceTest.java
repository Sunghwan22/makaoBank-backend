package kr.megaptera.makaoBank.services;

import kr.megaptera.makaoBank.exceptions.AccountNotFound;

import kr.megaptera.makaoBank.exceptions.IncorrectAmount;
import kr.megaptera.makaoBank.models.Account;
import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.repositoies.AccountRepository;
import kr.megaptera.makaoBank.repositoies.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TransferServiceTest {
  private TransferService transferService;
  private AccountRepository accountRepository;
  private TransactionRepository transactionRepository;

  @BeforeEach
  void setUp() {
    accountRepository = mock(AccountRepository.class);
    transactionRepository = mock(TransactionRepository.class);

    transferService = new TransferService(accountRepository, transactionRepository);
  }

  @Test
  void transfer() {
    Long amount1 = 1_000_000L;
    Long amount2 = 100_000L;

    Long transferAmount = 100_000L;

    AccountNumber accountNumber1 = new AccountNumber("1234");
    AccountNumber accountNumber2 = new AccountNumber("5678");
    String name = "Test";

    Account account1 = new Account(1L, accountNumber1, "FROM", amount1);
    Account account2 = new Account(2L, accountNumber2, "TO", amount2);

    given(accountRepository.findByAccountNumber(account1.accountNumber()))
        .willReturn(Optional.of(account1));

    given(accountRepository.findByAccountNumber(account2.accountNumber()))
        .willReturn(Optional.of(account2));

    transferService.transfer(
        accountNumber1, accountNumber2,
        transferAmount, name);

    assertThat(account1.amount()).isEqualTo(amount1 - transferAmount);
    assertThat(account2.amount()).isEqualTo(amount2 + transferAmount);

    verify(transactionRepository).save(any());
  }

  @Test
  void transferWithIncorrectFromAccountNumber() {
    Long amount2 = 100_000L;

    Long transferAmount = 100_000L;

    String name = "Test";

    AccountNumber accountNumber1 = new AccountNumber("1234");
    AccountNumber accountNumber2 = new AccountNumber("5678");

    Account account2 = new Account(2L, accountNumber2, "TO", amount2);

    given(accountRepository.findByAccountNumber(account2.accountNumber()))
        .willReturn(Optional.of(account2));

    assertThrows(AccountNotFound.class, () -> {
      transferService.transfer(accountNumber1, accountNumber2,
          transferAmount, name);
    });
  }

  @Test
  void transferWithIncorrectToAccountNumber() {
    Long amount1 = 1_000_000L;


    Long transferAmount = 100_000L;

    AccountNumber accountNumber1 = new AccountNumber("1234");
    AccountNumber accountNumber2 = new AccountNumber("5678");
    String name ="test";

    Account account1 = new Account(1L, accountNumber2, "TO", amount1);

    given(accountRepository.findByAccountNumber(account1.accountNumber()))
        .willReturn(Optional.of(account1));

    assertThrows(AccountNotFound.class, () -> {
      transferService.transfer(accountNumber1, accountNumber2, transferAmount, name);
    });
  }

  @Test
  void transferWithNagativeAmount() {
    Long amount1 = 1_000_000L;
    Long amount2 = 100_000L;

    Long transferAmount = -100_000L;

    AccountNumber accountNumber1 = new AccountNumber("1234");
    AccountNumber accountNumber2 = new AccountNumber("5678");
    String name ="test";

    Account account1 = new Account(1L, accountNumber1, "FROM", amount1);
    Account account2 = new Account(2L, accountNumber2, "TO", amount2);

    given(accountRepository.findByAccountNumber(account1.accountNumber()))
        .willReturn(Optional.of(account1));

    given(accountRepository.findByAccountNumber(account2.accountNumber()))
        .willReturn(Optional.of(account2));

    assertThrows(IncorrectAmount.class, () -> {
      transferService.transfer(accountNumber1, accountNumber2, transferAmount,name);
    });
  }
}
