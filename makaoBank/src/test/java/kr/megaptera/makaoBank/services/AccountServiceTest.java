package kr.megaptera.makaoBank.services;

import kr.megaptera.makaoBank.dtos.AccountRegistrationDto;
import kr.megaptera.makaoBank.exceptions.ConfirmPasswordMismatch;
import kr.megaptera.makaoBank.models.Account;
import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.repositoies.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AccountServiceTest {
  private AccountRepository accountRepository;
  private AccountService accountService;
  private PasswordEncoder passwordEncoder;

  @BeforeEach()
  void setUp() {
    accountRepository = mock(AccountRepository.class);
    passwordEncoder = new Argon2PasswordEncoder();

    given(accountRepository.findByAccountNumber(any()))
        .willReturn(Optional.of(Account.fake("1234")));

    accountService = new AccountService(accountRepository, passwordEncoder);
  }

  @Test
  void account() {
    AccountNumber accountNumber = new AccountNumber("1234");
    Account account = accountService.detail(accountNumber);

    verify(accountRepository).findByAccountNumber(accountNumber);

    assertThat(account.accountNumber()).isEqualTo(accountNumber);
  }

  @Test
  void create() {
    AccountRegistrationDto accountRegistrationDto = new AccountRegistrationDto(
        "녹차", "12345", "tjdghks245@", "tjdghks245@"
    );

    Account account = accountService.create(accountRegistrationDto);

    assertThat(account).isNotNull();
    assertThat(account.encodedPassword()).isNotNull();

    verify(accountRepository).save(account);
  }

  @Test
  void createWithPasswordNotEqualConfirmPassword() {
    AccountRegistrationDto accountRegistrationDto = new AccountRegistrationDto(
        "녹차", "12345", "tjdghks245@", "xxx"
    );

    assertThrows(ConfirmPasswordMismatch.class, () -> {
      accountService.create(accountRegistrationDto);
    });
  }
}
