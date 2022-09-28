package kr.megaptera.makaoBank.repositoies;

import kr.megaptera.makaoBank.models.Account;
import kr.megaptera.makaoBank.models.AccountNumber;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AccountRepositoryTest {
  @Autowired
  private AccountRepository accountRepository;

  @Test
  void creation() {
    Account account = new Account(new AccountNumber("12345"),"Tester");

    accountRepository.save(account);
  }
}