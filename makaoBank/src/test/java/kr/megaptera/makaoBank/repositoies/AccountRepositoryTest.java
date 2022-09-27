package kr.megaptera.makaoBank.repositoies;

import kr.megaptera.makaoBank.models.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AccountRepositoryTest {
  @Autowired
  private AccountRepository accountRepository;

  @Test
  void creation() {
    Account account = new Account("12345","Tester");

    accountRepository.save(account);
  }
}