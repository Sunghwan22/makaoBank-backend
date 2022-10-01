package kr.megaptera.makaoBank.services;

import kr.megaptera.makaoBank.exceptions.LoginFailed;
import kr.megaptera.makaoBank.models.Account;
import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.repositoies.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LoginService {
  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;

  public LoginService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Account login(AccountNumber accountNumber, String password) {
    Account account = accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new LoginFailed());

    if(!account.authenticate(password, passwordEncoder)) {
      throw new LoginFailed();
    };

    return account;
  }
}
