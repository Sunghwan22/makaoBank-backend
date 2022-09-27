package kr.megaptera.makaoBank.models;

import kr.megaptera.makaoBank.services.IncorrectAmount;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
  @Test
  void transferTo() {
    Long transferAmount = 100_000L;
    Long amount1 = 1_000_000L;
    Long amount2 = 100_000L;

    Account account1 = new Account(1L, "1234", "FROM", amount1);
    Account account2 = new Account(2L, "5678", "TO", amount2);

    account1.transferTo(account2, transferAmount);

    assertThat(account1.amount()).isEqualTo(amount1 - transferAmount);
    assertThat(account2.amount()).isEqualTo(amount2 + transferAmount);
  }

  @Test
  void transferToLargeAmount() {
    Long amount1 = 1_000_000L;
    Long amount2 = 100_000L;
    Long transferAmount = amount1 + amount1;

    Account account1 = new Account(1L, "1234", "FROM", amount1);
    Account account2 = new Account(2L, "5678", "TO", amount2);

    assertThrows(IncorrectAmount.class, () -> {
      account1.transferTo(account2, transferAmount);
    });
  }

  @Test
  void transferNegativeAmount() {
    Long amount1 = 1_000_000L;
    Long amount2 = 100_000L;
    Long transferAmount = -100_000L;

    Account account1 = new Account(1L, "1234", "FROM", amount1);
    Account account2 = new Account(2L, "5678", "TO", amount2);

    assertThrows(IncorrectAmount.class, () -> {
      account1.transferTo(account2, transferAmount);
    });
  }
}
