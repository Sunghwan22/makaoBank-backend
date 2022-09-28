package kr.megaptera.makaoBank.repositoies;

import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.models.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@WebAppConfiguration
class TransactionRepositoryTest {
  @Autowired
  private TransactionRepository transactionRepository;

  @Test
  void save() {
    AccountNumber sender = new AccountNumber("1234");
    AccountNumber receiver = new AccountNumber("5678");
    Long amount = 100_000L;
    String name = "Tester";

    Transaction transaction = new Transaction(
        sender, receiver , amount , name
    );

    transactionRepository.save(transaction);
  }
}