package kr.megaptera.makaoBank.services;

import kr.megaptera.makaoBank.dtos.AccountRegistrationDto;
import kr.megaptera.makaoBank.exceptions.AccountNotFound;
import kr.megaptera.makaoBank.exceptions.ConfirmPasswordMismatch;
import kr.megaptera.makaoBank.exceptions.AlreadyExistAccountNumber;
import kr.megaptera.makaoBank.models.Account;
import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.repositoies.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AccountService {
  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;

  public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Account detail(AccountNumber accountNumber) {
    return accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new AccountNotFound(accountNumber));
  }

  public Account create(AccountRegistrationDto accountRegistrationDto) {
    AccountNumber findAccountNumber =
        new AccountNumber(accountRegistrationDto.getAccountNumber());

    Optional<Account> findAccount = accountRepository.findByAccountNumber(findAccountNumber);

    if(findAccount.isPresent()) {
       throw new AlreadyExistAccountNumber();
    }


    if (!(accountRegistrationDto.getPassword()
        .equals(accountRegistrationDto.getConfirmPassword()))) {
      throw new ConfirmPasswordMismatch();
    }

    AccountNumber accountNumber =
        new AccountNumber(accountRegistrationDto.getAccountNumber());

    Account account = new Account(
        null,
        accountRegistrationDto.getName(),
        accountNumber);

    account.changePassword(accountRegistrationDto.getPassword(), passwordEncoder);

    return accountRepository.save(account);
  }
}
