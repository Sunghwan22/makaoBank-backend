package kr.megaptera.makaoBank.models;

import kr.megaptera.makaoBank.exceptions.IncorrectAmount;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
  @Test
  void transferTo() {
    Long transferAmount = 100_000L;
    Long amount1 = 1_000_000L;
    Long amount2 = 100_000L;

    Account account1 = new Account(1L, new AccountNumber("1234"), "FROM", amount1);
    Account account2 = new Account(2L, new AccountNumber("5678"), "TO", amount2);

    account1.transfer(account2, transferAmount);

    assertThat(account1.amount()).isEqualTo(amount1 - transferAmount);
    assertThat(account2.amount()).isEqualTo(amount2 + transferAmount);
  }

  @Test
  void transferToLargeAmount() {
    Long amount1 = 1_000_000L;
    Long amount2 = 100_000L;
    Long transferAmount = amount1 + amount1;

    Account account1 = new Account(1L, new AccountNumber("1234"), "FROM", amount1);
    Account account2 = new Account(2L, new AccountNumber("5678"), "TO", amount2);

    assertThrows(IncorrectAmount.class, () -> {
      account1.transfer(account2, transferAmount);
    });
  }

  @Test
  void transferNegativeAmount() {
    Long amount1 = 1_000_000L;
    Long amount2 = 100_000L;
    Long transferAmount = -100_000L;

    Account account1 = new Account(1L, new AccountNumber("1234"), "FROM", amount1);
    Account account2 = new Account(2L, new AccountNumber("5678"), "TO", amount2);

    assertThrows(IncorrectAmount.class, () -> {
      account1.transfer(account2, transferAmount);
    });
  }

  @Test
  void changePassword() {
    PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();

    Account account = Account.fake("1234");

    account.changePassword("password", passwordEncoder);

    assertThat(account.authenticate("password", passwordEncoder)).isTrue();
    assertThat(account.authenticate("xxx", passwordEncoder)).isFalse();
  }
}
