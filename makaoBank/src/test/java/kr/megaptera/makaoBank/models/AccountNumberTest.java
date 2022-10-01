package kr.megaptera.makaoBank.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountNumberTest {
  @Test
  void equals() {
    assertThat(new AccountNumber("1234")).isEqualTo(new AccountNumber("1234"));
    assertThat(new AccountNumber("1234")).isNotEqualTo(new AccountNumber("12345"));

    assertThat(new AccountNumber("1234")).isNotEqualTo(null);
  }

}