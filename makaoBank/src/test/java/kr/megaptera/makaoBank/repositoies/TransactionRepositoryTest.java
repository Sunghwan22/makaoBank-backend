package kr.megaptera.makaoBank.repositoies;

import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.models.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@WebAppConfiguration
class TransactionRepositoryTest {
  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  void save() {
    AccountNumber sender = new AccountNumber("1234");
    AccountNumber receiver = new AccountNumber("5678");
    Long amount = 100_000L;
    String name = "Tester";

    Transaction transaction = new Transaction(
        sender, receiver, amount, name
    );

    transactionRepository.save(transaction);
  }

  @Test
  void findAllBySenderOrReceiver() {
    LocalDateTime now = LocalDateTime.now();

    AccountNumber sender = new AccountNumber("1234");
    AccountNumber receiver = new AccountNumber("5678");
    Long amount = 100_000L;
    String name = "Tester";

    jdbcTemplate.execute("DELETE FROM transaction");

    jdbcTemplate.update("" +
            "INSERT INTO transaction(" +
            "id, sender, receiver, amount, name," +
            " created_at, updated_at" +
            ")" +
            " VALUES(1, ?, ?, ?, ?, ?, ?)",
        sender.value(), receiver.value(), amount, name, now, now
    );

    Sort sort = Sort.by("id").descending();

    Pageable pageable = PageRequest.of(0, 100, sort);

    List<Transaction> transactions =
        transactionRepository.findAllBySenderOrReceiver(sender, sender, pageable);

    assertThat(transactions).hasSize(1);
    assertThat(transactions.get(0).activity(sender)).isEqualTo("송금");
  }
}