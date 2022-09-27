package kr.megaptera.makaoBank.services;

import kr.megaptera.makaoBank.exceptions.AccountNotFound;

import kr.megaptera.makaoBank.models.Account;
import kr.megaptera.makaoBank.repositoies.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class TransferServiceTest {
  private TransferService transferService;
  private AccountRepository accountRepository;

  @BeforeEach
  void setUp() {
    accountRepository = mock(AccountRepository.class);

    transferService = new TransferService(accountRepository);
  }

  @Test
  void transfer() {
    Long amount1 = 1_000_000L;
    Long amount2 = 100_000L;

    Long transferAmount = 100_000L;

    Account account1 = new Account(1L, "1234", "FROM", amount1);
    Account account2 = new Account(2L, "5678", "TO", amount2);

    given(accountRepository.findByAccountNumber(account1.accountNumber()))
        .willReturn(Optional.of(account1));

    given(accountRepository.findByAccountNumber(account2.accountNumber()))
        .willReturn(Optional.of(account2));

    transferService.transfer("1234", "5678", transferAmount);

    assertThat(account1.amount()).isEqualTo(amount1 - transferAmount);
    assertThat(account2.amount()).isEqualTo(amount2 + transferAmount);
  }

  @Test
  void transferWithIncorrectFromAccountNumber() {
    Long amount2 = 100_000L;

    Long transferAmount = 100_000L;

    Account account2 = new Account(2L, "5678", "TO", amount2);

    given(accountRepository.findByAccountNumber(account2.accountNumber()))
        .willReturn(Optional.of(account2));

    assertThrows(AccountNotFound.class, () -> {
      transferService.transfer("1234", "5678", transferAmount);
    });
  }

  @Test
  void transferWithIncorrectToAccountNumber() {
    Long amount1 = 1_000_000L;


    Long transferAmount = 100_000L;

    Account account1 = new Account(1L, "5678", "TO", amount1);

    given(accountRepository.findByAccountNumber(account1.accountNumber()))
        .willReturn(Optional.of(account1));

    assertThrows(AccountNotFound.class, () -> {
      transferService.transfer("1234", "5678", transferAmount);
    });
  }

  @Test
  void transferWithNagativeAmount() {
    Long amount1 = 1_000_000L;
    Long amount2 = 100_000L;

    Long transferAmount = -100_000L;

    Account account1 = new Account(1L, "1234", "FROM", amount1);
    Account account2 = new Account(2L, "5678", "TO", amount2);

    given(accountRepository.findByAccountNumber(account1.accountNumber()))
        .willReturn(Optional.of(account1));

    given(accountRepository.findByAccountNumber(account2.accountNumber()))
        .willReturn(Optional.of(account2));

    assertThrows(IncorrectAmount.class, () -> {
      transferService.transfer("1234", "5678", transferAmount);
    });
  }
}
