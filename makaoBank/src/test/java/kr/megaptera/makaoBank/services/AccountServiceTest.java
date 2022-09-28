package kr.megaptera.makaoBank.services;

import kr.megaptera.makaoBank.models.Account;
import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.repositoies.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AccountServiceTest {
  AccountRepository accountRepository;
  AccountService accountService;

  @BeforeEach()
  void setUp() {
    accountRepository = mock(AccountRepository.class);

    given(accountRepository.findByAccountNumber(any()))
        .willReturn(Optional.of(Account.fake("1234")));

    accountService = new AccountService(accountRepository);
  }

  @Test
  void account() {
    AccountNumber accountNumber = new AccountNumber("1234");
    Account account = accountService.detail(accountNumber);

    verify(accountRepository).findByAccountNumber(accountNumber);

    assertThat(account.accountNumber()).isEqualTo(accountNumber);
  }
  // todo: exception에 대해서 처리해줘야함
}